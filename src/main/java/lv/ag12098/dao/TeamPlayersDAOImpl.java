package lv.ag12098.dao;
import lv.ag12098.ChampionshipDataParser;
import lv.ag12098.entity.*;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;

@Named
@Transactional
public class TeamPlayersDAOImpl extends AbstractBaseDAOImpl<TeamPlayersEntity>
        implements TeamPlayersDAO {

    @Inject
    TeamDAO teamDAO;

    @Inject
    GameDAO gameDAO;

        private static double round(double value, int places) {
            if (places < 0) throw new IllegalArgumentException();

            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
        }

        //unikālais automašīnas ieraksts pēc ID - implementācija
        @Override
        public TeamPlayersEntity getTeamPlayerById (int teamPlayerId){
                return (TeamPlayersEntity) currentSession()
                        .createQuery("from " + entityName() + " where id = :teamPlayerId ")
                        .setParameter("teamPlayerId", teamPlayerId)
                        .uniqueResult();
        }
        //nākošā sekvence (ID) - implementācija
        @Override
        public Integer getNextSeq(){
                BigInteger nextSeq = (BigInteger) currentSession()
                        .createSQLQuery("SELECT nextval('public.\"team_players_seq\"');")
                        .uniqueResult();

                return nextSeq.intValue();
        }

        public boolean exists(String teamPlayerName, String teamPlayerSurname, TeamEntity teamEntity) {
            Query query = currentSession().createQuery("select count(*) from " + entityName() +
                    " where name = :teamPlayerName and surname = :teamPlayerSurname and team_id = :teamId");

            query.setParameter("teamPlayerName", teamPlayerName);
            query.setParameter("teamPlayerSurname", teamPlayerSurname);
            query.setParameter("teamId", teamEntity.getId());

            Long count = (Long) query.uniqueResult();

            if (count > 0) return true;
            else return false;
        }

        public TeamPlayersEntity findByPlayerNumber(Integer playerNumber, TeamEntity teamEntity) {
            return (TeamPlayersEntity) currentSession()
                    .createQuery("from " + entityName() + " where playerNumber = :playerNumber and team_id = :teamId")
                    .setParameter("playerNumber", playerNumber)
                    .setParameter("teamId", teamEntity.getId())
                    .uniqueResult();
        }

        public List<BestPlayersEntity> getFirstBest(Integer limit) {
            String query = "select * from (     \n" +
                    "select tplay.id,\n" +
                    "coalesce((select count(g.id) goal_cnt\n" +
                    "  from team t\n" +
                    "  join team_players tp on tp.team_id = t.id and tp.id = tplay.id\n" +
                    "  join game_goals g on g.team_id = t.id and g.player_number = tp.player_number\n" +
                    " GROUP by tp.id),0) goals,\n" +
                    "coalesce((select count(ga.id) goal_assist_count\n" +
                    "  from team t\n" +
                    "  join team_players tp on tp.team_id = t.id and tp.id = tplay.id\n" +
                    "  join game_goals g on g.team_id = t.id\n" +
                    "  join goal_assists ga on ga.goal_id = g.id and ga.player_number = tp.player_number\n" +
                    " GROUP by tp.id),0) assists\n" +
                    " from team_players tplay \n" +
                    ") tdata order by tdata.goals desc nulls last, tdata.assists desc nulls last;";

            List<BestPlayersEntity> result =  currentSession().createSQLQuery(query)
                    .addEntity(BestPlayersEntity.class)
                    .list();

            List<BestPlayersEntity> playersList = new ArrayList<>();

            int index = 0;
            for(BestPlayersEntity player : result) {
                if (index > 9) break;
                player.setTeamPlayer(getTeamPlayerById(player.getId()));

                TeamEntity playersTeam = findPlayersTeam(player.getId());
                player.setTeamEntity(playersTeam);

                playersList.add(player);
                index++;
            }

            return playersList;

        }

        public TeamEntity findPlayersTeam(int teamPlayerId) {
            List<TeamEntity> result =  currentSession().createSQLQuery("select t.* from team t join team_players tp on tp.team_id = t.id where tp.id = :teamPlayerId")
                    .addEntity(TeamEntity.class)
                    .setParameter("teamPlayerId", teamPlayerId)
                    .list();

            if (result != null) return result.get(0);
            else return null;
        }

        public List<BestPlayersEntity> getFirstBestGoalkeepers(Integer limit) {
            List<TeamPlayersEntity> goalKeepers =  currentSession()
                    .createSQLQuery("select tp.* \n" +
                                                "  from team_players tp \n" +
                                                " where tp.position = :positionName\n" +
                                                "   and exists (select 1 \n" +
                                                "                 from players_on_field po \n" +
                                                "                where po.team_id = tp.team_id \n" +
                                                "                  and po.player_id = tp.id);")
                    .addEntity(TeamPlayersEntity.class)
                    .setParameter("positionName", "V")
                    .list();

            List<BestPlayersEntity> allGoalKeepers = new ArrayList<>();
            List<BestPlayersEntity> bestGoalkeepers = new ArrayList<>();

            for(TeamPlayersEntity keeper : goalKeepers) {
                Double goalRatio = 0.0;
                TeamEntity teamEntity = teamDAO.getTeamById(keeper.getTeamId());
                Integer gameCount = gameDAO.countAllGamesWherePlayerPlayed(keeper); // TODO jālabo, lai pareizi skaita spēlētāja spēļu skaitu
                Integer goalsLost = getPlayerGoalsLost(keeper) ; // TODO jāizveido jauna metode, kas iegūst konkrētā spēlētāja ielaistos vārtus, nevis visas komandas
                goalRatio = (double) goalsLost / gameCount;
                goalRatio = round(goalRatio,1);
                allGoalKeepers.add(new BestPlayersEntity(keeper.getId(), goalsLost, gameCount, goalRatio, keeper, teamEntity));
            }

            // sort by goals lost/game ratio
            Collections.sort(allGoalKeepers, new Comparator<BestPlayersEntity>(){
                public int compare(BestPlayersEntity o1, BestPlayersEntity o2){

                    Double ratio1 = (double) o1.getGoals() / o1.getAssists();
                    Double ratio2 = (double) o2.getGoals() / o2.getAssists();

                    if(ratio1 == ratio2)
                        return 0;
                    return ratio1 < ratio2 ? -1 : 1;
                }
            });

            int index = 0;
            for(BestPlayersEntity keeper : allGoalKeepers) {
                if (index > 4) break;
                bestGoalkeepers.add(keeper);
                index++;
            }

            return bestGoalkeepers;
        }

        public Integer getPlayerGoalsLost(TeamPlayersEntity teamPlayersEntity) {
            List<GameGoalsEntity> playerGoals =  currentSession()
                    .createSQLQuery("select gg.* \n" +
                            "  from players_on_field fd\n" +
                            "  join game g on g.id = fd.game_id\n" +
                            "  left outer join game_goals gg on gg.game_id = fd.game_id\n" +
                            " where fd.player_id = :playerId\n" +
                            "   and gg.team_id <> fd.team_id\n" +
                            "   and (fd.time_off is null or gg.goal_time between fd.time_on and fd.time_off);")
                    .addEntity(GameGoalsEntity.class)
                    .setParameter("playerId", teamPlayersEntity.getId())
                    .list();

            if (playerGoals == null) return 0;
            else return playerGoals.size();
        }

        public TeamPlayersEntity getTeamPlayerByFullName (String teamPlayerName, String teamPlayerSurname, TeamEntity teamEntity) {
            return (TeamPlayersEntity) currentSession()
                    .createQuery("from " + entityName() + " where name = :teamPlayerName and surname = :teamPlayerSurname and team_id = :teamId")
                    .setParameter("teamPlayerName", teamPlayerName)
                    .setParameter("teamPlayerSurname", teamPlayerSurname)
                    .setParameter("teamId", teamEntity.getId())
                    .uniqueResult();
        }

        public Integer getPlayerPenalties(TeamPlayersEntity teamPlayersEntity) {
            List<GamePenaltiesEntity> playerPenalties =  currentSession()
                    .createSQLQuery("select gp.*\n" +
                            "  from game_penalties gp\n" +
                            "  join team tm on tm.id = gp.team_id\n" +
                            "  join team_players tp on tp.team_id = tm.id \n" +
                            " where tp.id = :playerId\n" +
                            "   and gp.player_number = tp.player_number;")
                    .addEntity(GamePenaltiesEntity.class)
                    .setParameter("playerId", teamPlayersEntity.getId())
                    .list();

            if (playerPenalties == null) return 0;
            else return playerPenalties.size();
        }
}

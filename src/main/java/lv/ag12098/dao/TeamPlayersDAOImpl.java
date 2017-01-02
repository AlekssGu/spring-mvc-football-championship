package lv.ag12098.dao;
import lv.ag12098.ChampionshipDataParser;
import lv.ag12098.entity.*;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Named
@Transactional
public class TeamPlayersDAOImpl extends AbstractBaseDAOImpl<TeamPlayersEntity>
        implements TeamPlayersDAO {

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

        public TeamPlayersEntity getTeamPlayerByFullName (String teamPlayerName, String teamPlayerSurname, TeamEntity teamEntity) {
            return (TeamPlayersEntity) currentSession()
                    .createQuery("from " + entityName() + " where name = :teamPlayerName and surname = :teamPlayerSurname and team_id = :teamId")
                    .setParameter("teamPlayerName", teamPlayerName)
                    .setParameter("teamPlayerSurname", teamPlayerSurname)
                    .setParameter("teamId", teamEntity.getId())
                    .uniqueResult();
        }
}

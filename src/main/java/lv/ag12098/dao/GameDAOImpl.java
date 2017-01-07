package lv.ag12098.dao;

import lv.ag12098.entity.*;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Named
@Transactional
public class GameDAOImpl extends AbstractBaseDAOImpl<GameEntity>
        implements GameDAO {

        //unikālais automašīnas ieraksts pēc ID - implementācija
        @Override
        public GameEntity getGameById (Long gameId){
                return (GameEntity) currentSession()
                        .createQuery("from " + entityName() + " where id = :gameId ")
                        .setParameter("gameId", gameId)
                        .uniqueResult();
        }
        //nākošā sekvence (ID) - implementācija
        @Override
        public Integer getNextSeq(){
                BigInteger nextSeq = (BigInteger) currentSession()
                        .createSQLQuery("SELECT nextval('public.\"game_seq\"');")
                        .uniqueResult();

                return nextSeq.intValue();
        }

        @Override
        public  List<GameEntity> findGameByDate (Date date){
                return (List<GameEntity>) currentSession()
                        .createSQLQuery("select * " +
                                "from game " +
                                "where date = :date")
                        .addEntity(GameEntity.class)
                        .setParameter("date", date)
                        .list();
        }

        public Integer countAllGamesWherePlayerPlayed(TeamPlayersEntity teamPlayersEntity) {
            List<BestPlayersEntity> goalkeeperList = currentSession()
                                                    .createSQLQuery("select player_id as id, count(*) as goals, 0 as assists \n" +
                                                            "  from players_on_field\n" +
                                                            " where player_id = :playerId\n" +
                                                            " group by player_id;")
                                                    .addEntity(BestPlayersEntity.class)
                                                    .setParameter("playerId", teamPlayersEntity.getId())
                                                    .list();

            if (goalkeeperList == null) return 0;
            else return goalkeeperList.get(0).getGoals();
        }

        public List<GameEntity> findAllWhereTeamPlayed(TeamEntity teamEntity) {
            return (List<GameEntity>) currentSession()
                    .createSQLQuery("select * " +
                            "from game " +
                            "where (team_home_id = :homeId or team_guest_id = :guestId")
                    .addEntity(GameEntity.class)
                    .setParameter("homeId", teamEntity.getId())
                    .setParameter("guestId", teamEntity.getId())
                    .list();
        }

        public GameEntity findLast() {
                return (GameEntity) currentSession()
                        .createQuery("from " + entityName() + " where id = (SELECT max(id) from " + entityName())
                        .uniqueResult();
        }

        public boolean checkSourceFileLoaded(String sourceFilePath) {
            List<GameEntity> gameEntities =  currentSession()
                    .createSQLQuery("select g.* from game g where g.source_file = :sourceFilePath")
                    .addEntity(GameEntity.class)
                    .setParameter("sourceFilePath", sourceFilePath)
                    .list();

            if (gameEntities == null || gameEntities.size() == 0) return false;
            else return true;
        }
}

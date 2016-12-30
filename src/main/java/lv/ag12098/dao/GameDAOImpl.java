package lv.ag12098.dao;

import lv.ag12098.entity.GameEntity;
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
        //automašīnu meklēšana pēc markas - implementācija
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

        public GameEntity findLast() {
                return (GameEntity) currentSession()
                        .createQuery("from " + entityName() + " where id = (SELECT max(id) from " + entityName())
                        .uniqueResult();
        }
}

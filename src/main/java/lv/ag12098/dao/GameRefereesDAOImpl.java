package lv.ag12098.dao;

import lv.ag12098.entity.GameEntity;
import lv.ag12098.entity.GameRefereesEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.math.BigInteger;

@Named
@Transactional
public class GameRefereesDAOImpl extends AbstractBaseDAOImpl<GameRefereesEntity>
        implements GameRefereesDAO {

        //unikālais automašīnas ieraksts pēc ID - implementācija
        @Override
        public GameRefereesEntity getGameRefereeById (Long refereeId){
                return (GameRefereesEntity) currentSession()
                        .createQuery("from " + entityName() + " where id = :refereeId ")
                        .setParameter("refereeId", refereeId)
                        .uniqueResult();
        }
        //nākošā sekvence (ID) - implementācija
        @Override
        public Long getNextSeq(){
                BigInteger nextSeq = (BigInteger) currentSession()
                        .createSQLQuery("SELECT nextval('public.\"game_referees_seq\"');")
                        .uniqueResult();

                return nextSeq.longValue();
        }
}

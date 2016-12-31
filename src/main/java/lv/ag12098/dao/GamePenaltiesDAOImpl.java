package lv.ag12098.dao;
import lv.ag12098.entity.GamePenaltiesEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.math.BigInteger;

@Named
@Transactional
public class GamePenaltiesDAOImpl extends AbstractBaseDAOImpl<GamePenaltiesEntity>
        implements GamePenaltiesDAO {

    @Override
    public GamePenaltiesEntity getGameGoalById (Long gamePenaltyId){
            return (GamePenaltiesEntity) currentSession()
                    .createQuery("from " + entityName() + " where id = :gamePenaltyId ")
                    .setParameter("gamePenaltyId", gamePenaltyId)
                    .uniqueResult();
    }

    //nākošā sekvence (ID) - implementācija
    @Override
    public Integer getNextSeq(){
            BigInteger nextSeq = (BigInteger) currentSession()
                    .createSQLQuery("SELECT nextval('public.\"game_penalties_seq\"');")
                    .uniqueResult();

            return nextSeq.intValue();
    }
}

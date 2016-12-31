package lv.ag12098.dao;
import lv.ag12098.entity.GameGoalsEntity;
import lv.ag12098.entity.GoalAssistsEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.math.BigInteger;

@Named
@Transactional
public class GoalAssistsDAOImpl extends AbstractBaseDAOImpl<GoalAssistsEntity>
        implements GoalAssistsDAO {

    //unikālais automašīnas ieraksts pēc ID - implementācija
    @Override
    public GoalAssistsEntity getGoalAssistById(Long goalAssistId) {
            return (GoalAssistsEntity) currentSession()
                    .createQuery("from " + entityName() + " where id = :goalAssistId ")
                    .setParameter("goalAssistId", goalAssistId)
                    .uniqueResult();
    }

    //nākošā sekvence (ID) - implementācija
    @Override
    public Integer getNextSeq(){
            BigInteger nextSeq = (BigInteger) currentSession()
                    .createSQLQuery("SELECT nextval('public.\"goal_assists_seq\"');")
                    .uniqueResult();

            return nextSeq.intValue();
    }
}

package lv.ag12098.dao;
import lv.ag12098.entity.GameGoalsEntity;
import lv.ag12098.entity.PlayersOnFieldEntity;
import lv.ag12098.entity.TeamPlayersEntity;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.math.BigInteger;

@Named
@Transactional
public class GameGoalsDAOImpl extends AbstractBaseDAOImpl<GameGoalsEntity>
        implements GameGoalsDAO {

    //unikālais automašīnas ieraksts pēc ID - implementācija
    @Override
    public GameGoalsEntity getGameGoalById (Long gameGoalId){
            return (GameGoalsEntity) currentSession()
                    .createQuery("from " + entityName() + " where id = :gameGoalId ")
                    .setParameter("gameGoalId", gameGoalId)
                    .uniqueResult();
    }

    //nākošā sekvence (ID) - implementācija
    @Override
    public Integer getNextSeq(){
            BigInteger nextSeq = (BigInteger) currentSession()
                    .createSQLQuery("SELECT nextval('public.\"game_goals_seq\"');")
                    .uniqueResult();

            return nextSeq.intValue();
    }
}

package lv.ag12098.dao;
import lv.ag12098.entity.GameChangesEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.math.BigInteger;

@Named
@Transactional
public class GameChangesDAOImpl extends AbstractBaseDAOImpl<GameChangesEntity>
        implements GameChangesDAO {

    //unikālais automašīnas ieraksts pēc ID - implementācija
    @Override
    public GameChangesEntity getGameChangeById(Long gameChangeId) {
            return (GameChangesEntity) currentSession()
                    .createQuery("from " + entityName() + " where id = :gameChangeId ")
                    .setParameter("gameChangeId", gameChangeId)
                    .uniqueResult();
    }

    //nākošā sekvence (ID) - implementācija
    @Override
    public Integer getNextSeq(){
            BigInteger nextSeq = (BigInteger) currentSession()
                    .createSQLQuery("SELECT nextval('public.\"game_changes_seq\"');")
                    .uniqueResult();

            return nextSeq.intValue();
    }
}

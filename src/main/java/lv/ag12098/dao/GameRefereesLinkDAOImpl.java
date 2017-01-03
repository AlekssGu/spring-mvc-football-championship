package lv.ag12098.dao;

import lv.ag12098.entity.GameRefereesLinkEntity;

import javax.inject.Named;
import javax.transaction.Transactional;
import java.math.BigInteger;

@Named
@Transactional
public class GameRefereesLinkDAOImpl extends AbstractBaseDAOImpl<GameRefereesLinkEntity>
        implements GameRefereesLinkDAO {
    @Override
    public Long getNextSeq(){
        BigInteger nextSeq = (BigInteger) currentSession()
                .createSQLQuery("SELECT nextval('public.\"game_referees_link_seq\"');")
                .uniqueResult();

        return nextSeq.longValue();
    }
}

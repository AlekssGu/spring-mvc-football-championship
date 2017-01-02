package lv.ag12098.dao;

import lv.ag12098.entity.GameEntity;
import lv.ag12098.entity.TeamEntity;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Named
@Transactional
public class TeamDAOImpl extends AbstractBaseDAOImpl<TeamEntity>
    implements TeamDAO {

    //unikālais automašīnas ieraksts pēc ID - implementācija
    @Override
    public TeamEntity getTeamById (Long teamId){
        return (TeamEntity) currentSession()
                .createQuery("from " + entityName() + " where id = :teamId ")
                .setParameter("teamId", teamId)
                .uniqueResult();
    }

    public TeamEntity getTeamByName (String teamName){
        return (TeamEntity) currentSession()
                .createQuery("from " + entityName() + " where team_name = :teamName")
                .setParameter("teamName", teamName)
                .uniqueResult();
    }

    public Integer getGamesWonOT(Integer teamId) {

        String query = "select * " +
                "from team t " +
                "join game g on g.team_won_id = t.id " +
                "where t.id = :teamId " +
                "and g.is_overtime = :isOvertime";

        List result =  castedList(currentSession().createSQLQuery(query)
                .addEntity(GameEntity.class)
                .setParameter("teamId", teamId)
                .setParameter("isOvertime", true)
                .list());

        if (result == null) return 0;
        else return (Integer) result.size();
    }

    public Integer getGamesLostOT(Integer teamId) {

        String query = "select * " +
                "from team t " +
                "join game g on (g.team_guest_id = t.id or g.team_home_id = t.id) " +
                "where t.id = :teamId " +
                "and g.team_won_id != t.id " +
                "and g.is_overtime = :isOvertime";

        List result =  castedList(currentSession().createSQLQuery(query)
                .addEntity(GameEntity.class)
                .setParameter("teamId", teamId)
                .setParameter("isOvertime", true)
                .list());

        if (result == null) return 0;
        else return (Integer) result.size();
    }

    public boolean exists(String teamName) {
        Query query = currentSession().createQuery("select count(*) from " + entityName() + " where team_name = :teamName");
        query.setParameter("teamName", teamName);

        Long count = (Long) query.uniqueResult();

        if (count > 0) return true;
        else return false;
    }

    //nākošā sekvence (ID) - implementācija
    @Override
    public Integer getNextSeq(){
        BigInteger nextSeq = (BigInteger) currentSession()
                .createSQLQuery("SELECT nextval('public.\"team_seq\"');")
                .uniqueResult();

        return nextSeq.intValue();
    }
}

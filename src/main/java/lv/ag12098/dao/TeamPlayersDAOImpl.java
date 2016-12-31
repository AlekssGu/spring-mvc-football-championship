package lv.ag12098.dao;
import lv.ag12098.entity.TeamEntity;
import lv.ag12098.entity.TeamPlayersEntity;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.math.BigInteger;

@Named
@Transactional
public class TeamPlayersDAOImpl extends AbstractBaseDAOImpl<TeamPlayersEntity>
        implements TeamPlayersDAO {

        //unikālais automašīnas ieraksts pēc ID - implementācija
        @Override
        public TeamPlayersEntity getTeamPlayerById (Long teamPlayerId){
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

        public TeamPlayersEntity getTeamPlayerByFullName (String teamPlayerName, String teamPlayerSurname, TeamEntity teamEntity) {
            return (TeamPlayersEntity) currentSession()
                    .createQuery("from " + entityName() + " where name = :teamPlayerName and surname = :teamPlayerSurname and team_id = :teamId")
                    .setParameter("teamPlayerName", teamPlayerName)
                    .setParameter("teamPlayerSurname", teamPlayerSurname)
                    .setParameter("teamId", teamEntity.getId())
                    .uniqueResult();
        }
}

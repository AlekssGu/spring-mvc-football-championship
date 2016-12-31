package lv.ag12098.dao;
import lv.ag12098.entity.GameEntity;
import lv.ag12098.entity.PlayersOnFieldEntity;
import lv.ag12098.entity.TeamEntity;
import lv.ag12098.entity.TeamPlayersEntity;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.Entity;
import javax.inject.Named;
import java.math.BigInteger;

@Named
@Transactional
public class PlayersOnFieldDAOImpl extends AbstractBaseDAOImpl<PlayersOnFieldEntity>
        implements PlayersOnFieldDAO {

    //unikālais automašīnas ieraksts pēc ID - implementācija
    @Override
    public PlayersOnFieldEntity getPlayerOnFieldbyPlayerId (Integer teamPlayerId, GameEntity gameEntity){
            return (PlayersOnFieldEntity) currentSession()
                    .createQuery("from " + entityName() + " where player_id = :teamPlayerId and game_id = :gameId")
                    .setParameter("teamPlayerId", teamPlayerId)
                    .setParameter("gameId", gameEntity.getId())
                    .uniqueResult();
    }

    //nākošā sekvence (ID) - implementācija
    @Override
    public Integer getNextSeq(){
            BigInteger nextSeq = (BigInteger) currentSession()
                    .createSQLQuery("SELECT nextval('public.\"players_on_field_seq\"');")
                    .uniqueResult();

            return nextSeq.intValue();
    }

    public boolean existsByFullName(String playerOnFieldName, String playerOnFieldSurname) {
        Query query = currentSession().createQuery("select count(*) from " + entityName() +
                " where name = :playerOnFieldName and surname = :playerOnFieldSurname");

        query.setParameter("playerOnFieldName", playerOnFieldName);
        query.setParameter("playerOnFieldSurname", playerOnFieldSurname);

        Long count = (Long) query.uniqueResult();

        if (count > 0) return true;
        else return false;
    }

    public boolean existsByPlayerNumber(Integer playerNumber, TeamPlayersEntity playersEntity) {
        Query query = currentSession().createQuery("select count(*) from " + entityName() +
                " join public.team_players tpl on tpl.id = " + entityName() + ".player_id" +
                " where tpl.team_id = :teamId and tpl.player_number = :teamPlayerNumber");

        query.setParameter("teamId", playersEntity.getTeamId());
        query.setParameter("teamPlayerNumber", playersEntity.getPlayerNumber());

        Long count = (Long) query.uniqueResult();

        if (count > 0) return true;
        else return false;
    }

    @Override
    public PlayersOnFieldEntity getPlayerOnFieldbyFullName (String playerOnFieldName, String playerOnFieldSurname) {
        return (PlayersOnFieldEntity) currentSession()
                .createQuery("from " + entityName() + " where name = :playerOnFieldName and surname = :playerOnFieldSurname")
                .setParameter("playerOnFieldName", playerOnFieldName)
                .setParameter("playerOnFieldSurname", playerOnFieldSurname)
                .uniqueResult();
    }
}

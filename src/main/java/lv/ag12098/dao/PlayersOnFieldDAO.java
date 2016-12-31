package lv.ag12098.dao;

import lv.ag12098.entity.PlayersOnFieldEntity;
import lv.ag12098.entity.TeamEntity;
import lv.ag12098.entity.TeamPlayersEntity;

public interface PlayersOnFieldDAO extends AbstractBaseDAO<PlayersOnFieldEntity> {

    PlayersOnFieldEntity getPlayerOnFieldbyPlayerId(Integer teamPlayerId);
    PlayersOnFieldEntity getPlayerOnFieldbyFullName(String playerOnFieldName, String playerOnFieldSurname);
    boolean existsByFullName(String playerOnFieldName, String playerOnFieldSurname);
    boolean existsByPlayerNumber(Integer playerNumber, TeamPlayersEntity playersEntity);

    //nākošā sekvence (ID)
    Integer getNextSeq();

}

package lv.ag12098.dao;

import lv.ag12098.entity.TeamEntity;
import lv.ag12098.entity.TeamPlayersEntity;

public interface TeamPlayersDAO extends AbstractBaseDAO<TeamPlayersEntity> {

    TeamPlayersEntity getTeamPlayerById(Long teamPlayerId);
    TeamPlayersEntity getTeamPlayerByFullName(String teamPlayerName, String teamPlayerSurname, TeamEntity teamEntity);
    TeamPlayersEntity findByPlayerNumber(Integer playerNumber, TeamEntity teamEntity);
    boolean exists(String teamPlayerName, String teamPlayerSurname, TeamEntity teamEntity);

    //nākošā sekvence (ID)
    Integer getNextSeq();

}

package lv.ag12098.dao;

import lv.ag12098.entity.TeamPlayersEntity;

public interface TeamPlayersDAO extends AbstractBaseDAO<TeamPlayersEntity> {

    TeamPlayersEntity getTeamPlayerById(Long teamPlayerId);
    TeamPlayersEntity getTeamPlayerByFullName(String teamPlayerName, String teamPlayerSurname);
    boolean exists(String teamPlayerName, String teamPlayerSurname);

    //nākošā sekvence (ID)
    Integer getNextSeq();

}

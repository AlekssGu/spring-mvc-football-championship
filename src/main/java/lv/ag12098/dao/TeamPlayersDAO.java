package lv.ag12098.dao;

import lv.ag12098.entity.BestPlayersEntity;
import lv.ag12098.entity.TeamEntity;
import lv.ag12098.entity.TeamPlayersEntity;

import java.util.List;

public interface TeamPlayersDAO extends AbstractBaseDAO<TeamPlayersEntity> {

    TeamPlayersEntity getTeamPlayerById(int teamPlayerId);
    TeamPlayersEntity getTeamPlayerByFullName(String teamPlayerName, String teamPlayerSurname, TeamEntity teamEntity);
    TeamPlayersEntity findByPlayerNumber(Integer playerNumber, TeamEntity teamEntity);
    TeamEntity findPlayersTeam(int teamPlayerId);

    Integer getPlayerGoalsLost(TeamPlayersEntity teamPlayersEntity);
    Integer getPlayerPenalties(TeamPlayersEntity teamPlayersEntity);

    List<BestPlayersEntity> getFirstBestGoalkeepers(Integer limit);
    List<BestPlayersEntity> getFirstBest(Integer limit);
    boolean exists(String teamPlayerName, String teamPlayerSurname, TeamEntity teamEntity);

    //nākošā sekvence (ID)
    Integer getNextSeq();

}

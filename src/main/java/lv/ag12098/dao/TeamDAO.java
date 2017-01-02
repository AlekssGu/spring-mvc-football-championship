package lv.ag12098.dao;

import lv.ag12098.entity.GameEntity;
import lv.ag12098.entity.TeamEntity;

import java.util.Date;
import java.util.List;

public interface TeamDAO extends AbstractBaseDAO<TeamEntity> {

    TeamEntity getTeamById(Long teamId);
    TeamEntity getTeamByName(String teamName);

    Integer getGamesWonOT(Integer teamId);
    Integer getGamesLostOT(Integer teamId);

    boolean exists(String teamName);
    //nākošā sekvence (ID)
    Integer getNextSeq();
}

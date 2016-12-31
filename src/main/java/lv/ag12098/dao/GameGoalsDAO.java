package lv.ag12098.dao;

import lv.ag12098.entity.GameGoalsEntity;
import lv.ag12098.entity.PlayersOnFieldEntity;
import lv.ag12098.entity.TeamPlayersEntity;

public interface GameGoalsDAO extends AbstractBaseDAO<GameGoalsEntity> {

    GameGoalsEntity getGameGoalById(Long gameGoalId);

    //nākošā sekvence (ID)
    Integer getNextSeq();

}

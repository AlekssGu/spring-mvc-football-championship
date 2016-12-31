package lv.ag12098.dao;

import lv.ag12098.entity.GamePenaltiesEntity;

public interface GamePenaltiesDAO extends AbstractBaseDAO<GamePenaltiesEntity> {

    GamePenaltiesEntity getGameGoalById(Long gameGoalId);

    //nākošā sekvence (ID)
    Integer getNextSeq();

}

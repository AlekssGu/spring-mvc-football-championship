package lv.ag12098.dao;

import lv.ag12098.entity.GoalAssistsEntity;

public interface GoalAssistsDAO extends AbstractBaseDAO<GoalAssistsEntity> {

    GoalAssistsEntity getGoalAssistById(Long goalAssistId);

    //nākošā sekvence (ID)
    Integer getNextSeq();

}

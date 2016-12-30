package lv.ag12098.dao;

import lv.ag12098.entity.GameRefereesEntity;

public interface GameRefereesDAO extends AbstractBaseDAO<GameRefereesEntity> {

    GameRefereesEntity getGameRefereeById(Long refereeId);

    //nākošā sekvence (ID)
    Long getNextSeq();

}

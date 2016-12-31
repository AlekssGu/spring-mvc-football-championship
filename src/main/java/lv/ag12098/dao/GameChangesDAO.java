package lv.ag12098.dao;

import lv.ag12098.entity.GameChangesEntity;

public interface GameChangesDAO extends AbstractBaseDAO<GameChangesEntity> {

    GameChangesEntity getGameChangeById(Long gameChangeId);

    //nākošā sekvence (ID)
    Integer getNextSeq();

}

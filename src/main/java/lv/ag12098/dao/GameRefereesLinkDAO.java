package lv.ag12098.dao;

import lv.ag12098.entity.GameRefereesLinkEntity;

public interface GameRefereesLinkDAO extends AbstractBaseDAO<GameRefereesLinkEntity> {
    //nākošā sekvence (ID)
    Long getNextSeq();
}

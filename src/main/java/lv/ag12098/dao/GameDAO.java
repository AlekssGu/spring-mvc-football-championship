package lv.ag12098.dao;

import java.util.Date;
import java.util.List;
import lv.ag12098.entity.GameEntity;

public interface GameDAO extends AbstractBaseDAO<GameEntity> {

    GameEntity getGameById(Long gameId);

    List<GameEntity> findGameByDate(Date date);

    GameEntity findLast();

    //nākošā sekvence (ID)
    Integer getNextSeq();

}

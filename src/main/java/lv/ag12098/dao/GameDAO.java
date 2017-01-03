package lv.ag12098.dao;

import java.util.Date;
import java.util.List;
import lv.ag12098.entity.GameEntity;
import lv.ag12098.entity.TeamEntity;
import lv.ag12098.entity.TeamPlayersEntity;

public interface GameDAO extends AbstractBaseDAO<GameEntity> {

    GameEntity getGameById(Long gameId);

    List<GameEntity> findGameByDate(Date date);

    GameEntity findLast();
    List<GameEntity> findAllWhereTeamPlayed(TeamEntity teamEntity);
    Integer countAllGamesWherePlayerPlayed(TeamPlayersEntity teamPlayersEntity);

    //nākošā sekvence (ID)
    Integer getNextSeq();

}

package lv.ag12098.dao;

import lv.ag12098.entity.BestPlayersEntity;
import lv.ag12098.entity.BestRefereesEntity;
import lv.ag12098.entity.GameRefereesEntity;

import java.util.List;

public interface GameRefereesDAO extends AbstractBaseDAO<GameRefereesEntity> {

    GameRefereesEntity getGameRefereeById(int refereeId);
    List<BestRefereesEntity> getAllRefereeData();
    GameRefereesEntity getGameRefereeByNameAndSurname(String name, String surname, String refereeType);
    boolean exists (String name, String surname, String refereeType);
    double getRefereeTimePlayed(GameRefereesEntity refereesEntity, String refereeType);
    //nākošā sekvence (ID)
    Long getNextSeq();

}

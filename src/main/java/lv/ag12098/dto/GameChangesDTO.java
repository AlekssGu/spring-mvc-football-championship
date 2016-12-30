package lv.ag12098.dto;

import lv.ag12098.entity.GameChangesEntity;
import java.sql.Timestamp;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */
public class GameChangesDTO {
    private int id;
    private int gameId;
    private Timestamp changeTime;
    private Integer playerOffNumber;
    private Integer playerOnNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Timestamp getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Timestamp changeTime) {
        this.changeTime = changeTime;
    }

    public Integer getPlayerOffNumber() {
        return playerOffNumber;
    }

    public void setPlayerOffNumber(Integer playerOffNumber) {
        this.playerOffNumber = playerOffNumber;
    }

    public Integer getPlayerOnNumber() {
        return playerOnNumber;
    }

    public void setPlayerOnNumber(Integer playerOnNumber) {
        this.playerOnNumber = playerOnNumber;
    }

    public GameChangesDTO(GameChangesEntity entity) {
        this.id = entity.getId();
        this.gameId = entity.getGameId();
        this.changeTime = entity.getChangeTime();
        this.playerOffNumber = entity.getPlayerOffNumber();
        this.playerOnNumber = entity.getPlayerOnNumber();
    }
}

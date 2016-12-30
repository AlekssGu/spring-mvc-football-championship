package lv.ag12098.dto;

import lv.ag12098.entity.GamePenaltiesEntity;
import java.sql.Timestamp;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */

public class GamePenaltiesDTO {
    private int id;
    private int gameId;
    private Timestamp penaltyTime;
    private Integer playerNumber;

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

    public Timestamp getPenaltyTime() {
        return penaltyTime;
    }

    public void setPenaltyTime(Timestamp penaltyTime) {
        this.penaltyTime = penaltyTime;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(Integer playerNumber) {
        this.playerNumber = playerNumber;
    }

    public GamePenaltiesDTO(GamePenaltiesEntity entity) {
        this.id = entity.getId();
        this.gameId = entity.getGameId();
        this.penaltyTime = entity.getPenaltyTime();
        this.playerNumber = entity.getPlayerNumber();
    }
}

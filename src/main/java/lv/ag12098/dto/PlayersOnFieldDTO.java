package lv.ag12098.dto;

import java.sql.Timestamp;
import lv.ag12098.entity.PlayersOnFieldEntity;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */

public class PlayersOnFieldDTO {
    private int id;
    private int gameId;
    private int playerId;
    private Timestamp timeOn;
    private Timestamp timeOff;

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

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public Timestamp getTimeOn() {
        return timeOn;
    }

    public void setTimeOn(Timestamp timeOn) {
        this.timeOn = timeOn;
    }

    public Timestamp getTimeOff() {
        return timeOff;
    }

    public void setTimeOff(Timestamp timeOff) {
        this.timeOff = timeOff;
    }

    public PlayersOnFieldDTO(PlayersOnFieldEntity entity) {
        this.id = entity.getId();
        this.gameId = entity.getGameId();
        this.playerId = entity.getPlayerId();
        this.timeOn = entity.getTimeOn();
        this.timeOff = entity.getTimeOff();
    }
}

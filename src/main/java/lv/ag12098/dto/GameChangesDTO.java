package lv.ag12098.dto;

import lv.ag12098.entity.GameChangesEntity;
import java.sql.Timestamp;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */
public class GameChangesDTO {
    private int id;
    private int gameId;
    private int teamId;
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

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
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

    public GameChangesDTO(int id, int gameId, int teamId, Timestamp changeTime, Integer playerOffNumber, Integer playerOnNumber) {
        this.id = id;
        this.gameId = gameId;
        this.teamId = teamId;
        this.changeTime = changeTime;
        this.playerOffNumber = playerOffNumber;
        this.playerOnNumber = playerOnNumber;
    }
}

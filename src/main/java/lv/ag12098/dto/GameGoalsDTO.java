package lv.ag12098.dto;

import lv.ag12098.entity.GameGoalsEntity;
import java.sql.Timestamp;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */

public class GameGoalsDTO {
    private int id;
    private int gameId;
    private int teamId;
    private Timestamp goalTime;
    private Integer playerNumber;
    private String isPenalty;

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

    public Timestamp getGoalTime() {
        return goalTime;
    }

    public void setGoalTime(Timestamp goalTime) {
        this.goalTime = goalTime;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(Integer playerNumber) {
        this.playerNumber = playerNumber;
    }

    public String getIsPenalty() {
        return isPenalty;
    }

    public void setIsPenalty(String isPenalty) {
        this.isPenalty = isPenalty;
    }

    public GameGoalsDTO(GameGoalsEntity entity) {
        this.id = entity.getId();
        this.gameId = entity.getGameId();
        this.teamId = entity.getTeamId();
        this.goalTime = entity.getGoalTime();
        this.playerNumber = entity.getPlayerNumber();
        this.isPenalty = entity.getIsPenalty();
    }
}

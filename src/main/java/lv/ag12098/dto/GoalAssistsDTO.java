package lv.ag12098.dto;

import lv.ag12098.entity.GoalAssistsEntity;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */

public class GoalAssistsDTO {
    private int id;
    private int goalId;
    private Integer playerNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(Integer playerNumber) {
        this.playerNumber = playerNumber;
    }

    public GoalAssistsDTO(GoalAssistsEntity entity) {
        this.id = entity.getId();
        this.goalId = entity.getGoalId();
        this.playerNumber = entity.getPlayerNumber();
    }
}

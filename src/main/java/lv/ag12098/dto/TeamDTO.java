package lv.ag12098.dto;

import lv.ag12098.entity.TeamEntity;

import static org.json.XMLTokener.entity;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */

public class TeamDTO {
    private int id;
    private String teamName;
    // statistics data:
    private int gameCount;
    private int gamesLost;
    private int gamesWon;
    private int gamesTied;
    private int goalsScored;
    private int goalsLost;
    private int goalsRelation;
    private int totalPoints;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesTied() {
        return gamesTied;
    }

    public void setGamesTied(int gamesTied) {
        this.gamesTied = gamesTied;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public int getGoalsLost() {
        return goalsLost;
    }

    public void setGoalsLost(int goalsLost) {
        this.goalsLost = goalsLost;
    }

    public int getGoalsRelation() {
        return goalsRelation;
    }

    public void setGoalsRelation(int goalsRelation) {
        this.goalsRelation = goalsRelation;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public void calcGoalRelation() {
        this.goalsRelation = this.goalsScored - this.goalsLost;
    }

    public TeamDTO(TeamEntity teamEntity) {
        this.id = id;
        this.teamName = teamEntity.getTeamName();
        this.gameCount = teamEntity.getGameCount();
        this.gamesLost = teamEntity.getGamesLost();
        this.gamesWon = teamEntity.getGamesWon();
        this.gamesTied = teamEntity.getGamesTied();
        this.goalsScored = teamEntity.getGoalsScored();
        this.goalsLost = teamEntity.getGoalsLost();
        this.goalsRelation = teamEntity.getGoalsRelation();
        this.totalPoints = teamEntity.getTotalPoints();
    }
}

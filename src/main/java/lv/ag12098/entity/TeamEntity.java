package lv.ag12098.entity;

import javax.persistence.*;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */
@Entity
@Table(name = "team", schema = "public", catalog = "football_champ")
public class TeamEntity {
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "team_name")
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Basic
    @Column(name = "game_count")
    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    @Basic
    @Column(name = "games_lost")
    public int getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }

    @Basic
    @Column(name = "games_won")
    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    @Basic
    @Column(name = "games_tied")
    public int getGamesTied() {
        return gamesTied;
    }

    public void setGamesTied(int gamesTied) {
        this.gamesTied = gamesTied;
    }

    @Basic
    @Column(name = "goals_scored")
    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    @Basic
    @Column(name = "goals_lost")
    public int getGoalsLost() {
        return goalsLost;
    }

    public void setGoalsLost(int goalsLost) {
        this.goalsLost = goalsLost;
    }

    @Basic
    @Column(name = "goals_relation")
    public int getGoalsRelation() {
        return goalsRelation;
    }

    public void setGoalsRelation(int goalsRelation) {
        this.goalsRelation = goalsRelation;
    }

    @Basic
    @Column(name = "total_points")
    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}

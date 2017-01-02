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

    @Transient
    private int gamesWonOT;
    private int gamesWonFT;
    private int gamesLostOT;
    private int gamesLostFT;

    // vērtība mainās katrai spēlei un kolonna nav datubāzē:
    private int gameGoalsScored;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Transient
    public int getGameGoalsScored() {
        return gameGoalsScored;
    }

    @Transient
    public void setGameGoalsScored(int gameGoalsScored) {
        this.gameGoalsScored = gameGoalsScored;
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

    @Transient
    public int getGamesWonOT() {
        return gamesWonOT;
    }

    public void setGamesWonOT(int gamesWonOT) {
        this.gamesWonOT = gamesWonOT;
    }

    @Transient
    public int getGamesWonFT() {
        return gamesWonFT;
    }

    public void setGamesWonFT(int gamesWonFT) {
        this.gamesWonFT = gamesWonFT;
    }

    @Transient
    public int getGamesLostOT() {
        return gamesLostOT;
    }

    public void setGamesLostOT(int gamesLostOT) {
        this.gamesLostOT = gamesLostOT;
    }

    @Transient
    public int getGamesLostFT() {
        return gamesLostFT;
    }

    public void setGamesLostFT(int gamesLostFT) {
        this.gamesLostFT = gamesLostFT;
    }
}

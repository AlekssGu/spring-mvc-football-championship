package lv.ag12098.entity;

import javax.persistence.*;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */
@Entity
@Table(name = "best_players", schema = "public", catalog = "football_champ")
public class BestPlayersEntity {

    private int id;
    private int goals;
    private int assists;
    private Double goalRatio;

    private TeamPlayersEntity teamPlayer;
    private TeamEntity teamEntity;
    private GameRefereesEntity refereesEntity;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "goals", nullable = true)
    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    @Basic
    @Column(name = "assists", nullable = true)
    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    @Transient
    public TeamPlayersEntity getTeamPlayer() {
        return teamPlayer;
    }

    public void setTeamPlayer(TeamPlayersEntity teamPlayer) {
        this.teamPlayer = teamPlayer;
    }

    @Transient
    public TeamEntity getTeamEntity() {
        return teamEntity;
    }

    public void setTeamEntity(TeamEntity teamEntity) {
        this.teamEntity = teamEntity;
    }

    public BestPlayersEntity() {

    }

    @Transient
    public Double getGoalRatio() {
        return goalRatio;
    }

    public void setGoalRatio(Double goalRatio) {
        this.goalRatio = goalRatio;
    }

    @Transient
    public GameRefereesEntity getRefereesEntity() {
        return refereesEntity;
    }

    public void setRefereesEntity(GameRefereesEntity refereesEntity) {
        this.refereesEntity = refereesEntity;
    }

    public BestPlayersEntity(int id, int goals, GameRefereesEntity refereesEntity) {
        this.id = id;
        this.goals = goals;
        this.refereesEntity = refereesEntity;
    }

    public BestPlayersEntity(int id, int goals, int assists, Double goalRatio, TeamPlayersEntity teamPlayer, TeamEntity teamEntity) {
        this.id = id;
        this.goals = goals;
        this.assists = assists;
        this.goalRatio = goalRatio;
        this.teamPlayer = teamPlayer;
        this.teamEntity = teamEntity;
    }
}

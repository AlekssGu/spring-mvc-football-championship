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

    private TeamPlayersEntity teamPlayer;
    private TeamEntity teamEntity;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "goals")
    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    @Basic
    @Column(name = "assists")
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
}

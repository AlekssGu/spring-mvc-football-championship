package lv.ag12098.entity;

import javax.persistence.*;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */
@Entity
@Table(name = "goal_assists", schema = "public", catalog = "football_champ")
public class GoalAssistsEntity {
    private int id;
    private int goalId;
    private Integer playerNumber;

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
    @Column(name = "goal_id")
    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    @Basic
    @Column(name = "player_number")
    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(Integer playerNumber) {
        this.playerNumber = playerNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoalAssistsEntity that = (GoalAssistsEntity) o;

        if (id != that.id) return false;
        if (goalId != that.goalId) return false;
        if (playerNumber != null ? !playerNumber.equals(that.playerNumber) : that.playerNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + goalId;
        result = 31 * result + (playerNumber != null ? playerNumber.hashCode() : 0);
        return result;
    }
}

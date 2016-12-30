package lv.ag12098.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */
@Entity
@Table(name = "game_goals", schema = "public", catalog = "football_champ")
public class GameGoalsEntity {
    private int id;
    private int gameId;
    private int teamId;
    private Timestamp goalTime;
    private Integer playerNumber;
    private String isPenalty;

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
    @Column(name = "game_id")
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    @Basic
    @Column(name = "team_id")
    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    @Basic
    @Column(name = "goal_time")
    public Timestamp getGoalTime() {
        return goalTime;
    }

    public void setGoalTime(Timestamp goalTime) {
        this.goalTime = goalTime;
    }

    @Basic
    @Column(name = "player_number")
    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(Integer playerNumber) {
        this.playerNumber = playerNumber;
    }

    @Basic
    @Column(name = "is_penalty")
    public String getIsPenalty() {
        return isPenalty;
    }

    public void setIsPenalty(String isPenalty) {
        this.isPenalty = isPenalty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameGoalsEntity that = (GameGoalsEntity) o;

        if (id != that.id) return false;
        if (gameId != that.gameId) return false;
        if (teamId != that.teamId) return false;
        if (goalTime != null ? !goalTime.equals(that.goalTime) : that.goalTime != null) return false;
        if (playerNumber != null ? !playerNumber.equals(that.playerNumber) : that.playerNumber != null) return false;
        if (isPenalty != null ? !isPenalty.equals(that.isPenalty) : that.isPenalty != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + gameId;
        result = 31 * result + teamId;
        result = 31 * result + (goalTime != null ? goalTime.hashCode() : 0);
        result = 31 * result + (playerNumber != null ? playerNumber.hashCode() : 0);
        result = 31 * result + (isPenalty != null ? isPenalty.hashCode() : 0);
        return result;
    }
}

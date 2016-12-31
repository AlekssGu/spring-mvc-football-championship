package lv.ag12098.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */
@Entity
@Table(name = "game_penalties", schema = "public", catalog = "football_champ")
public class GamePenaltiesEntity {
    private int id;
    private int gameId;
    private int teamId;
    private Timestamp penaltyTime;
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
    @Column(name = "penalty_time")
    public Timestamp getPenaltyTime() {
        return penaltyTime;
    }

    public void setPenaltyTime(Timestamp penaltyTime) {
        this.penaltyTime = penaltyTime;
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

        GamePenaltiesEntity that = (GamePenaltiesEntity) o;

        if (id != that.id) return false;
        if (gameId != that.gameId) return false;
        if (teamId != that.teamId) return false;
        if (penaltyTime != null ? !penaltyTime.equals(that.penaltyTime) : that.penaltyTime != null) return false;
        return playerNumber != null ? playerNumber.equals(that.playerNumber) : that.playerNumber == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + gameId;
        result = 31 * result + teamId;
        result = 31 * result + (penaltyTime != null ? penaltyTime.hashCode() : 0);
        result = 31 * result + (playerNumber != null ? playerNumber.hashCode() : 0);
        return result;
    }
}

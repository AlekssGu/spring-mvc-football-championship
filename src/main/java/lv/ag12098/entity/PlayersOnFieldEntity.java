package lv.ag12098.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */
@Entity
@Table(name = "players_on_field", schema = "public", catalog = "football_champ")
public class PlayersOnFieldEntity {
    private int id;
    private int gameId;
    private int teamId;
    private int playerId;
    private Timestamp timeOn;
    private Timestamp timeOff;

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
    @Column(name = "player_id")
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    @Basic
    @Column(name = "time_on")
    public Timestamp getTimeOn() {
        return timeOn;
    }

    public void setTimeOn(Timestamp timeOn) {
        this.timeOn = timeOn;
    }

    @Basic
    @Column(name = "time_off")
    public Timestamp getTimeOff() {
        return timeOff;
    }

    public void setTimeOff(Timestamp timeOff) {
        this.timeOff = timeOff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayersOnFieldEntity that = (PlayersOnFieldEntity) o;

        if (id != that.id) return false;
        if (gameId != that.gameId) return false;
        if (teamId != that.teamId) return false;
        if (playerId != that.playerId) return false;
        if (timeOn != null ? !timeOn.equals(that.timeOn) : that.timeOn != null) return false;
        return timeOff != null ? timeOff.equals(that.timeOff) : that.timeOff == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + gameId;
        result = 31 * result + teamId;
        result = 31 * result + playerId;
        result = 31 * result + (timeOn != null ? timeOn.hashCode() : 0);
        result = 31 * result + (timeOff != null ? timeOff.hashCode() : 0);
        return result;
    }
}

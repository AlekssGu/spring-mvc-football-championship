package lv.ag12098.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */
@Entity
@Table(name = "game_changes", schema = "public", catalog = "football_champ")
public class GameChangesEntity {
    private int id;
    private int gameId;
    private Timestamp changeTime;
    private Integer playerOffNumber;
    private Integer playerOnNumber;

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
    @Column(name = "change_time")
    public Timestamp getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Timestamp changeTime) {
        this.changeTime = changeTime;
    }

    @Basic
    @Column(name = "player_off_number")
    public Integer getPlayerOffNumber() {
        return playerOffNumber;
    }

    public void setPlayerOffNumber(Integer playerOffNumber) {
        this.playerOffNumber = playerOffNumber;
    }

    @Basic
    @Column(name = "player_on_number")
    public Integer getPlayerOnNumber() {
        return playerOnNumber;
    }

    public void setPlayerOnNumber(Integer playerOnNumber) {
        this.playerOnNumber = playerOnNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameChangesEntity that = (GameChangesEntity) o;

        if (id != that.id) return false;
        if (gameId != that.gameId) return false;
        if (changeTime != null ? !changeTime.equals(that.changeTime) : that.changeTime != null) return false;
        if (playerOffNumber != null ? !playerOffNumber.equals(that.playerOffNumber) : that.playerOffNumber != null)
            return false;
        if (playerOnNumber != null ? !playerOnNumber.equals(that.playerOnNumber) : that.playerOnNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + gameId;
        result = 31 * result + (changeTime != null ? changeTime.hashCode() : 0);
        result = 31 * result + (playerOffNumber != null ? playerOffNumber.hashCode() : 0);
        result = 31 * result + (playerOnNumber != null ? playerOnNumber.hashCode() : 0);
        return result;
    }
}

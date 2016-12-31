package lv.ag12098.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */
@Entity
@Table(name = "game", schema = "public", catalog = "football_champ")
public class GameEntity {
    private Integer id;
    private Date gameDate;
    private Integer attendees;
    private String place;
    private Integer gameRound;
    private Integer gameSeq;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "game_date")
    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    @Basic
    @Column(name = "attendees")
    public Integer getAttendees() {
        return attendees;
    }

    public void setAttendees(Integer attendees) {
        this.attendees = attendees;
    }

    @Basic
    @Column(name = "place")
    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Basic
    @Column(name = "game_round")
    public Integer getGameRound() {
        return gameRound;
    }

    public void setGameRound(Integer gameRound) {
        this.gameRound = gameRound;
    }

    @Basic
    @Column(name = "game_round_seq")
    public Integer getGameSeq() {
        return gameSeq;
    }

    public void setGameSeq(Integer gameSeq) {
        this.gameSeq = gameSeq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameEntity that = (GameEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (gameDate != null ? !gameDate.equals(that.gameDate) : that.gameDate != null) return false;
        if (attendees != null ? !attendees.equals(that.attendees) : that.attendees != null) return false;
        if (place != null ? !place.equals(that.place) : that.place != null) return false;
        if (gameRound != null ? !gameRound.equals(that.gameRound) : that.gameRound != null) return false;
        return gameSeq != null ? gameSeq.equals(that.gameSeq) : that.gameSeq == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (gameDate != null ? gameDate.hashCode() : 0);
        result = 31 * result + (attendees != null ? attendees.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (gameRound != null ? gameRound.hashCode() : 0);
        result = 31 * result + (gameSeq != null ? gameSeq.hashCode() : 0);
        return result;
    }
}

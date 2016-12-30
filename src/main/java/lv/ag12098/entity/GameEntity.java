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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameEntity that = (GameEntity) o;

        if (id != that.id) return false;
        if (attendees != that.attendees) return false;
        if (gameDate != null ? !gameDate.equals(that.gameDate) : that.gameDate != null) return false;
        if (place != null ? !place.equals(that.place) : that.place != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (gameDate != null ? gameDate.hashCode() : 0);
        result = 31 * result + attendees;
        result = 31 * result + (place != null ? place.hashCode() : 0);
        return result;
    }
}

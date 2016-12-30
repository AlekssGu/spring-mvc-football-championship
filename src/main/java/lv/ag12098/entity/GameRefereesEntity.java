package lv.ag12098.entity;

import javax.persistence.*;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */
@Entity
@Table(name = "game_referees", schema = "public", catalog = "football_champ")
public class GameRefereesEntity {
    private int id;
    private int gameId;
    private String refereeType;
    private String refereeName;
    private String refereeSurname;

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
    @Column(name = "referee_type")
    public String getRefereeType() {
        return refereeType;
    }

    public void setRefereeType(String refereeType) {
        this.refereeType = refereeType;
    }

    @Basic
    @Column(name = "referee_name")
    public String getRefereeName() {
        return refereeName;
    }

    public void setRefereeName(String refereeName) {
        this.refereeName = refereeName;
    }

    @Basic
    @Column(name = "referee_surname")
    public String getRefereeSurname() {
        return refereeSurname;
    }

    public void setRefereeSurname(String refereeSurname) {
        this.refereeSurname = refereeSurname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameRefereesEntity that = (GameRefereesEntity) o;

        if (id != that.id) return false;
        if (gameId != that.gameId) return false;
        if (refereeType != null ? !refereeType.equals(that.refereeType) : that.refereeType != null) return false;
        if (refereeName != null ? !refereeName.equals(that.refereeName) : that.refereeName != null) return false;
        if (refereeSurname != null ? !refereeSurname.equals(that.refereeSurname) : that.refereeSurname != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + gameId;
        result = 31 * result + (refereeType != null ? refereeType.hashCode() : 0);
        result = 31 * result + (refereeName != null ? refereeName.hashCode() : 0);
        result = 31 * result + (refereeSurname != null ? refereeSurname.hashCode() : 0);
        return result;
    }
}

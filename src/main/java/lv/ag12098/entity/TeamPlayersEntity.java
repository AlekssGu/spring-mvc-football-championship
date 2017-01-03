package lv.ag12098.entity;

import javax.persistence.*;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */
@Entity
@Table(name = "team_players", schema = "public", catalog = "football_champ")
public class TeamPlayersEntity {
    private int id;
    private int teamId;
    private int playerNumber;
    private String position;
    private String name;
    private String surname;

    private int penaltyCount;
    private int yellowCardCount;
    private int redCardCount;

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
    @Column(name = "team_id")
    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    @Basic
    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "player_number")
    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    @Transient
    public int getPenaltyCount() {
        return penaltyCount;
    }

    public void setPenaltyCount(int penaltyCount) {
        this.penaltyCount = penaltyCount;
    }

    @Transient
    public int getYellowCardCount() {
        return yellowCardCount;
    }

    public void setYellowCardCount(int yellowCardCount) {
        this.yellowCardCount = yellowCardCount;
    }

    @Transient
    public int getRedCardCount() {
        return redCardCount;
    }

    public void setRedCardCount(int redCardCount) {
        this.redCardCount = redCardCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamPlayersEntity that = (TeamPlayersEntity) o;

        if (id != that.id) return false;
        if (teamId != that.teamId) return false;
        if (playerNumber != that.playerNumber) return false;
        if (position != null ? !position.equals(that.position) : that.position != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return surname != null ? surname.equals(that.surname) : that.surname == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + teamId;
        result = 31 * result + playerNumber;
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        return result;
    }
}

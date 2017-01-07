package lv.ag12098.entity;

import javax.persistence.*;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */
@Entity
@Table(name = "best_referees", schema = "public", catalog = "football_champ")
public class BestRefereesEntity {

    private String fullName;
    private String name;
    private String surname;
    private int penalties;
    private double minutesPlayedAsReferee;
    private double minutesPlayedAsLinesman;

    private GameRefereesEntity refereesEntity;

    @Id
    @Column(name = "fullname", unique = true, nullable = false)
    public String getFullname() {
        return fullName;
    }

    public void setFullname(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "surname", nullable = true)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "name", nullable = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "penalties", nullable = true)
    public int getPenalties() {
        return penalties;
    }

    public void setPenalties(int penalties) {
        this.penalties = penalties;
    }

    @Transient
    public double getMinutesPlayedAsReferee() {
        return minutesPlayedAsReferee;
    }

    public void setMinutesPlayedAsReferee(double minutesPlayedAsReferee) {
        this.minutesPlayedAsReferee = minutesPlayedAsReferee;
    }

    @Transient
    public double getMinutesPlayedAsLinesman() {
        return minutesPlayedAsLinesman;
    }

    public void setMinutesPlayedAsLinesman(double minutesPlayedAsLinesman) {
        this.minutesPlayedAsLinesman = minutesPlayedAsLinesman;
    }

    @Transient
    public GameRefereesEntity getRefereesEntity() {
        return refereesEntity;
    }

    public void setRefereesEntity(GameRefereesEntity refereesEntity) {
        this.refereesEntity = refereesEntity;
    }

    public BestRefereesEntity() {

    }

    public BestRefereesEntity(String fullName, String name, String surname, int penalties, GameRefereesEntity refereesEntity) {
        this.fullName = fullName;
        this.name = name;
        this.surname = surname;
        this.penalties = penalties;
        this.refereesEntity = refereesEntity;
    }
}

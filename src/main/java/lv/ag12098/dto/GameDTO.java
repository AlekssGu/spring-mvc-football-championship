package lv.ag12098.dto;

import lv.ag12098.entity.GameEntity;
import java.util.Date;

public class GameDTO {

    private int id;
    private Date gameDate;
    private int attendees;
    private String place;
    private Integer gameRound;
    private Integer gameSeq;
    private boolean isOvertime;
    private Integer teamHomeId;
    private Integer teamGuestId;

    GameDTO(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void setGameDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    public int getAttendees() {
        return attendees;
    }

    public void setAttendees(int attendees) {
        this.attendees = attendees;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getGameRound() {
        return gameRound;
    }

    public void setGameRound(Integer gameRound) {
        this.gameRound = gameRound;
    }

    public Integer getGameSeq() {
        return gameSeq;
    }

    public void setGameSeq(Integer gameSeq) {
        this.gameSeq = gameSeq;
    }

    public boolean isOvertime() {
        return isOvertime;
    }

    public void setOvertime(boolean overtime) {
        isOvertime = overtime;
    }

    public Integer getTeamHomeId() {
        return teamHomeId;
    }

    public void setTeamHomeId(Integer teamHomeId) {
        this.teamHomeId = teamHomeId;
    }

    public Integer getTeamGuestId() {
        return teamGuestId;
    }

    public void setTeamGuestId(Integer teamGuestId) {
        this.teamGuestId = teamGuestId;
    }

    public GameDTO(int id, Date gameDate, int attendees, String place, Integer gameRound, Integer gameSeq, boolean isOvertime, Integer teamHomeId, Integer teamGuestId) {
        this.id = id;
        this.gameDate = gameDate;
        this.attendees = attendees;
        this.place = place;
        this.gameRound = gameRound;
        this.gameSeq = gameSeq;
        this.isOvertime = isOvertime;
        this.teamHomeId = teamHomeId;
        this.teamGuestId = teamGuestId;
    }
}

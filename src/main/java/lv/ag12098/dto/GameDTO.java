package lv.ag12098.dto;

import lv.ag12098.entity.GameEntity;
import java.util.Date;

public class GameDTO {

    private int id;
    private Date gameDate;
    private int attendees;
    private String place;

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

    //konstruktors saņem entity objektu un uzseto DTO laukus
    public GameDTO(GameEntity entity){
        id = entity.getId();
        gameDate = entity.getGameDate();
        attendees = entity.getAttendees();
        place = entity.getPlace();
    }
}

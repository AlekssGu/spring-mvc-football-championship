package lv.ag12098.dto;

import lv.ag12098.entity.TeamPlayersEntity;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */

public class TeamPlayersDTO {
    private int id;
    private int teamId;
    private String position;
    private String name;
    private String surname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public TeamPlayersDTO(TeamPlayersEntity entity) {
        this.id = entity.getId();
        this.teamId = entity.getTeamId();
        this.position = entity.getPosition();
        this.name = entity.getName();
        this.surname = entity.getSurname();
    }
}

package lv.ag12098.dto;

import lv.ag12098.entity.TeamEntity;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */

public class TeamDTO {
    private int id;
    private String teamName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public TeamDTO(TeamEntity entity) {
        this.id = entity.getId();
        this.teamName = entity.getTeamName();
    }
}

package lv.ag12098.dto;

import lv.ag12098.entity.GameRefereesEntity;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */
public class GameRefereesDTO {
    private int id;
    private int gameId;
    private String refereeType;
    private String refereeName;
    private String refereeSurname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getRefereeType() {
        return refereeType;
    }

    public void setRefereeType(String refereeType) {
        this.refereeType = refereeType;
    }

    public String getRefereeName() {
        return refereeName;
    }

    public void setRefereeName(String refereeName) {
        this.refereeName = refereeName;
    }

    public String getRefereeSurname() {
        return refereeSurname;
    }

    public void setRefereeSurname(String refereeSurname) {
        this.refereeSurname = refereeSurname;
    }

    public GameRefereesDTO(GameRefereesEntity entity) {
        this.id = entity.getId();
        this.gameId = entity.getGameId();
        this.refereeType = entity.getRefereeType();
        this.refereeName = entity.getRefereeName();
        this.refereeSurname = entity.getRefereeSurname();
    }
}

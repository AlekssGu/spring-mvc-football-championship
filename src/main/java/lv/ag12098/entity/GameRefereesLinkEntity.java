package lv.ag12098.entity;

import javax.persistence.*;

/**
 * Created by aleksandrs.gusevs on 2016.12.29..
 */
@Entity
@Table(name = "game_referees_link", schema = "public", catalog = "football_champ")
public class GameRefereesLinkEntity {
    private int id;
    private int refereeId;
    private int gameId;

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
    @Column(name = "referee_id")
    public int getRefereeId() {
        return refereeId;
    }

    public void setRefereeId(int refereeId) {
        this.refereeId = refereeId;
    }
}

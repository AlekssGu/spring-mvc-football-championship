package lv.ag12098;

import lv.ag12098.dao.*;
import lv.ag12098.entity.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import javax.inject.Inject;
import java.io.*;
import java.net.URL;
import java.sql.Timestamp;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by aleksandrs.gusevs on 2016.12.27..
 */
@Service
public class ChampionshipDataParser {

    @Inject
    GameDAO gameDAO;

    @Inject
    GameRefereesDAO gameRefereesDAO;

    @Inject
    TeamDAO teamDAO;

    @Inject
    TeamPlayersDAO teamPlayersDAO;

    @Inject
    PlayersOnFieldDAO playersOnFieldDAO;

    @Inject
    GameGoalsDAO gameGoalsDAO;

    @Inject
    GoalAssistsDAO goalAssistsDAO;

    @Inject
    GameChangesDAO gameChangesDAO;

    @Inject
    GamePenaltiesDAO gamePenaltiesDAO;

    private List<URL> generateUrlArray()
    {
        List<URL> jsonFiles = new ArrayList<URL>();

        jsonFiles.add(0, ChampionshipDataParser.class.getResource("/championship-data/FirstRound/futbols0.json"));
        jsonFiles.add(1, ChampionshipDataParser.class.getResource("/championship-data/FirstRound/futbols1.json"));
        jsonFiles.add(2, ChampionshipDataParser.class.getResource("/championship-data/FirstRound/futbols2.json"));

        jsonFiles.add(3, ChampionshipDataParser.class.getResource("/championship-data/SecondRound/futbols0.json"));
        jsonFiles.add(4, ChampionshipDataParser.class.getResource("/championship-data/SecondRound/futbols1.json"));
        jsonFiles.add(5, ChampionshipDataParser.class.getResource("/championship-data/SecondRound/futbols2.json"));

        return jsonFiles;
    }

    public void parseJsonFile()
    {
        String line;
        Integer gameRound = 1;
        Integer gameRoundSeq = 1;

        try {
            // Iterate over all championship data json files
            for (URL jsonFileUrl : generateUrlArray()) {
                String jsonText = "";

                System.out.println("url = " + jsonFileUrl);
                System.out.println("jsonText = " + jsonText);

                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(jsonFileUrl.getPath()), "UTF-8"));

                System.out.println("jsonText = " + jsonText);

                while ((line = reader.readLine()) != null) {
                    jsonText += line;
                }

                System.out.println("jsonText = " + jsonText);

                System.out.println(jsonText);
                JSONObject obj = new JSONObject(jsonText);

                JSONObject game = obj.getJSONObject("Spele");
                System.out.println("Speles informacija");
                System.out.println("        Speles datums: " + game.getString("Laiks"));
                System.out.println("        Speles vieta: " + game.getString("Vieta"));
                System.out.println("        Skatitaju skaits: " + game.getString("Skatitaji"));

                GameEntity gameEntity = new GameEntity();

                try {
                    DateFormat df = new SimpleDateFormat("YYYY/MM/DD");
                    gameEntity.setGameDate(df.parse(game.getString("Laiks")));
                    gameEntity.setAttendees(Integer.valueOf(game.getString("Skatitaji")));
                    gameEntity.setPlace(game.getString("Vieta"));
                    gameEntity.setGameSeq(gameRoundSeq);
                    gameEntity.setGameRound(gameRound);
                    gameDAO.save(gameEntity);
                } catch (Exception e) {
                    System.out.println("[ERROR] Kļūda, veidojot spēles ierakstu");
                    e.printStackTrace();
                }

                // Tiesnešu informācija
                JSONArray linesmen = game.getJSONArray("T");
                JSONObject referee = game.getJSONObject("VT");
                saveRefereeData(linesmen, referee, gameEntity);


                JSONArray team = game.getJSONArray("Komanda");
                saveTeamData(team, gameEntity);

                if(gameRoundSeq == 3) {
                    gameRound++;
                    gameRoundSeq = 1;
                } else {
                    gameRoundSeq++;
                }
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Kļūda, apstrādājot JSON datus");
            e.printStackTrace();
        }

    }

    private boolean saveTeamPlayersData(JSONObject teamPlayers, GameEntity gameEntity, TeamEntity teamEntity)
    {
        try {
            TeamPlayersEntity teamPlayersEntity;
            JSONArray playerData = teamPlayers.getJSONArray("Speletajs");

            for (int j = 0; j < playerData.length(); j++) {
                JSONObject playerRow = playerData.getJSONObject(j);
                String position = playerRow.getString("Loma");
                Integer playerNumber = playerRow.getInt("Nr");
                String name = playerRow.getString("Vards");
                String surname = playerRow.getString("Uzvards");

                // if not exists, create new player
                if (!teamPlayersDAO.exists(name, surname, teamEntity)) {
                    teamPlayersEntity = new TeamPlayersEntity();
                    teamPlayersEntity.setPosition(position);
                    teamPlayersEntity.setName(name);
                    teamPlayersEntity.setSurname(surname);
                    teamPlayersEntity.setTeamId(teamEntity.getId());
                    teamPlayersEntity.setPlayerNumber(playerNumber);
                    teamPlayersDAO.save(teamPlayersEntity);
                }
            }
        } catch (JSONException e) {
            System.out.println("[ERROR] Kļūda, saglabājot spēlētāju datus:");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean savePlayersOnFieldData(JSONObject playersOnField, GameEntity gameEntity, TeamEntity teamEntity)
    {
        try {
            JSONArray playerData = playersOnField.getJSONArray("Speletajs");

            for (int j = 0; j < playerData.length(); j++) {
                JSONObject playerRow = playerData.getJSONObject(j);
                Integer playerNumber = playerRow.getInt("Nr");

                Time time = new Time(0, 0, 0);

                PlayersOnFieldEntity playersOnFieldEntity = new PlayersOnFieldEntity();
                playersOnFieldEntity.setGameId(gameEntity.getId());
                playersOnFieldEntity.setTimeOn(new Timestamp(time.getTime()));
                playersOnFieldEntity.setTeamId(teamEntity.getId());
                playersOnFieldEntity.setPlayerId(teamPlayersDAO.findByPlayerNumber(playerNumber, teamEntity).getId());
                playersOnFieldDAO.save(playersOnFieldEntity);
            }
        } catch (JSONException e) {
            System.out.println("[ERROR] Kļūda, saglabājot laukuma spēlētāju datus:");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean saveTeamGoalsData(JSONObject teamGoals, GameEntity gameEntity, TeamEntity teamEntity)
    {
        try {
            JSONObject goalsScoredObj = teamGoals.optJSONObject("VG");

            if (goalsScoredObj != null) {
                // Object
                String timeScored = goalsScoredObj.getString("Laiks");
                Integer playerNumber = goalsScoredObj.getInt("Nr");
                String isPenalty = goalsScoredObj.getString("Sitiens");

                String[] timesplit = timeScored.split(":");
                Time time = new Time(0, Integer.valueOf(timesplit[0]), Integer.valueOf(timesplit[1]));

                GameGoalsEntity gameGoalsEntity = new GameGoalsEntity();
                gameGoalsEntity.setGameId(gameEntity.getId());
                gameGoalsEntity.setTeamId(teamEntity.getId());
                gameGoalsEntity.setIsPenalty(isPenalty);
                gameGoalsEntity.setPlayerNumber(playerNumber);
                gameGoalsEntity.setGoalTime(new Timestamp(time.getTime()));
                gameGoalsDAO.save(gameGoalsEntity);
            } else {
                // Array
                JSONArray goalsScored = teamGoals.optJSONArray("VG");
                for (int j = 0; j < goalsScored.length(); j++) {
                    JSONObject goalRow = goalsScored.getJSONObject(j);
                    String timeScored = goalRow.getString("Laiks");
                    Integer playerNumber = goalRow.getInt("Nr");
                    String isPenalty = goalRow.getString("Sitiens");

                    String[] timesplit = timeScored.split(":");
                    Time time = new Time(0, Integer.valueOf(timesplit[0]), Integer.valueOf(timesplit[1]));

                    GameGoalsEntity gameGoalsEntity = new GameGoalsEntity();
                    gameGoalsEntity.setGameId(gameEntity.getId());
                    gameGoalsEntity.setTeamId(teamEntity.getId());
                    gameGoalsEntity.setIsPenalty(isPenalty);
                    gameGoalsEntity.setPlayerNumber(playerNumber);
                    gameGoalsEntity.setGoalTime(new Timestamp(time.getTime()));
                    gameGoalsDAO.save(gameGoalsEntity);

                    try {
                        JSONArray goalAssists = goalRow.getJSONArray("P");
                        for (int k = 0; k < goalAssists.length(); k++) {
                            JSONObject goalAssist = goalAssists.getJSONObject(k);
                            playerNumber = goalAssist.getInt("Nr");
                            GoalAssistsEntity goalAssistsEntity = new GoalAssistsEntity();
                            goalAssistsEntity.setGoalId(gameGoalsEntity.getId());
                            goalAssistsEntity.setPlayerNumber(playerNumber);
                            goalAssistsDAO.save(goalAssistsEntity);
                        }
                    } catch (JSONException e) {
                        System.out.println("Vārtu autoram nav bijušas piespēles");
                    }
                }
            }
        } catch (JSONException e) {
            System.out.println("[ERROR] Kļūda, saglabājot komandu iesisto vārtu datus:");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean saveGameChangesData(JSONObject gameChanges, GameEntity gameEntity, TeamEntity teamEntity)
    {
        try {
            JSONObject changeData = gameChanges.optJSONObject("Maina");

            // Object
            if (changeData != null) {
                String timeChanged = changeData.getString("Laiks");
                Integer playerNumberOff = changeData.getInt("Nr1");
                Integer playerNumberOn = changeData.getInt("Nr2");
                String[] timesplit = timeChanged.split(":");

                Time time = new Time(0, Integer.valueOf(timesplit[0]), Integer.valueOf(timesplit[1]));

                // new change
                GameChangesEntity gameChangesEntity = new GameChangesEntity();
                gameChangesEntity.setGameId(gameEntity.getId());
                gameChangesEntity.setTeamId(teamEntity.getId());
                gameChangesEntity.setChangeTime(new Timestamp(time.getTime()));
                gameChangesEntity.setPlayerOffNumber(playerNumberOff);
                gameChangesEntity.setPlayerOnNumber(playerNumberOn);
                gameChangesDAO.save(gameChangesEntity);

                // update time played once player goes off the field
                TeamPlayersEntity playerOffEntity = teamPlayersDAO.findByPlayerNumber(playerNumberOff, teamEntity);
                PlayersOnFieldEntity playerOff = playersOnFieldDAO.getPlayerOnFieldbyPlayerId(playerOffEntity.getId(), gameEntity);
                playerOff.setTimeOff(new Timestamp(time.getTime()));
                playersOnFieldDAO.save(playerOff);

                PlayersOnFieldEntity playerOn = new PlayersOnFieldEntity();
                playerOn.setTeamId(teamEntity.getId());
                playerOn.setPlayerId(teamPlayersDAO.findByPlayerNumber(playerNumberOn, teamEntity).getId());
                playerOn.setTimeOn(new Timestamp(time.getTime()));
                playerOn.setGameId(gameEntity.getId());
                playersOnFieldDAO.save(playerOn);
            } else {
                // Array
                JSONArray changeDataArray = gameChanges.optJSONArray("Maina");

                for (int k = 0; k < changeDataArray.length(); k++) {
                    JSONObject changeRow = changeDataArray.getJSONObject(k);

                    String timeChanged = changeRow.getString("Laiks");
                    Integer playerNumberOff = changeRow.getInt("Nr1");
                    Integer playerNumberOn = changeRow.getInt("Nr2");
                    String[] timesplit = timeChanged.split(":");

                    Time time = new Time(0, Integer.valueOf(timesplit[0]), Integer.valueOf(timesplit[1]));

                    // new change
                    GameChangesEntity gameChangesEntity = new GameChangesEntity();
                    gameChangesEntity.setGameId(gameEntity.getId());
                    gameChangesEntity.setTeamId(teamEntity.getId());
                    gameChangesEntity.setChangeTime(new Timestamp(time.getTime()));
                    gameChangesEntity.setPlayerOffNumber(playerNumberOff);
                    gameChangesEntity.setPlayerOnNumber(playerNumberOn);
                    gameChangesDAO.save(gameChangesEntity);

                    // update time played once player goes off the field
                    TeamPlayersEntity playerOffEntity = teamPlayersDAO.findByPlayerNumber(playerNumberOff, teamEntity);
                    PlayersOnFieldEntity playerOff = playersOnFieldDAO.getPlayerOnFieldbyPlayerId(playerOffEntity.getId(), gameEntity);
                    playerOff.setTimeOff(new Timestamp(time.getTime()));
                    playersOnFieldDAO.save(playerOff);

                    PlayersOnFieldEntity playerOn = new PlayersOnFieldEntity();
                    playerOn.setTeamId(teamEntity.getId());
                    playerOn.setPlayerId(teamPlayersDAO.findByPlayerNumber(playerNumberOn, teamEntity).getId());
                    playerOn.setTimeOn(new Timestamp(time.getTime()));
                    playerOn.setGameId(gameEntity.getId());
                    playersOnFieldDAO.save(playerOn);
                }

                System.out.println("MASĪVS MAINA");
            }
        } catch (JSONException e) {
            System.out.println("[ERROR] Kļūda, saglabājot spēlētāju maiņu datus:");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean saveTeamPenaltyData(JSONObject teamPenalties, GameEntity gameEntity, TeamEntity teamEntity)
    {
        try {
            JSONObject teamPenalty = teamPenalties.optJSONObject("Sods");

            if (teamPenalty != null) {
                // Object
                String penaltyTime = teamPenalty.getString("Laiks");
                Integer playerNumber = teamPenalty.getInt("Nr");

                String[] timesplit = penaltyTime.split(":");
                Time time = new Time(0, Integer.valueOf(timesplit[0]), Integer.valueOf(timesplit[1]));

                GamePenaltiesEntity gamePenaltiesEntity = new GamePenaltiesEntity();
                gamePenaltiesEntity.setGameId(gameEntity.getId());
                gamePenaltiesEntity.setTeamId(teamEntity.getId());
                gamePenaltiesEntity.setPlayerNumber(playerNumber);
                gamePenaltiesEntity.setPenaltyTime(new Timestamp(time.getTime()));
                gamePenaltiesDAO.save(gamePenaltiesEntity);
            } else {
                // Array
                JSONArray teamPenaltiesArray = teamPenalties.optJSONArray("Sods");
                for (int j = 0; j < teamPenaltiesArray.length(); j++) {
                    JSONObject teamPenaltyObj = teamPenaltiesArray.getJSONObject(j);
                    String penaltyTime = teamPenaltyObj.getString("Laiks");
                    Integer playerNumber = teamPenaltyObj.getInt("Nr");

                    String[] timesplit = penaltyTime.split(":");
                    Time time = new Time(0, Integer.valueOf(timesplit[0]), Integer.valueOf(timesplit[1]));

                    GamePenaltiesEntity gamePenaltiesEntity = new GamePenaltiesEntity();
                    gamePenaltiesEntity.setGameId(gameEntity.getId());
                    gamePenaltiesEntity.setTeamId(teamEntity.getId());
                    gamePenaltiesEntity.setPlayerNumber(playerNumber);
                    gamePenaltiesEntity.setPenaltyTime(new Timestamp(time.getTime()));
                    gamePenaltiesDAO.save(gamePenaltiesEntity);
                }
            }
        } catch (JSONException e) {
            System.out.println("[ERROR] Kļūda, saglabājot komandu sodu datus:");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean saveRefereeData(JSONArray linesmen, JSONObject referee, GameEntity gameEntity)
    {
        String key = null;
        Iterator keys = null;

        try {
            System.out.println("Līnijtiesneši");
            for (int i = 0; i < linesmen.length(); i++) {
                JSONObject row = linesmen.getJSONObject(i);
                GameRefereesEntity gameRefereesEntity = new GameRefereesEntity();
                gameRefereesEntity.setRefereeName(row.getString("Vards"));
                gameRefereesEntity.setRefereeSurname(row.getString("Uzvards"));
                gameRefereesEntity.setGameId(gameEntity.getId());
                gameRefereesEntity.setRefereeType("VT");
                gameRefereesDAO.save(gameRefereesEntity);

                System.out.println("    Uzvards: " + row.getString("Uzvards"));
                System.out.println("    Vards: " + row.getString("Vards"));
            }

            System.out.println("Vecākais tiesnesis");

            GameRefereesEntity gameRefereesEntity = new GameRefereesEntity();
            gameRefereesEntity.setRefereeName(referee.getString("Vards"));
            gameRefereesEntity.setRefereeSurname(referee.getString("Uzvards"));
            gameRefereesEntity.setGameId(gameEntity.getId());
            gameRefereesEntity.setRefereeType("T");
            gameRefereesDAO.save(gameRefereesEntity);

            keys = referee.keys();
            while (keys.hasNext()) {
                key = (String) keys.next();
                System.out.println("    " + key + ": " + referee.getString(key));
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Kļūda, apstrādājot tiesnešu datus:");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean saveTeamData(JSONArray team, GameEntity gameEntity)
    {

        TeamEntity teamEntity;

        System.out.println("Komandas dati");
        try {
            for (int i = 0; i < team.length(); i++) {
                JSONObject row = team.getJSONObject(i);
                String teamName = row.getString("Nosaukums");
                System.out.println("    Nosaukums: " + teamName);

                if (teamDAO.exists(teamName)) {
                    teamEntity = teamDAO.getTeamByName(teamName);
                } else {
                    teamEntity = new TeamEntity();
                    teamEntity.setTeamName(teamName);
                    teamDAO.save(teamEntity);
                }

                JSONObject teamPlayers = row.getJSONObject("Speletaji");
                saveTeamPlayersData(teamPlayers, gameEntity, teamEntity);

                JSONObject playersOnField = row.getJSONObject("Pamatsastavs");
                savePlayersOnFieldData(playersOnField, gameEntity, teamEntity);

                JSONObject teamGoals = row.optJSONObject("Varti");
                // Object
                if (teamGoals != null) {
                    saveTeamGoalsData(teamGoals, gameEntity, teamEntity);
                } else {
                    // Array
                    JSONArray teamGoalsArray = row.optJSONArray("Varti");
                    if (teamGoalsArray == null) System.out.println("Komanda nav iesitusi nevienus vārtus");
                    else System.out.println("[ERROR] Vārtu masīvs nav tukšs");
                }

                JSONObject gameChanges = row.optJSONObject("Mainas");

                // Object
                if (gameChanges != null) {
                    saveGameChangesData(gameChanges, gameEntity, teamEntity);
                } else {
                    // Array
                    JSONArray gameChangesArray = row.optJSONArray("Mainas");

                    if(gameChangesArray == null)
                        System.out.println("Spēlē nav bijušas maiņas");
                    else {
                        System.out.println("[ERROR] Spēļu maiņu masīvs nav tukšs");
                    }
                }

                JSONObject teamPenalties = row.optJSONObject("Sodi");

                // Object
                if (teamPenalties != null) {
                    saveTeamPenaltyData(teamPenalties, gameEntity, teamEntity);
                } else {
                    // Array
                    JSONArray teamPenaltiesArray = row.optJSONArray("Sodi");
                    if (teamPenaltiesArray == null) System.out.println("Komanda nav saņēmusi nevienu sodu");
                    else System.out.println("[ERROR] Sodu masīvs nav tukšs");
                }
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Kļūda, apstrādājot komandas datus:");
            e.printStackTrace();
            return false;
        }

        return true;
    }
}

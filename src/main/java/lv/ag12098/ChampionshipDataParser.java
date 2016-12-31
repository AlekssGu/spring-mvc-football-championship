package lv.ag12098;

import groovy.util.logging.Log4j;
import lv.ag12098.dao.*;
import lv.ag12098.entity.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.Timestamp;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

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

    private List<URL> generateUrlArray() {
        List<URL> jsonFiles = new ArrayList<URL>();

        jsonFiles.add(0, ChampionshipDataParser.class.getResource("/championship-data/FirstRound/futbols0.json"));
        //jsonFiles.add(1, ChampionshipDataParser.class.getResource("/championship-data/FirstRound/futbols1.json"));
        //jsonFiles.add(2, ChampionshipDataParser.class.getResource("/championship-data/FirstRound/futbols2.json"));

//        jsonFiles.add(3, ChampionshipDataParser.class.getResource("/championship-data/SecondRound/futbols0.json"));
  //      jsonFiles.add(4, ChampionshipDataParser.class.getResource("/championship-data/SecondRound/futbols1.json"));
    //    jsonFiles.add(5, ChampionshipDataParser.class.getResource("/championship-data/SecondRound/futbols2.json"));

        return jsonFiles;
    }

    public void parseJsonFile() {
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

    private boolean saveTeamData(JSONArray team, GameEntity gameEntity) {

        TeamEntity teamEntity;
        TeamPlayersEntity teamPlayersEntity;

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
                JSONArray playerData = teamPlayers.getJSONArray("Speletajs");

                for (int j = 0; j < playerData.length(); j++) {
                    JSONObject playerRow = playerData.getJSONObject(j);
                    String position = playerRow.getString("Loma");
                    Integer playerNumber = playerRow.getInt("Nr");
                    String name = playerRow.getString("Vards");
                    String surname = playerRow.getString("Uzvards");

                    // if not exists, create new player
                    if(!teamPlayersDAO.exists(name, surname, teamEntity)) {
                        teamPlayersEntity = new TeamPlayersEntity();
                        teamPlayersEntity.setPosition(position);
                        teamPlayersEntity.setName(name);
                        teamPlayersEntity.setSurname(surname);
                        teamPlayersEntity.setTeamId(teamEntity.getId());
                        teamPlayersEntity.setPlayerNumber(playerNumber);
                        teamPlayersDAO.save(teamPlayersEntity);
                    }
                }

                JSONObject playersOnField = row.getJSONObject("Pamatsastavs");
                playerData = playersOnField.getJSONArray("Speletajs");

                for (int j = 0; j < playerData.length(); j++) {
                    JSONObject playerRow = playerData.getJSONObject(j);
                    Integer playerNumber = playerRow.getInt("Nr");

                    Time time = new Time(0,0,0);

                    PlayersOnFieldEntity playersOnFieldEntity = new PlayersOnFieldEntity();
                    playersOnFieldEntity.setGameId(gameEntity.getId());
                    playersOnFieldEntity.setTimeOn(new Timestamp(time.getTime()));
                    playersOnFieldEntity.setTeamId(teamEntity.getId());
                    playersOnFieldEntity.setPlayerId(teamPlayersDAO.findByPlayerNumber(playerNumber, teamEntity).getId());
                    playersOnFieldDAO.save(playersOnFieldEntity);
                }

                JSONObject teamGoals = row.getJSONObject("Varti");
                JSONArray goalsScored = teamGoals.getJSONArray("VG");

                for (int j = 0; j < goalsScored.length(); j++) {
                    JSONObject goalRow = goalsScored.getJSONObject(j);
                    String timeScored = goalRow.getString("Laiks");
                    Integer playerNumber = goalRow.getInt("Nr");
                    String isPenalty = goalRow.getString("Sitiens");

                    String[] timesplit = timeScored.split(":");
                    Time time = new Time(0, Integer.valueOf(timesplit[0]),Integer.valueOf(timesplit[1]));

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

                JSONObject gameChanges = row.getJSONObject("Mainas");
                JSONObject changeData = gameChanges.optJSONObject("Maina");

                if (changeData != null) {
                    String timeChanged = changeData.getString("Laiks");
                    Integer playerNumberOff = changeData.getInt("Nr1");
                    Integer playerNumberOn = changeData.getInt("Nr2");
                    String[] timesplit = timeChanged.split(":");

                    Time time = new Time(0, Integer.valueOf(timesplit[0]), Integer.valueOf(timesplit[1]));

                    // new change
                    GameChangesEntity gameChangesEntity = new GameChangesEntity();
                    gameChangesEntity.setGameId(gameEntity.getId());
                    gameChangesEntity.setChangeTime(new Timestamp(time.getTime()));
                    gameChangesEntity.setPlayerOffNumber(playerNumberOff);
                    gameChangesEntity.setPlayerOnNumber(playerNumberOn);
                    gameChangesDAO.save(gameChangesEntity);

                    // update time played once player goes off the field
                    TeamPlayersEntity playerOffEntity = teamPlayersDAO.findByPlayerNumber(playerNumberOff, teamEntity);
                    PlayersOnFieldEntity playerOff = playersOnFieldDAO.getPlayerOnFieldbyPlayerId(playerOffEntity.getId());
                    playerOff.setTimeOff(new Timestamp(time.getTime()));
                    playersOnFieldDAO.save(playerOff);

                    PlayersOnFieldEntity playerOn = new PlayersOnFieldEntity();
                    playerOn.setTeamId(teamEntity.getId());
                    playerOn.setPlayerId(teamPlayersDAO.findByPlayerNumber(playerNumberOn, teamEntity).getId());
                    playerOn.setTimeOn(new Timestamp(time.getTime()));
                    playerOn.setGameId(gameEntity.getId());
                    playersOnFieldDAO.save(playerOn);
                } else {
                    //array
                    JSONArray changeDataArray = gameChanges.optJSONArray("Maina");
                }
            }
        } catch (Exception e) {
            System.out.println("[ERROR] Kļūda, apstrādājot komandas datus:");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean saveRefereeData(JSONArray linesmen, JSONObject referee, GameEntity gameEntity) {
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
}

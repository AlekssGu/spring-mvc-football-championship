package lv.ag12098.controller;

import lv.ag12098.ChampionshipDataParser;
import lv.ag12098.dao.GameDAO;
import lv.ag12098.dao.GameRefereesDAO;
import lv.ag12098.dao.TeamDAO;
import lv.ag12098.dao.TeamPlayersDAO;
import lv.ag12098.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by aleksandrs.gusevs on 2016.12.27..
 */
@Controller
public class HomeController {

    @Inject
    ChampionshipDataParser champ;

    @Inject
    TeamDAO teamDAO;

    @Inject
    TeamPlayersDAO teamPlayersDAO;

    @Inject
    GameRefereesDAO gameRefereesDAO;

    @Inject
    GameDAO gameDAO;

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private List<TeamEntity> getTeamPointsOT(List<TeamEntity> teamsList) {
        // Sakārtojam punktu secībā
        Collections.sort(teamsList, new Comparator<TeamEntity>(){
            public int compare(TeamEntity o1, TeamEntity o2){
                if(o1.getTotalPoints() == o2.getTotalPoints())
                    return 0;
                return o1.getTotalPoints() < o2.getTotalPoints() ? 1 : -1;
            }
        });

        int index = 0;
        int count = 0;
        for (TeamEntity team : teamsList) {
            // games won OT / FT
            count = ChampionshipDataParser.nvl(teamDAO.getGamesWonOT(team.getId()),0);
            team.setGamesWonOT(count);
            team.setGamesWonFT(team.getGamesWon() - count);
            // games lost OT / FT
            count = ChampionshipDataParser.nvl(teamDAO.getGamesLostOT(team.getId()),0);
            team.setGamesLostOT(count);
            team.setGamesLostFT(team.getGamesLost() - count);
            // re-set team objects in teamlist
            teamsList.set(index, team);
            index++;
        }

        return teamsList;
    }

    private List<TeamPlayersEntity> getTeamPlayerPenalties(List<TeamPlayersEntity> allTeamPlayers) {
        List<TeamPlayersEntity> mostPenalties = new ArrayList<>();

        int index = 0;
        for (TeamPlayersEntity player : allTeamPlayers) {
            int playerPenalties = teamPlayersDAO.getPlayerPenalties(player);
            player.setPenaltyCount(playerPenalties);
            allTeamPlayers.set(index, player);
            index++;
        }

        Collections.sort(allTeamPlayers, new Comparator<TeamPlayersEntity>(){
            public int compare(TeamPlayersEntity o1, TeamPlayersEntity o2){
                if(o1.getPenaltyCount() == o2.getPenaltyCount())
                    return 0;
                return o1.getPenaltyCount() < o2.getPenaltyCount() ? 1 : -1;
            }
        });

        if (allTeamPlayers.size() == 0) return null;
        else return allTeamPlayers.subList(0,10);
    }

    @RequestMapping(value={"", "/", "/home", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        //atgriež sakuma lapu
        return "static/index";
    }

    @RequestMapping(value = "/championship-table", method = RequestMethod.GET)
    public String championshipTable(Model model) {

        List<TeamEntity> teamsList = teamDAO.findAll();
        List<BestPlayersEntity> playerList = teamPlayersDAO.getFirstBest(10);
        List<BestPlayersEntity> bestGoalkeepers = teamPlayersDAO.getFirstBestGoalkeepers(5);
        List<BestRefereesEntity> refereeList = gameRefereesDAO.getAllRefereeData();
        List<TeamPlayersEntity> worstPlayers = teamPlayersDAO.findAll();

        worstPlayers = getTeamPlayerPenalties(worstPlayers);
        getTeamPointsOT(teamsList);

        model.addAttribute("teams", teamsList);
        model.addAttribute("players", playerList);
        model.addAttribute("goalKeepers", bestGoalkeepers);
        model.addAttribute("referees", refereeList);
        model.addAttribute("worstPlayers", worstPlayers);

        return "static/championship-table";
    }

    @RequestMapping(value = "/teamStatistics", params = {"id", "action"}, method = RequestMethod.GET)
    public String teamStatistics(Model model
                                , @RequestParam(value = "id") String teamId
                                , @RequestParam(value = "action") String action
                                , HttpServletRequest request) {
        try {
            // parameters
            int teamIdValue = Integer.valueOf(teamId);
            String refererURI = new URI(request.getHeader("Referer")).getPath();

            // gather data
            TeamEntity teamData = teamDAO.getTeamById(teamIdValue);
            List<TeamPlayersEntity> allPlayers = teamPlayersDAO.findAllTeamPlayers(teamIdValue);

            int index = 0;
            for (TeamPlayersEntity player : allPlayers) {
                switch (player.getPosition()) {
                    case "U":
                        player.setPosition("Uzbrucējs");
                        break;
                    case "V":
                        player.setPosition("Vārtsargs");
                        break;
                    case "A":
                        player.setPosition("Aizsargs");
                        break;
                    default:
                        break;
                }

                player.setGameCountMain(teamPlayersDAO.getPlayerGamesCount(player.getId(),"main"));
                player.setGameCountTotal(teamPlayersDAO.getPlayerGamesCount(player.getId(),"total"));
                player.setMinutesPlayed(round(teamPlayersDAO.getPlayerMinutes(player.getId()),2));
                player.setGoalsScored(teamPlayersDAO.getPlayerGoalsScored(player.getId()));
                player.setAssistsMade(teamPlayersDAO.getPlayerGoalAssists(player.getId()));
                player.setYellowCardCount(teamPlayersDAO.getPlayerPenaltyByType(player.getId(),"YELLOW"));
                player.setRedCardCount(teamPlayersDAO.getPlayerPenaltyByType(player.getId(),"RED"));
                allPlayers.set(index,player);
                index++;
            }

            model.addAttribute("teamData", teamData);
            model.addAttribute("allTeamPlayers",allPlayers);
            model.addAttribute("goBack", refererURI);

        } catch (Exception e) {
            e.printStackTrace();
            return "static/error404";
        }
        return "static/team-statistics";
    }

    @RequestMapping(value = "/load-data", method = RequestMethod.GET)
    public String loadJson(Model model, RedirectAttributes redirectAttributes) {

        int countLoaded = champ.parseJsonFile();

        List<TeamEntity> teamsList = teamDAO.findAll();
        List<BestPlayersEntity> playerList = teamPlayersDAO.getFirstBest(10);
        List<BestPlayersEntity> bestGoalkeepers = teamPlayersDAO.getFirstBestGoalkeepers(5);
        List<BestRefereesEntity> refereeList = gameRefereesDAO.getAllRefereeData();
        List<TeamPlayersEntity> allTeamPlayers = teamPlayersDAO.findAll();

        getTeamPlayerPenalties(allTeamPlayers);
        getTeamPointsOT(teamsList);

        model.addAttribute("teams", teamsList);
        model.addAttribute("players", playerList);
        model.addAttribute("goalKeepers", bestGoalkeepers);
        model.addAttribute("referees", refereeList);
        model.addAttribute("allPlayers", allTeamPlayers);

        if (countLoaded > 0)
            redirectAttributes.addFlashAttribute("successMsg", "Dati veiksmīgi ielādēti sistēmā!");
        else
            redirectAttributes.addFlashAttribute("infoMsg", "Dati netika ielādēti, jo visi faili jau ir apstrādāti!");

        //atgriež sakuma lapu
        return "redirect:/championship-table";
    }

    @RequestMapping(value = "/files-loaded", method = RequestMethod.GET)
    public String filesLoaded(Model model) {

        List<GameEntity> filesLoaded = gameDAO.findAll();
        model.addAttribute("filesLoaded", filesLoaded);

        return "static/files-loaded";
    }

    @RequestMapping(value = "/delete-all", method = RequestMethod.GET)
    public String deleteAll(Model model, RedirectAttributes redirectAttributes) {

        champ.deleteAll();
        redirectAttributes.addFlashAttribute("successMsg", "Dati veiksmīgi dzēsti!");

        return "redirect:/files-loaded";
    }
}

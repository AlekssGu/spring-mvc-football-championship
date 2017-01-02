package lv.ag12098.controller;

import lv.ag12098.ChampionshipDataParser;
import lv.ag12098.dao.GameRefereesDAO;
import lv.ag12098.dao.TeamDAO;
import lv.ag12098.dao.TeamPlayersDAO;
import lv.ag12098.entity.BestPlayersEntity;
import lv.ag12098.entity.GameRefereesEntity;
import lv.ag12098.entity.TeamEntity;
import lv.ag12098.entity.TeamPlayersEntity;
import org.hibernate.pretty.MessageHelper;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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

    @RequestMapping(value={"", "/", "/home", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        //atgriež sakuma lapu
        return "static/index";
    }

    @RequestMapping(value = "/championship-table", method = RequestMethod.GET)
    public String championshipTable(Model model) {

        List<TeamEntity> teamsList = teamDAO.findAll();
        List<BestPlayersEntity> playerList = teamPlayersDAO.getFirstBest(10);
        List<GameRefereesEntity> refereeList = gameRefereesDAO.findAll();

        getTeamPointsOT(teamsList);

        model.addAttribute("teams", teamsList);
        model.addAttribute("players", playerList);
        model.addAttribute("referees", refereeList);

        return "static/championship-table";
    }

    @RequestMapping(value = "/load-data", method = RequestMethod.GET)
    public String loadJson(Model model, RedirectAttributes redirectAttributes) {

        champ.parseJsonFile();

        List<TeamEntity> teamsList = teamDAO.findAll();
        getTeamPointsOT(teamsList);

        List<BestPlayersEntity> playerList = teamPlayersDAO.getFirstBest(10);
        List<GameRefereesEntity> refereeList = gameRefereesDAO.findAll();

        model.addAttribute("teams", teamsList);
        model.addAttribute("players", playerList);
        model.addAttribute("referees", refereeList);

        if (teamsList.size() > 0)
            redirectAttributes.addFlashAttribute("successMsg", "Dati veiksmīgi ielādēti sistēmā!");
        else
            redirectAttributes.addFlashAttribute("errorMsg", "Kļūda! Dati netika ieladēti sistēmā!");

        //atgriež sakuma lapu
        return "redirect:/championship-table";
    }
}

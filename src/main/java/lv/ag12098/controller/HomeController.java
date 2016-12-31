package lv.ag12098.controller;

import lv.ag12098.ChampionshipDataParser;
import lv.ag12098.dao.TeamDAO;
import lv.ag12098.entity.TeamEntity;
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

    @RequestMapping(value={"", "/", "/home", "/index"}, method = RequestMethod.GET)
    public String index(Model model) {
        //atgriež sakuma lapu
        return "static/index";
    }

    @RequestMapping(value = "/championship-table", method = RequestMethod.GET)
    public String championshipTable(Model model) {

        List<TeamEntity> teamsList = teamDAO.findAll();
        model.addAttribute("teams", teamsList);

        return "static/championship-table";
    }

    @RequestMapping(value = "/load-data", method = RequestMethod.GET)
    public String loadJson(Model model, RedirectAttributes redirectAttributes) {

        champ.parseJsonFile();

        List<TeamEntity> teamsList = teamDAO.findAll();
        model.addAttribute("teams", teamsList);

        if (teamsList.size() > 0)
            redirectAttributes.addFlashAttribute("successMsg", "Dati veiksmīgi ielādēti sistēmā!");
        else
            redirectAttributes.addFlashAttribute("errorMsg", "Kļūda! Dati netika ieladēti sistēmā!");

        //atgriež sakuma lapu
        return "redirect:/championship-table";
    }
}

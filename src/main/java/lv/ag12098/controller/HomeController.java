package lv.ag12098.controller;

import lv.ag12098.ChampionshipDataParser;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by aleksandrs.gusevs on 2016.12.27..
 */
@Controller
public class HomeController {

    @Inject
    ChampionshipDataParser champ;

    @RequestMapping(value={"", "/", "/home", "/index"}, method = RequestMethod.GET)
    public String index() {
        //atgriež sakuma lapu
        return "static/index";
    }

    @RequestMapping("/championship-table")
    public String championshipTable() {

        champ.parseJsonFile();

        //atgriež sakuma lapu
        return "static/championship-table";
    }
}

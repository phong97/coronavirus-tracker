package com.example.coronavirustracker.controller;

import com.example.coronavirustracker.models.LocationStats;
import com.example.coronavirustracker.services.CoronaVirusDataService;
import com.example.coronavirustracker.util.StringFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CoronaVirusDataService coronaVirusDataService;

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalCasesNumber = allStats.stream()
                .mapToInt(stat -> stat.getTotalCases()).sum();
        String totalCases = StringFormat.numberFormat(totalCasesNumber);
        int newCasesNumber = allStats.stream()
                .mapToInt(locationStats -> locationStats.getNewCases()).sum();

//        // today
//        Date date = new Date();
//        SimpleDateFormat pattern = new SimpleDateFormat("yyyy/MM/dd");
//        String today = pattern.format(date);

        String newCases = StringFormat.numberFormat(newCasesNumber);
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalCases", totalCases);
        model.addAttribute("newCases", newCases);
//        model.addAttribute("today", today);
        return "home";
    }
}

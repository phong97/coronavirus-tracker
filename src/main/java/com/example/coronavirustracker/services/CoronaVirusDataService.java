package com.example.coronavirustracker.services;

import com.example.coronavirustracker.models.LocationStats;
import lombok.Getter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CoronaVirusDataService {
    private static final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    @Getter
    private List<LocationStats> allStats = new ArrayList<>();

    @PostConstruct
    @Scheduled(fixedDelayString = "${data.refresh.time}")
    public void fetchVirusData() throws IOException {
        List<LocationStats> newStats = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response
                = restTemplate.getForEntity(VIRUS_DATA_URL, String.class);

        if (response.getBody() == null) {
            return;
        }
        StringReader in = new StringReader(response.getBody());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(in);

        int id = 0;
        for (CSVRecord record : records) {
            LocationStats locationStats = new LocationStats();
            locationStats.setId(++id);
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));
            int totalCases = 0;
            int newCases = 0;
            try {
                totalCases = Integer.parseInt(record.get(record.size() - 1));
                newCases = totalCases - Integer.parseInt(record.get(record.size() - 2));
            }  catch (NumberFormatException ex) {
                // do something
            }
            locationStats.setTotalCases(totalCases);
            locationStats.setNewCases(newCases);
            newStats.add(locationStats);
        }

        // update new stats
        allStats = newStats;
    }
}

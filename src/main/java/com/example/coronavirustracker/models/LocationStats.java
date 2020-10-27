package com.example.coronavirustracker.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public
class LocationStats {
    private int id;
    private String state;
    private String country;
    private int totalCases;
    private int newCases;

    @Override
    public String toString() {
        return "LocationState{" +
                "state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", totalCases=" + totalCases +
                '}';
    }
}

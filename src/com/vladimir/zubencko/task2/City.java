package com.vladimir.zubencko.task2;

import java.util.List;

public class City {

    private String name;

    private int index;        // index city

    private int coast;

    private List<City> cities; //list of neighbors

    public City(String name, int index, int coast) {
        this.name = name;
        this.index = index;
        this.coast = coast;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCoast() {
        return coast;
    }

    public void setCoast(int coast) {
        this.coast = coast;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
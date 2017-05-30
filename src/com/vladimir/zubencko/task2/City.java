package com.vladimir.zubencko.task2;

import java.util.List;

/**
 * City from parameters: @{@link City#name}, @{@link City#index}, @{@link City#cost}.
 *
 * @author Vladimir Zubencko
 * @version 1.0
 */
public class City {

    /**
     * Name city
     */
    private String name;

    /**
     * Index city
     */
    private int index;

    /**
     * Cost from neighborminimum
     */
    private int cost;

    private List<City> neighborCities;

    /**
     * Create new object
     */
    public City() {
    }

    /**
     * Create new object
     *
     * @param name  @{@link City#name}
     * @param index @{@link City#index}
     * @param cost  @{@link City#cost}
     */
    public City(String name, int index, int cost) {
        this.name = name;
        this.index = index;
        this.cost = cost;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<City> getNeighborCities() {
        return neighborCities;
    }

    public void setNeighborCities(List<City> neighborCities) {
        this.neighborCities = neighborCities;
    }
}

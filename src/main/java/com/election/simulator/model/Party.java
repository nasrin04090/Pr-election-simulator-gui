package com.election.simulator.model;

public class Party {
    private String name;
    private String abbreviation;
    private int votes;
    private int seats;

    public Party(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.votes = 0;
        this.seats = 0;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public int getVotes() {
        return votes;
    }

    public int getSeats() {
        return seats;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void addVote() {
        this.votes++;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        return "Party{" +
               "name=\'" + name + '\'' +
               ", abbreviation=\'" + abbreviation + '\'' +
               ", votes=" + votes +
               ", seats=" + seats +
               '}';
    }
}


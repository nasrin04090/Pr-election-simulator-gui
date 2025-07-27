package com.election.simulator.model;

public class Party {
    private String name;
    private String abbreviation;
    private int votes;
    private int seats;
    private String iconPath;

    public Party(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.votes = 0;
        this.seats = 0;
        this.iconPath = "";
    }

    public Party(String name, String abbreviation, String iconPath) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.votes = 0;
        this.seats = 0;
        this.iconPath = iconPath != null ? iconPath : "";
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

    public String getIconPath() {
        return iconPath;
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

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath != null ? iconPath : "";
    }

    @Override
    public String toString() {
        return "Party{" +
               "name=\'" + name + '\'' +
               ", abbreviation=\'" + abbreviation + '\'' +
               ", votes=" + votes +
               ", seats=" + seats +
               ", iconPath=\'" + iconPath + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Party party = (Party) obj;
        return name.equals(party.name) && abbreviation.equals(party.abbreviation);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + abbreviation.hashCode();
    }
}


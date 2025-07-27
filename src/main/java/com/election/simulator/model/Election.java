package com.election.simulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Election {
    private String electionName;
    private List<User> registeredUsers;
    private List<Party> politicalParties;
    private List<Vote> castVotes;
    private int totalSeats;

    public Election(String electionName, int totalSeats) {
        this.electionName = electionName;
        this.registeredUsers = new ArrayList<>();
        this.politicalParties = new ArrayList<>();
        this.castVotes = new ArrayList<>();
        this.totalSeats = totalSeats;
    }

    // Getters
    public String getElectionName() {
        return electionName;
    }

    public List<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public List<Party> getPoliticalParties() {
        return politicalParties;
    }

    public List<Vote> getCastVotes() {
        return castVotes;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    // Setters
    public void setElectionName(String electionName) {
        this.electionName = electionName;
    }

    public void setRegisteredUsers(List<User> registeredUsers) {
        this.registeredUsers = registeredUsers;
    }

    public void setPoliticalParties(List<Party> politicalParties) {
        this.politicalParties = politicalParties;
    }

    public void setCastVotes(List<Vote> castVotes) {
        this.castVotes = castVotes;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    // Methods for managing election data
    public void addUser(User user) {
        this.registeredUsers.add(user);
    }

    public void addParty(Party party) {
        this.politicalParties.add(party);
    }

    public void addVote(Vote vote) {
        this.castVotes.add(vote);
    }

    public int getTotalVotes() {
        return castVotes.size();
    }

    public Map<Party, Integer> getPartyVotes() {
        Map<Party, Integer> partyVotes = new HashMap<>();
        for (Party party : politicalParties) {
            partyVotes.put(party, 0);
        }
        for (Vote vote : castVotes) {
            Party votedParty = vote.getVotedParty();
            partyVotes.put(votedParty, partyVotes.get(votedParty) + 1);
        }
        return partyVotes;
    }

    @Override
    public String toString() {
        return "Election{" +
               "electionName=\'" + electionName + '\'' +
               ", totalSeats=" + totalSeats +
               ", registeredUsersCount=" + registeredUsers.size() +
               ", politicalPartiesCount=" + politicalParties.size() +
               ", castVotesCount=" + castVotes.size() +
               '}';
    }
}


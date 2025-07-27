package com.election.simulator.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Election {
    private String electionName;
    private List<Voter> registeredVoters;
    private List<Party> politicalParties;
    private List<Vote> castVotes;
    private int totalSeats;

    public Election(String electionName, int totalSeats) {
        this.electionName = electionName;
        this.registeredVoters = new ArrayList<>();
        this.politicalParties = new ArrayList<>();
        this.castVotes = new ArrayList<>();
        this.totalSeats = totalSeats;
    }

    // Getters
    public String getElectionName() {
        return electionName;
    }

    public List<Voter> getRegisteredVoters() {
        return registeredVoters;
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

    public void setRegisteredVoters(List<Voter> registeredVoters) {
        this.registeredVoters = registeredVoters;
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
    public void addVoter(Voter voter) {
        this.registeredVoters.add(voter);
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
               ", registeredVotersCount=" + registeredVoters.size() +
               ", politicalPartiesCount=" + politicalParties.size() +
               ", castVotesCount=" + castVotes.size() +
               '\n' +
               '}';
    }
}



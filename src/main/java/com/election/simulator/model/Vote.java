package com.election.simulator.model;

import java.time.LocalDateTime;

public class Vote {
    private Voter voter;
    private Party votedParty;
    private LocalDateTime timestamp;

    public Vote(Voter voter, Party votedParty) {
        this.voter = voter;
        this.votedParty = votedParty;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public Voter getVoter() {
        return voter;
    }

    public Party getVotedParty() {
        return votedParty;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Vote{" +
               "voter=" + voter.getUsername() +
               ", votedParty=" + votedParty.getName() +
               ", timestamp=" + timestamp +
               "}";
    }
}



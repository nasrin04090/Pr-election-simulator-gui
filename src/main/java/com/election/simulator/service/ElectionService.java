package com.election.simulator.service;

import com.election.simulator.model.Election;
import com.election.simulator.model.Party;
import com.election.simulator.model.Voter;
import com.election.simulator.model.Vote;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ElectionService {
    private Election currentElection;

    public ElectionService() {
        // Initialize a default election, e.g., 100 seats
        this.currentElection = new Election("General Election 2025", 100);
    }

    public Election getCurrentElection() {
        return currentElection;
    }

    public void setParties(List<Party> parties) {
        this.currentElection.setPoliticalParties(parties);
    }

    public boolean castVote(String nationalId, String partyName) {
        // Check if user has already voted
        boolean hasVoted = currentElection.getCastVotes().stream()
                .anyMatch(vote -> vote.getVoter().getNationalId().equals(nationalId));
        
        if (hasVoted) {
            System.out.println("Error: User with National ID " + nationalId + " has already voted.");
            return false;
        }
        
        // Find or create party
        Party party = currentElection.getPoliticalParties().stream()
                .filter(p -> p.getName().equals(partyName))
                .findFirst()
                .orElseGet(() -> {
                    Party newParty = new Party(partyName, partyName.substring(0, Math.min(3, partyName.length())).toUpperCase());
                    currentElection.getPoliticalParties().add(newParty);
                    return newParty;
                });
        
        // Create a temporary voter for voting (in real system, this would be the authenticated voter)
        Voter tempVoter = new Voter("voter_" + nationalId, "", "", nationalId, false);
        Vote newVote = new Vote(tempVoter, party);
        currentElection.addVote(newVote);
        party.addVote();
        
        System.out.println("Vote cast successfully for " + partyName + " by voter " + nationalId);
        return true;
    }

    public boolean castVote(Voter voter, Party party) {
        if (voter.hasVoted()) {
            System.out.println("Error: " + voter.getUsername() + " has already voted.");
            return false;
        }
        if (!currentElection.getPoliticalParties().contains(party)) {
            System.out.println("Error: Party " + party.getName() + " is not a valid party in this election.");
            return false;
        }

        Vote newVote = new Vote(voter, party);
        currentElection.addVote(newVote);
        party.addVote(); // Increment vote count for the party
        voter.setHasVoted(true);
        System.out.println(voter.getUsername() + " successfully voted for " + party.getName());
        return true;
    }

    public void calculateAndAllocateSeats() {
        int totalVotes = currentElection.getTotalVotes();
        int totalSeats = currentElection.getTotalSeats();
        List<Party> parties = currentElection.getPoliticalParties();

        if (totalVotes == 0) {
            System.out.println("No votes cast, cannot allocate seats.");
            return;
        }

        // Reset seats for all parties before recalculation
        parties.forEach(p -> p.setSeats(0));

        // Using D'Hondt method for proportional representation
        // This is a common method for seat allocation
        List<Party> sortedParties = parties.stream()
                .sorted(Comparator.comparingInt(Party::getVotes).reversed())
                .collect(Collectors.toList());

        double[] quotients = new double[sortedParties.size()];
        int[] allocatedSeats = new int[sortedParties.size()];

        for (int i = 0; i < totalSeats; i++) {
            int bestPartyIndex = -1;
            double maxQuotient = -1.0;

            for (int j = 0; j < sortedParties.size(); j++) {
                // D'Hondt formula: votes / (seats_already_allocated + 1)
                quotients[j] = (double) sortedParties.get(j).getVotes() / (allocatedSeats[j] + 1);

                if (quotients[j] > maxQuotient) {
                    maxQuotient = quotients[j];
                    bestPartyIndex = j;
                }
            }

            if (bestPartyIndex != -1) {
                allocatedSeats[bestPartyIndex]++;
            }
        }

        // Assign calculated seats back to parties
        for (int i = 0; i < sortedParties.size(); i++) {
            sortedParties.get(i).setSeats(allocatedSeats[i]);
        }

        System.out.println("Seats allocated successfully using D'Hondt method.");
    }

    public Map<Party, Integer> getElectionResults() {
        return currentElection.getPoliticalParties().stream()
                .collect(Collectors.toMap(party -> party, Party::getSeats));
    }

    public List<Party> getParties() {
        return currentElection.getPoliticalParties();
    }

    public List<Vote> getAllVotes() {
        return currentElection.getCastVotes();
    }

    // Party management methods for admin dashboard
    public void addParty(Party party) {
        if (party != null && !currentElection.getPoliticalParties().contains(party)) {
            currentElection.getPoliticalParties().add(party);
            System.out.println("Party " + party.getName() + " added successfully.");
        } else {
            System.out.println("Party already exists or is null.");
        }
    }

    public void updateParty(Party updatedParty) {
        List<Party> parties = currentElection.getPoliticalParties();
        for (int i = 0; i < parties.size(); i++) {
            Party existingParty = parties.get(i);
            if (existingParty.equals(updatedParty)) {
                // Update the existing party with new details
                existingParty.setName(updatedParty.getName());
                existingParty.setAbbreviation(updatedParty.getAbbreviation());
                existingParty.setIconPath(updatedParty.getIconPath());
                System.out.println("Party updated successfully.");
                return;
            }
        }
        System.out.println("Party not found for update.");
    }

    public void removeParty(Party party) {
        if (currentElection.getPoliticalParties().remove(party)) {
            // Also remove any votes for this party
            currentElection.getCastVotes().removeIf(vote -> vote.getVotedParty().equals(party));
            System.out.println("Party " + party.getName() + " removed successfully.");
        } else {
            System.out.println("Party not found for removal.");
        }
    }

    public Party findPartyByName(String name) {
        return currentElection.getPoliticalParties().stream()
                .filter(party -> party.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public Party findPartyByAbbreviation(String abbreviation) {
        return currentElection.getPoliticalParties().stream()
                .filter(party -> party.getAbbreviation().equalsIgnoreCase(abbreviation))
                .findFirst()
                .orElse(null);
    }

    // Election configuration methods
    public void setTotalSeats(int totalSeats) {
        if (totalSeats > 0) {
            currentElection.setTotalSeats(totalSeats);
            System.out.println("Total seats updated to " + totalSeats);
        } else {
            System.out.println("Total seats must be a positive number.");
        }
    }

    public void resetElection() {
        currentElection.getCastVotes().clear();
        currentElection.getPoliticalParties().forEach(party -> {
            party.setVotes(0);
            party.setSeats(0);
        });
        System.out.println("Election reset successfully.");
    }

    public void initializeDefaultParties() {
        if (currentElection.getPoliticalParties().isEmpty()) {
            // Add some default parties for demonstration
            addParty(new Party("Democratic Party", "DEM"));
            addParty(new Party("Republican Party", "REP"));
            addParty(new Party("Independent Party", "IND"));
            addParty(new Party("Green Party", "GRN"));
            System.out.println("Default parties initialized.");
        }
    }
}


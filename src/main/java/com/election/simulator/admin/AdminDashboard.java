package com.election.simulator.admin;

import com.election.simulator.auth.AuthService;
import com.election.simulator.model.Party;
import com.election.simulator.model.Voter;
import com.election.simulator.service.ElectionService;

import java.util.List;
import java.util.Scanner;

public class AdminDashboard {
    private AuthService authService;
    private ElectionService electionService;
    private Scanner scanner;

    public AdminDashboard(AuthService authService, ElectionService electionService) {
        this.authService = authService;
        this.electionService = electionService;
        this.scanner = new Scanner(System.in);
    }

    public void displayAdminOptions() {
        if (authService.getCurrentVoter() == null || !authService.getCurrentVoter().isAdmin()) {
            System.out.println("Access Denied: Only administrators can access this dashboard.");
            return;
        }

        System.out.println("\n--- Admin Dashboard ---");
        int choice;
        do {
            System.out.println("1. Manage Users");
            System.out.println("2. Manage Political Parties");
            System.out.println("3. Monitor Voting Activity");
            System.out.println("4. View Election Results");
            System.out.println("5. Recalculate Seats");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    manageUsers();
                    break;
                case 2:
                    manageParties();
                    break;
                case 3:
                    monitorVotingActivity();
                    break;
                case 4:
                    viewElectionResults();
                    break;
                case 5:
                    electionService.calculateAndAllocateSeats();
                    break;
                case 0:
                    authService.logout();
                    System.out.println("Logged out from Admin Dashboard.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private void manageUsers() {
        System.out.println("\n--- Voter Management ---");
        int choice;
        do {
            System.out.println("1. View All Voters");
            System.out.println("2. Add New Voter");
            System.out.println("3. Delete Voter");
            System.out.println("0. Back to Admin Dashboard");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAllUsers();
                    break;
                case 2:
                    addNewUser();
                    break;
                case 3:
                    deleteUser();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private void viewAllUsers() {
        System.out.println("\n--- All Voters ---");
        List<Voter> voters = authService.getAllVoters();
        if (voters.isEmpty()) {
            System.out.println("No voters registered.");
            return;
        }
        for (Voter voter : voters) {
            System.out.println(voter);
        }
    }

    private void addNewUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter national ID: ");
        String nationalId = scanner.nextLine();

        authService.registerVoter(username, password, fullName, nationalId);
    }

    private void deleteUser() {
        System.out.print("Enter username of voter to delete: ");
        String username = scanner.nextLine();
        authService.deleteVoter(username);
    }

    private void manageParties() {
        System.out.println("\n--- Political Party Management ---");
        int choice;
        do {
            System.out.println("1. View All Parties");
            System.out.println("2. Add New Party");
            System.out.println("0. Back to Admin Dashboard");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewAllParties();
                    break;
                case 2:
                    addNewParty();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private void viewAllParties() {
        System.out.println("\n--- All Political Parties ---");
        List<Party> parties = electionService.getParties();
        if (parties.isEmpty()) {
            System.out.println("No political parties registered.");
            return;
        }
        for (Party party : parties) {
            System.out.println(party);
        }
    }

    private void addNewParty() {
        System.out.print("Enter party name: ");
        String name = scanner.nextLine();
        System.out.print("Enter party abbreviation: ");
        String abbreviation = scanner.nextLine();

        Party newParty = new Party(name, abbreviation);
        // Add party to election service (assuming ElectionService manages parties)
        List<Party> currentParties = electionService.getParties();
        currentParties.add(newParty);
        electionService.setParties(currentParties);
        System.out.println("Party " + name + " added successfully.");
    }

    private void monitorVotingActivity() {
        System.out.println("\n--- Voting Activity Monitoring ---");
        System.out.println("Total Votes Cast: " + electionService.getCurrentElection().getTotalVotes());
        System.out.println("\nVotes by Party:");
        electionService.getCurrentElection().getPoliticalParties().forEach(party -> {
            System.out.println(party.getName() + ": " + party.getVotes() + " votes");
        });
        System.out.println("\nRecent Votes:");
        electionService.getAllVotes().forEach(vote -> {
            System.out.println(vote);
        });
    }

    private void viewElectionResults() {
        System.out.println("\n--- Election Results ---");
        electionService.calculateAndAllocateSeats(); // Ensure latest calculation
        electionService.getCurrentElection().getPoliticalParties().forEach(party -> {
            System.out.println(party.getName() + ": " + party.getVotes() + " votes, " + party.getSeats() + " seats");
        });
    }
}


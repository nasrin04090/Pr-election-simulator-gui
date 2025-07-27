package com.election.simulator;

import com.election.simulator.auth.AuthService;
import com.election.simulator.admin.AdminDashboard;
import com.election.simulator.service.ElectionService;
import com.election.simulator.model.User;
import com.election.simulator.model.Party;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        AuthService authService = new AuthService();
        ElectionService electionService = new ElectionService();
        Scanner scanner = new Scanner(System.in);

        // Add sample parties
        List<Party> parties = new ArrayList<>();
        parties.add(new Party("Party A", "PA"));
        parties.add(new Party("Party B", "PB"));
        parties.add(new Party("Party C", "PC"));
        electionService.setParties(parties);

        System.out.println("PR Election Simulator (Core Functionality with Enhanced Face Recognition Simulation) Started!");

        User currentUser = null;

        while (true) {
            if (currentUser == null) {
                System.out.println("\n--- Welcome! ---");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter username: ");
                        String regUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String regPassword = scanner.nextLine();
                        System.out.print("Enter full name: ");
                        String regFullName = scanner.nextLine();
                        System.out.print("Enter national ID: ");
                        String regNationalId = scanner.nextLine();
                        authService.registerUser(regUsername, regPassword, regFullName, regNationalId);
                        break;
                    case 2:
                        System.out.print("Enter username: ");
                        String loginUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String loginPassword = scanner.nextLine();
                        currentUser = authService.login(loginUsername, loginPassword);
                        break;
                    case 0:
                        System.out.println("Exiting application.");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else { // User is logged in
                if (currentUser.isAdmin()) {
                    AdminDashboard adminDashboard = new AdminDashboard(authService, electionService);
                    adminDashboard.displayAdminOptions();
                    currentUser = authService.getCurrentUser(); // Update current user after admin actions
                } else {
                    System.out.println("\n--- Voter Menu ---");
                    System.out.println("Welcome, " + currentUser.getFullName() + "!");
                    if (currentUser.hasVoted()) {
                        System.out.println("You have already cast your vote. Thank you!");
                    } else {
                        System.out.println("1. Cast Vote");
                    }
                    System.out.println("0. Logout");
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            if (!currentUser.hasVoted()) {
                                System.out.println("\nAvailable Parties:");
                                List<Party> availableParties = electionService.getParties();
                                for (int i = 0; i < availableParties.size(); i++) {
                                    System.out.println((i + 1) + ". " + availableParties.get(i).getName());
                                }
                                System.out.print("Enter party number to vote for: ");
                                int partyChoice = scanner.nextInt();
                                scanner.nextLine();
                                if (partyChoice > 0 && partyChoice <= availableParties.size()) {
                                    electionService.castVote(currentUser, availableParties.get(partyChoice - 1));
                                } else {
                                    System.out.println("Invalid party choice.");
                                }
                            } else {
                                System.out.println("You have already voted.");
                            }
                            break;
                        case 0:
                            authService.logout();
                            currentUser = null;
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                }
            }
        }
    }
}



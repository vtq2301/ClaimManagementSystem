package main.java.com.FP.insurance.ui;

import main.java.com.FP.insurance.dao.ClaimDAO;
import main.java.com.FP.insurance.dao.CustomerDAO;
import main.java.com.FP.insurance.dao.InsuranceCardDAO;
import main.java.com.FP.insurance.model.Claim;
import main.java.com.FP.insurance.model.Customer;
import main.java.com.FP.insurance.model.InsuranceCard;
import main.java.com.FP.insurance.service.ClaimProcess;
import main.java.com.FP.insurance.service.CustomerProcess;
import main.java.com.FP.insurance.service.InsuranceCardProcess;

import java.util.List;
import java.util.Scanner;

public class TextBasedUI {

    private List<Customer> customers;
    private List<InsuranceCard> cards;
    private List<Claim> claims;
    private static Scanner scanner = new Scanner(System.in);
    private CustomerDAO customerDAO;
    private InsuranceCardDAO insuranceCardDAO;
    private ClaimDAO claimDAO;


    public TextBasedUI() {
        this.customerDAO = new CustomerDAO();
        this.insuranceCardDAO = new InsuranceCardDAO();
        this.claimDAO = new ClaimDAO();
        loadData();
    }

    private static void header(String message) {
        String header = "";
        for (int i = 0; i < 20; i++) {
            header += "=";
        }
        System.out.println(header);
        System.out.println(message);
        System.out.println(header);
    }

    private void loadData() {
        customers = customerDAO.readAll();

        insuranceCardDAO.setCustomers(customers);
        cards = insuranceCardDAO.readAll();

        claimDAO.setCards(cards);
        claimDAO.setCustomers(customers);
        claims = claimDAO.readAll();
    }

    public void start() {
        boolean running = true;

        while (running) {
            startMenu();
        }
    }

    public void exit() {
        System.out.println("Saving data...");
        customerDAO.writeAll(customers);
        insuranceCardDAO.writeAll(cards);
        claimDAO.writeAll(claims);
        System.out.println("Exiting...");
        System.exit(0);
    }

    private void startMenu() {
        header("Welcome to Insurance Claim Management System");
        System.out.println("Choose one:");
        System.out.println("1. Claims");
        System.out.println("2. Customers");
        System.out.println("3. Insurance cards");
        System.out.println("4. Exit");
        System.out.print("Please enter your choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                claimOperations();
                break;
            case 2:
                customerOperations();
                break;
            case 3:
                insuranceCardOperations();
                break;
            case 4:
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please enter a valid number.");
        }
    }

    private void claimOperations() {
        // Implementation of claim operations...
        ClaimProcess app = new ClaimProcess(claims);
        header("Claim Management Operations");
        System.out.println("1. File a Claim");
        System.out.println("2. Update a Claim");
        System.out.println("3. Delete a Claim");
        System.out.println("4. View a Claim");
        System.out.println("5. View All Claims");
        System.out.println("6. Save Claims");
        System.out.println();
        System.out.println("0. Back");
        System.out.println("-1. Save then Exit");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                System.out.println("Enter Claim ID to delete: ");
                String id = scanner.nextLine();
                System.out.println("Are you sure you want to delete this claim?");
                app.viewOne(id);
                System.out.println("Type YES or NO: ");
                String c = scanner.nextLine();
                if (c.equalsIgnoreCase("YES")) {
                    System.out.println("Removing claim...");
                    app.delete(id);
                    System.out.println("Done removing! But the data is not saved. Would you want to save?");
                    System.out.println("> Type YES or NO: ");
                    String saveChoice = scanner.nextLine();
                    if (saveChoice.equalsIgnoreCase("YES")) {
                        System.out.println("Saving claims to file...");
                        claimDAO.writeAll(claims);
                        System.out.println("Saving done!");
                    }
                    claimOperations();
                }
            case 4:
                System.out.println("Enter claim ID: ");
                String id = scanner.nextLine();
                app.viewOne(id);
                break;
            case 5:
                app.viewAll();
                break;
            case 6:
                System.out.println("Saving claims to file...");
                claimDAO.writeAll(claims);
                System.out.println("Saving done!");
                break;
            case 0:
                startMenu();
                break;
            case -1:
                exit();
            default:
                System.out.println("Invalid choice. Please enter a valid number.");
        }
    }

    private void customerOperations() {
        // Implementation for customer operations...
        CustomerProcess app = new CustomerProcess(customers);
        header("Customer Management Operations");
        System.out.println("1. View a Customer");
        System.out.println("2. View All Customers");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.println("Enter customer ID:");
                String id = scanner.nextLine().trim();
                if (!id.isBlank()) app.viewOne(id);
                break;
            case 2:
                app.viewAll();
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid number.");
        }
    }

    private void insuranceCardOperations() {
        // Implementation for insurance card operations...
        InsuranceCardProcess app = new InsuranceCardProcess(cards);
        header("Insurance Card Management Operations");
        System.out.println("1. View an Insurance Card");
        System.out.println("2. View all Insurance Cards");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.println("Enter card number: ");
                String cardNumber = scanner.nextLine().trim();
                app.viewOne(cardNumber);
                break;
            case 2:
                app.viewAll();
            default:
                System.out.println("Invalid choice. Please enter a valid number.");
        }
    }
}

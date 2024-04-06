package main.java.com.FP.insurance.ui;

import main.java.com.FP.insurance.dao.ClaimDAO;
import main.java.com.FP.insurance.dao.CustomerDAO;
import main.java.com.FP.insurance.dao.InsuranceCardDAO;
import main.java.com.FP.insurance.model.Claim;
import main.java.com.FP.insurance.model.Customer;
import main.java.com.FP.insurance.model.InsuranceCard;
import main.java.com.FP.insurance.service.CustomerProcess;
import main.java.com.FP.insurance.service.InsuranceCardProcess;

import java.util.List;
import java.util.Scanner;

public class TextBasedUI {

    private List<Customer> customers;
    private List<InsuranceCard> cards;
    private List<Claim> claims;
    private static Scanner scanner = new Scanner(System.in);

    public TextBasedUI() {
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
        customers = (new CustomerDAO().readAll());
        InsuranceCardDAO cardDAO = new InsuranceCardDAO();
        cardDAO.setCustomers(customers);
        cards = cardDAO.readAll();
        ClaimDAO claimDAO = new ClaimDAO();
        claimDAO.setCards(cards);
        claimDAO.setCustomers(customers);
        claims = claimDAO.readAll();
    }

    public void start() {
        boolean running = true;

        while (running) {
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
    }

    private void claimOperations() {
        header("Claim Management Operations");
        // Implementation of claim operations...
        System.out.println("1. View All Claims");
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
        System.out.println("1. View an insurance card");
        System.out.println("2. View all insurance cards");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                System.out.println("Enter card number: ");
                String cardNumber = scanner.nextLine();
                app.viewOne(cardNumber);
                break;
            case 2:
                app.viewAll();
            default:
                System.out.println("Invalid choice. Please enter a valid number.");
        }
    }
}

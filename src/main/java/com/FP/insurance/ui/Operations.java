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

public abstract class Operations {
    protected List<Customer> customers;
    protected List<Claim> claims;
    protected List<InsuranceCard> cards;
    protected final CustomerDAO customerDAO;
    protected final ClaimDAO claimDAO;
    protected final InsuranceCardDAO insuranceCardDAO;
    protected CustomerProcess customerProcess;
    protected ClaimProcess claimProcess;
    protected InsuranceCardProcess insuranceCardProcess;
    protected Scanner scanner;


    public Operations(List<Customer> customers, List<Claim> claims, List<InsuranceCard> cards,
                      CustomerDAO customerDAO, ClaimDAO claimDAO, InsuranceCardDAO insuranceCardDAO,
                      Scanner scanner) {
        this.customers = customers;
        this.claims = claims;
        this.cards = cards;
        this.customerDAO = customerDAO;
        this.claimDAO = claimDAO;
        this.insuranceCardDAO = insuranceCardDAO;
        this.customerProcess = new CustomerProcess(customers);
        this.claimProcess = new ClaimProcess(claims);
        this.insuranceCardProcess = new InsuranceCardProcess(cards);
        this.scanner = scanner;
    }
    // Constructor with all dependencies
    public Operations(List<Customer> customers, List<Claim> claims, List<InsuranceCard> cards,
                      CustomerDAO customerDAO, ClaimDAO claimDAO, InsuranceCardDAO insuranceCardDAO,
                      CustomerProcess customerProcess, ClaimProcess claimProcess, InsuranceCardProcess insuranceCardProcess,
                      Scanner scanner) {
        this.customers = customers;
        this.claims = claims;
        this.cards = cards;
        this.customerDAO = customerDAO;
        this.claimDAO = claimDAO;
        this.insuranceCardDAO = insuranceCardDAO;
        this.customerProcess = customerProcess;
        this.claimProcess = claimProcess;
        this.insuranceCardProcess = insuranceCardProcess;
        this.scanner = scanner;
    }

    protected void header(String message) {
        System.out.println("=".repeat(40));
        System.out.println("*** " + message + " ***");
        System.out.println("=".repeat(40));
    }

    private void setOperations(String name, Operations o) {
        o.startMenu(name);
    }

    public void menu() {
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
                setOperations("Claim", new ClaimOperations(customers, claims, cards, customerDAO, claimDAO, insuranceCardDAO, customerProcess, claimProcess, insuranceCardProcess, scanner));
                break;
            case 2:
                setOperations("Customer", new CustomerOperations(customers, claims, cards, customerDAO, claimDAO, insuranceCardDAO, customerProcess, claimProcess, insuranceCardProcess, scanner));
                break;
            case 3:
                setOperations("Insurance Card", new InsuranceCardOperations(customers, claims, cards, customerDAO, claimDAO, insuranceCardDAO, customerProcess, claimProcess, insuranceCardProcess, scanner));
                break;
            case 4:
                saveThenExit();
            default:
                System.out.println("Invalid choice. Please enter a valid number.");
        }
    }

    public void startMenu(String type) {
        int choice;
        do {
            header(type + " Management");
            System.out.println("1. Add " + type);
            System.out.println("2. Update " + type);
            System.out.println("3. Delete " + type);
            System.out.println("4. View " + type);
            System.out.println("5. View All " + type + "s");
            System.out.println("6. Back to " + type + " Menu");
            System.out.println("7. Save");
            System.out.println("0. Save & Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addOne();
                    break;
                case 2:
                    updateOne();
                    break;
                case 3:
                    deleteOne();
                    break;
                case 4:
                    viewOne();
                    break;
                case 5:
                    viewAll();
                    break;
                case 6:
                    back();
                    break;
                case 7:
                    save();
                    break;
                case 0:
                    saveThenExit();
            }
        } while (choice != 6);
    }

    public abstract void addOne();
    public abstract void updateOne();
    public abstract void deleteOne();
    public abstract void viewOne();
    public abstract void viewAll();
    public abstract void back();
    public abstract void save();
    public abstract void saveThenExit();
}

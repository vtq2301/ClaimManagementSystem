/**
 *@author Vu Tien Quang - s3981278
 */
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
import main.java.com.FP.insurance.ui.Operations;

import java.util.List;
import java.util.Scanner;

public class CustomerOperations extends Operations {

    public CustomerOperations(List<Customer> customers, List<Claim> claims, List<InsuranceCard> cards,
                              CustomerDAO customerDAO, ClaimDAO claimDAO, InsuranceCardDAO insuranceCardDAO,
                              CustomerProcess customerProcess, ClaimProcess claimProcess, InsuranceCardProcess insuranceCardProcess,
                              Scanner scanner) {
        // Call to the superclass constructor to pass dependencies
        super(customers, claims, cards, customerDAO, claimDAO, insuranceCardDAO, customerProcess, claimProcess, insuranceCardProcess, scanner);
    }
    @Override
    public void addOne() {
        header("Register Customer");
        System.out.println("> Enter full name:");
        String fullName = scanner.next().trim();
        System.out.println("> Are you a dependent? (type 1 if correct)");
        int role = scanner.nextInt();
        scanner.nextLine();

    }

    @Override
    public void updateOne() {
        System.out.println("!!! Under Construction. Heading back to Customer Menu.");
        startMenu("Customer");
    }

    @Override
    public void deleteOne() {
        System.out.println("!!! Under Construction. Heading back to Customer Menu.");
        startMenu("Customer");
    }

    @Override
    public void viewOne() {
        header("View Customer");
        System.out.println("Enter customer ID:");
        String id = scanner.nextLine().trim();
        if (!id.isBlank()) customerProcess.viewOne(id);
    }

    @Override
    public void viewAll() {
        header("View All Customers");
        customerProcess.viewAll();
    }

    @Override
    public void back() {
        menu();
    }

    @Override
    public void save(){
        header("Saving data...");
        System.out.println("Saving Customers...");
        customerDAO.writeAll(customers);
        System.out.println("Saving Insurance Cards...");
        insuranceCardDAO.writeAll(cards);
        System.out.println("Saving Claims...");
        claimDAO.writeAll(claims);
        System.out.println("Saving complete!");
    }

    @Override
    public void saveThenExit() {
        save();
        System.out.println("Exiting...");
        System.exit(0);
    }
}
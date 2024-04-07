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

import java.util.List;
import java.util.Scanner;

public class TextBasedUI {

    private List<Customer> customers;
    private List<InsuranceCard> cards;
    private List<Claim> claims;
    private static final Scanner scanner = new Scanner(System.in);
    private final CustomerDAO customerDAO;
    private final InsuranceCardDAO insuranceCardDAO;
    private final ClaimDAO claimDAO;


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
        while (true) {
            ClaimOperations operations = new ClaimOperations(customers, claims, cards, customerDAO, claimDAO, insuranceCardDAO, scanner);
            operations.menu();
        }
    }





}

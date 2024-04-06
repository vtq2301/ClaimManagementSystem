package main.java.com.FP.insurance.dao;

import main.java.com.FP.insurance.model.Customer;
import main.java.com.FP.insurance.model.InsuranceCard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InsuranceCardDAO implements DAO<InsuranceCard> {

    private static final String INSURANCE_CARD_FILE_PATH = "src/resources/insurance-cards.txt";
    List<Customer> customers;

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
    /**
     * @return
     */
    @Override
    public List<InsuranceCard> readAll() {
        List<InsuranceCard> cards = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try (BufferedReader reader = new BufferedReader(new FileReader(INSURANCE_CARD_FILE_PATH))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String cardNumber = parts[0];
                String cardHolderId = parts[1];
                String policyOwner = parts[2];
                Date expDate = null;

                try {
                    expDate = dateFormat.parse(parts[3]);
                } catch (ParseException e) {
                    System.out.println("Error parsing the expiry date.");
                    e.printStackTrace();
                }

                Customer cardHolder = null;
                for (Customer c : customers) {
                    if (c.getId().equals(cardHolderId)) {
                        cardHolder = c;
                        break;
                    }
                }

                InsuranceCard card = new InsuranceCard(cardNumber, cardHolder, policyOwner, expDate);
                cards.add(card);

            }
        } catch (IOException e) {
            System.out.println("Error reading Insurance Card file.");
            e.printStackTrace();
        }

        return cards;
    }

    /**
     * @param data
     */
    @Override
    public void writeAll(List<InsuranceCard> data) {

    }
}

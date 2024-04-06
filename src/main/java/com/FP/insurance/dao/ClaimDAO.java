package main.java.com.FP.insurance.dao;

import main.java.com.FP.insurance.model.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ClaimDAO implements DAO<Claim>{
    private static final String CLAIMS_FILE_PATH = "src/resources/claims.txt";
    private static final String HEADER = "id,claimDate,insuredPersonId,cardNumber,examDate,documents,claimAmount,status,receiverInfo";
    private List<Customer> customers;
    private List<InsuranceCard> cards;

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<InsuranceCard> getCards() {
        return cards;
    }

    public void setCards(List<InsuranceCard> cards) {
        this.cards = cards;
    }

    /**
     * @return
     */
    @Override
    public List<Claim> readAll() {
        List<Claim> claims = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try (BufferedReader reader = new BufferedReader(new FileReader(CLAIMS_FILE_PATH))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0];
                Date claimDate = null;

                Date examDate = null;

                try {
                    claimDate = dateFormat.parse(parts[1]);
                    examDate = dateFormat.parse(parts[4]);
                } catch (ParseException e) {
                    System.out.println("Error parsing dates.");
                    e.printStackTrace();
                }

                String insuredPersonId = parts[2];
                Customer insuredPerson = null;
                for (Customer c: customers) {
                    if (c.getId().equals(insuredPersonId)) {
                        insuredPerson = c;
                        break;
                    }
                }

                String cardNumber = parts[3];
                InsuranceCard card = null;
                for (InsuranceCard c : cards) {
                    if (c.getCardNumber().equals(cardNumber)) {
                        card = c;
                        break;
                    }
                }

                String[] documentsArray = parts[5].split("/");
                List<String> documents = Arrays.stream(documentsArray).toList();

                int claimAmount = Integer.parseInt(parts[6]);
                ClaimStatus status = ClaimStatus.valueOf(parts[7]);
                String receiverInfo = parts[8];

                Claim claim = new Claim(id, claimDate, insuredPerson, card, examDate, documents,
                        claimAmount, status, receiverInfo);

                claims.add(claim);

            }
        } catch (IOException e) {
            System.out.println("Error reading claims file.");
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * @param data
     */
    @Override
    public void writeAll(List<Claim> claims) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CLAIMS_FILE_PATH))) {
            writer.write(HEADER + "\n");

            for (Claim claim : claims) {
                String claimDateStr = dateFormat.format(claim.getClaimDate());
                String examDateStr = dateFormat.format(claim.getExamDate());

                // Join documents with separator "/"
                String documentsStr = String.join("/", claim.getDocuments());

                // Write to file
                writer.write(String.format("%s,%s,%s,%s,%s,%s,%d,%s,%s\n",
                        claim.getId(),
                        claimDateStr,
                        claim.getInsuredPerson().getId(),
                        claim.getCard().getCardNumber(),
                        examDateStr,
                        documentsStr,
                        claim.getClaimAmount(),
                        claim.getStatus().name(),
                        claim.getReceiverInfo()
                ));

            }
        } catch (IOException e) {
            System.out.println("Error saving claims.");
            e.printStackTrace();
        }
    }
}

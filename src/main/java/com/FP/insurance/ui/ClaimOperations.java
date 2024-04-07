package main.java.com.FP.insurance.ui;

import main.java.com.FP.insurance.dao.ClaimDAO;
import main.java.com.FP.insurance.dao.CustomerDAO;
import main.java.com.FP.insurance.dao.InsuranceCardDAO;
import main.java.com.FP.insurance.model.Claim;
import main.java.com.FP.insurance.model.ClaimStatus;
import main.java.com.FP.insurance.model.Customer;
import main.java.com.FP.insurance.model.InsuranceCard;
import main.java.com.FP.insurance.service.ClaimProcess;
import main.java.com.FP.insurance.service.CustomerProcess;
import main.java.com.FP.insurance.service.InsuranceCardProcess;
import main.java.com.FP.insurance.ui.Operations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ClaimOperations extends Operations {

    public ClaimOperations(List<Customer> customers, List<Claim> claims, List<InsuranceCard> cards, CustomerDAO customerDAO, ClaimDAO claimDAO, InsuranceCardDAO insuranceCardDAO, Scanner scanner) {
        super(customers, claims, cards, customerDAO, claimDAO, insuranceCardDAO, scanner);
    }

    public ClaimOperations(List<Customer> customers, List<Claim> claims, List<InsuranceCard> cards,
                           CustomerDAO customerDAO, ClaimDAO claimDAO, InsuranceCardDAO insuranceCardDAO,
                           CustomerProcess customerProcess, ClaimProcess claimProcess, InsuranceCardProcess insuranceCardProcess,
                           Scanner scanner) {
        // Call to the superclass constructor to pass dependencies
        super(customers, claims, cards, customerDAO, claimDAO, insuranceCardDAO, customerProcess, claimProcess, insuranceCardProcess, scanner);
    }
    @Override
    public void addOne() {
        header("FILE A CLAIM");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        try {
            // Input claim date
            System.out.println("Please Enter the following:");
            Date claimDate = null;
            // Validate date
            while (claimDate == null || claimDate.after(today)) {
                System.out.print("> Claim Date (YYYY-MM-DD): ");
                String claimDateStr = scanner.next().trim();
                claimDate = dateFormat.parse(claimDateStr);
                if (claimDate.after(today)) {
                    System.out.println("Claim date cannot be in the future. Please try again.");
                }
            }

            // Verifying Insured Person
            Customer insuredPerson = null;
            while (insuredPerson == null) {
                System.out.print("> Insured Person Customer ID: ");
                String insuredPersonId = scanner.next().trim();
                insuredPerson = customerProcess.getOne(insuredPersonId);
                if (insuredPerson == null) {
                    System.out.println("Invalid customer ID. Please try again.");
                }
            }

            // Verifying Card Number
            InsuranceCard card = null;
            while (card == null) {
                System.out.print("> Card Number: ");
                String cardNumber = scanner.next().trim();
                card = insuranceCardProcess.getOne(cardNumber);
                if (card == null || !card.getCardHolder().getId().equals(insuredPerson.getId())) {
                    System.out.println("Invalid card number or card does not belong to the insured person. Please try again.");
                    card = null;
                }
            }

            // Input exam date
            Date examDate = null;
            // Validate date
            while (examDate == null || examDate.after(today)) {
                System.out.print("> Claim Date (YYYY-MM-DD): ");
                String claimDateStr = scanner.next().trim();
                examDate = dateFormat.parse(claimDateStr);
                if (examDate.after(today)) {
                    System.out.println("Exam date cannot be in the future. Please try again.");
                }
            }

            // Input claim amount

            System.out.print("> Claim Amount: ");
            int claimAmount = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Input receiver info
            System.out.print("> Receiver info (format: Bank-Name-Number): ");
            String receiverInfo = scanner.nextLine().trim();

            // Generate claim ID
            String id = String.format("f-%010d", claims.size() + 1);

            // Create new claim object
            Claim newClaim = new Claim(id, claimDate, insuredPerson, card, examDate, null, claimAmount,
                    ClaimStatus.NEW, receiverInfo);

            // Input documents
            System.out.println("> Documents: ");
            List<String> documents = new ArrayList<>();
            System.out.println(">> Type Document Name or leave blank to finish: ");
            while (true) {
                String docName = scanner.nextLine().trim();
                if (docName.isEmpty()) {
                    break; // Stop if user enters blank line
                }
                String doc = id + "_" + card.getCardNumber() + "_" + docName;
                documents.add(doc);
                System.out.println("Document added.");
            }
            newClaim.setDocuments(documents);

            // Add new claim to the list of claims
            claims.add(newClaim);
            System.out.printf("Claim (id: %s) added successfully.\n", newClaim.getId());
            viewOne(newClaim.getId());
        } catch (ParseException e) {
            System.out.println("Error parsing date. Please enter date in format YYYY-MM-DD.");
        } catch (InputMismatchException e){
            System.out.println("Wrong input. Please try again.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            startMenu("Claim");
        }
    }


    @Override
    public void updateOne() {
        header("UPDATE CLAIM");
        System.out.println("Enter the claim ID: ");
        String idUpdate = scanner.next().trim();
        Claim updateClaim = claimProcess.getOne(idUpdate);
        if (updateClaim == null) {
            System.out.println("Claim does not exist.");
            startMenu("Claim");
        }
        claimProcess.viewOne(idUpdate);
        System.out.println("Choose a number of what field you want to update? Enter updated data or leave blank.");
        System.out.println("1. Claim Date");
        System.out.println("2. Insured Person");
        System.out.println("3. Card Number");
        System.out.println("4. Exam Date");
        System.out.println("6. Claim Amount");
        System.out.println("7. Status");
        System.out.println("8. Receiver Info");
        System.out.println("9. Documents");
    }

    @Override
    public void deleteOne() {
        header("Removing Claim");
        System.out.println("Enter Claim ID to delete: ");
        String idDelete = scanner.nextLine();
        System.out.println("Are you sure you want to delete this claim?");
        claimProcess.viewOne(idDelete);
        System.out.println("Type YES or NO: ");
        String c = scanner.nextLine();
        if (c.equalsIgnoreCase("YES")) {
            System.out.println("Removing claim...");
            claimProcess.delete(idDelete);
            System.out.println("Done removing! But the data is not saved. Would you want to save?");
            System.out.println("> Type YES or NO: ");
            String saveChoice = scanner.nextLine();
            if (saveChoice.equalsIgnoreCase("YES")) {
                System.out.println("Saving claims to file...");
                claimDAO.writeAll(claims);
                System.out.println("Saving done!");
            }
            startMenu("Claim");
        }
    }

    @Override
    public void viewOne() {
        header("View Claim");
        System.out.println("Enter claim ID: ");
        String idView = scanner.nextLine();
        claimProcess.viewOne(idView);
    }

    @Override
    public void viewAll() {
        header("View All Claims");
        claimProcess.viewAll();
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
        System.out.println("Existing...");
        System.exit(0);
    }
}
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
            claimProcess.viewOne(newClaim.getId());
            System.out.printf("Claim (id: %s) added successfully.\n", newClaim.getId());

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
        header("UPDATE A CLAIM");

        System.out.println("> Enter the claim ID to update: ");
        String claimId = scanner.next().trim();
        scanner.nextLine();
        Claim claimToUpdate = claimProcess.getOne(claimId);
        if (claimToUpdate == null) {
            System.out.println("Claim does not exist. Returning to Claim Menu.");
            return;
        }
        claimProcess.viewOne(claimToUpdate.getId());

        try {
            // Claim Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("-".repeat(40));
            System.out.println("Current Claim Date: " + dateFormat.format(claimToUpdate.getClaimDate()));
            System.out.println("> New Claim Date (YYYY-MM-DD or press Enter to skip): ");
            String newClaimDateStr = scanner.nextLine().trim();
            if (!newClaimDateStr.isEmpty()) {
                Date newClaimDate = dateFormat.parse(newClaimDateStr);
                claimToUpdate.setClaimDate(newClaimDate);
            }

            // Insured Person
            System.out.println("-".repeat(40));
            System.out.println("Current Insured Person ID: " + claimToUpdate.getInsuredPerson().getId());
            System.out.print("> New Insured Person Customer ID (or press Enter to skip): ");
            String newInsuredPersonId = scanner.nextLine().trim();
            if (!newInsuredPersonId.isEmpty()) {
                Customer newInsuredPerson = customerProcess.getOne(newInsuredPersonId);
                if (newInsuredPerson != null) {
                    claimToUpdate.setInsuredPerson(newInsuredPerson);
                } else {
                    System.out.println("No customer found with ID: " + newInsuredPersonId);
                }
            }

            // Card Number
            System.out.println("-".repeat(40));
            System.out.println("Current Card Number: " + claimToUpdate.getCard().getCardNumber());
            System.out.println("> New Card Number (or press Enter to skip): ");
            String newCardNumber = scanner.nextLine().trim();
            if (!newCardNumber.isEmpty()) {
                InsuranceCard newCard = insuranceCardProcess.getOne(newCardNumber);
                if (newCard != null) {
                    claimToUpdate.setCard(newCard);
                } else {
                    System.out.println("No Insurance Card found with number: " + newCardNumber);
                }
            }

            // Exam Date
            System.out.println("-".repeat(40));
            System.out.println("Current Exam Date: " + dateFormat.format(claimToUpdate.getExamDate()));
            System.out.print("> New Exam Date (YYYY-MM-DD or press Enter to skip): ");
            String newExamDateStr = scanner.nextLine().trim();
            if (!newExamDateStr.isEmpty()) {
                Date newExamDate = dateFormat.parse(newExamDateStr);
                claimToUpdate.setClaimDate(newExamDate);
            }
            // Claim Amount
            System.out.println("-".repeat(40));
            System.out.println("Current Claim Amount: " + claimToUpdate.getClaimAmount());
            System.out.print("> New Claim Amount (or press Enter to skip): ");
            String claimAmountStr = scanner.nextLine().trim();
            if (!claimAmountStr.isEmpty()) {
                try {
                    int claimAmount = Integer.parseInt(claimAmountStr);
                    if (claimAmount > 0) {
                        claimToUpdate.setClaimAmount(claimAmount);
                    } else {
                        System.out.println("Claim Amount should be positive.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Claim Amount should be a positive integer.");
                }
            }

            // Status
            System.out.println("-".repeat(40));
            System.out.println("Current Claim Status: " + claimToUpdate.getStatus().name());
            System.out.println("> Choose a Claim Status:");
            System.out.println("1. NEW");
            System.out.println("2. PROCESSING");
            System.out.println("3. DONE");
            System.out.print("Enter your choice (1, 2, or 3): ");
            int statusChoice = scanner.nextInt();
            ClaimStatus status = null;

            switch (statusChoice) {
                case 1:
                    status = ClaimStatus.NEW;
                    break;
                case 2:
                    status = ClaimStatus.PROCESSING;
                    break;
                case 3:
                    status = ClaimStatus.DONE;
                    break;
                default:
                    status = claimToUpdate.getStatus();
                    break;
            }
            claimToUpdate.setStatus(status);

            // Receiver Info
            System.out.println("Current Receiver Info: " + claimToUpdate.getReceiverInfo());
            System.out.println("> New Receiver Info (or press Enter to skip): ");
            String newInfo = scanner.nextLine().trim();
            if (!newInfo.isEmpty()) {
                claimToUpdate.setReceiverInfo(newInfo);
            }

            // Find the index of the claim to be updated
            int index = -1;
            for (int i = 0; i < claims.size(); i++) {
                if (claims.get(i).getId().equals(claimToUpdate.getId())) {
                    index = i;
                    break;
                }
            }

            if (index != -1) {
                claims.set(index, claimToUpdate);
                System.out.println("Claim updated successfully.");
            } else {
                System.out.println("Claim not found. Update failed.");
            }

        } catch (ParseException e) {
            System.out.println("Error parsing date. Please enter date in format YYYY-MM-DD.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    @Override
    public void deleteOne() {
        header("REMOVING A CLAIM");
        System.out.println("Enter Claim ID to delete: ");
        String claimId = scanner.nextLine().trim();

        // Check if the claim with the provided ID exists
        Claim claimToDelete = claimProcess.getOne(claimId);
        if (claimToDelete != null) {
            System.out.println("Are you sure you want to delete this claim?");
            claimProcess.viewOne(claimId);
            System.out.println("Type YES to delete: ");
            String confirmation = scanner.nextLine().trim();

            if (confirmation.equalsIgnoreCase("YES")) {
                // Remove the claim from the list
                claims.remove(claimToDelete);
                System.out.println("Claim successfully removed.");

                // Ask the user if they want to save the changes
                System.out.println("Would you like to save the changes? (YES/NO)");
                String saveChoice = scanner.nextLine().trim();
                if (saveChoice.equalsIgnoreCase("YES")) {
                    save();
                } else {
                    System.out.println("Changes not saved in database.");
                }
            } else {
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("Claim with ID " + claimId + " not found.");
        }

        // Return to the main claim menu
        startMenu("Claim");
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
        System.out.println("Exiting...");
        System.exit(0);
    }
}
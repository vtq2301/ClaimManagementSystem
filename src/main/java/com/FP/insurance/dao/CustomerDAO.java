package main.java.com.FP.insurance.dao;

import main.java.com.FP.insurance.model.Customer;
import main.java.com.FP.insurance.model.Dependent;
import main.java.com.FP.insurance.model.PolicyHolder;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerDAO implements DAO<Customer> {
    private static final String CUSTOMER_FILE_PATH = "src/resources/customers.txt";

    private static final String HEADER = "id,fullName,cardNumber,policyHolderId";
    /**
     * Reads customer data from a file and organizes it into a list of customers.
     * This includes creating PolicyHolder and Dependent objects
     * and associating dependents with their policyholders.
     *
     * @return A list of Customer objects, including both PolicyHolders and their Dependents.
     */
    @Override
    public List<Customer> readAll() {
        Map<String, Customer> customerMap = new HashMap<>();
        Map<String, List<Dependent>> dependentsMap = new HashMap<>();

        // First pass: Create Customer Objects
        try (BufferedReader reader = new BufferedReader(new FileReader(CUSTOMER_FILE_PATH))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String id = parts[0];
                String fullName = parts[1];
                String cardNumber = parts[2];
                String policyHolderId = parts[3];

                if (id.equals(policyHolderId)){ // PolicyHolder
                    PolicyHolder policyHolder = new PolicyHolder(id, fullName, cardNumber);
                    customerMap.put(id, policyHolder);
                    // Initialize dependents list for this policy holder
                    dependentsMap.put(id, new ArrayList<>());
                } else { // Dependent
                    Dependent dependent = new Dependent(id, fullName, cardNumber);
                    // List<Dependent> dependents = dependentsMap.get(policyHolderId);
                    //                   if (dependents == null) {
                    //                        dependents = new ArrayList<>();
                    //                        dependentsMap.put(policyHolderId, dependents);
                    //                    }
                    // dependents.add(dependent);
                    // dependentsMap.put(policyHolderId, dependents);
                    // This snippet can be replaced with computeIfAbsent here:
                    dependentsMap.computeIfAbsent(policyHolderId, k -> new ArrayList<>())
                                                        .add(dependent);

                }
            }
        } catch (IOException e) {
            System.out.println("Error reading customer file.");
            e.printStackTrace();
        }

        // Second Pass: Associate Dependents with PolicyHolders
        dependentsMap.forEach((policyHolderId, dependents) -> {
            if (customerMap.containsKey(policyHolderId)) {
                PolicyHolder policyHolder = (PolicyHolder) customerMap.get(policyHolderId);
                dependents.forEach(d -> {
                    policyHolder.addDependent(d);
                    d.setPolicyHolder(policyHolder);
                    customerMap.put(d.getId(), d); // add Dependent to customers
                });
            }
        });

        return new ArrayList<>(customerMap.values());
    }

    /**
     * @param data
     */
    @Override
    public void writeAll(List<Customer> customers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_FILE_PATH))) {
            writer.write(HEADER + "\n");

            for (Customer customer : customers) {
                String policyHolderId;
                if (customer.getPolicyHolder() != null){
                    policyHolderId = customer.getPolicyHolder().getId();
                } else {
                    policyHolderId = customer.getId();
                }

                writer.write(String.format("%s,%s,%s,%s\n",
                        customer.getId(),
                        customer.getFullName(),
                        customer.getCardNumber(),
                        policyHolderId
                ));
            }
        } catch (IOException e) {
            System.out.println("Error saving customers.");
            e.printStackTrace();
        }

    }
}

package main.java.com.FP.insurance.service;

import main.java.com.FP.insurance.model.Customer;

import java.util.List;

public class CustomerProcess implements ProcessManager<Customer> {
    private List<Customer> customers;

    public CustomerProcess(List<Customer> customers) {
        this.customers = customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * @param newData
     */
    @Override
    public void add(Customer newData) {
        customers.add(newData);
    }

    /**
     * @param id
     * @param updatedData
     */
    @Override
    public void update(String id, Customer updatedData) {
        delete(id);
        add(updatedData);

    }

    /**
     * @param id
     * @return
     */
    @Override
    public String delete(String id) {
        Customer customerToDelete = getOne(id);
        customers.remove(customerToDelete);
        return customerToDelete.getId();

    }

    /**
     * @param id
     * @return
     */
    @Override
    public Customer getOne(String id) {
        for (Customer c: customers) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;

    }
    @Override
    public List<Customer> getAll() {
        return this.customers;
    }

    @Override
    public void viewOne(String id) {
        Customer customer = getOne(id);
        System.out.println("Customer ID\tFull Name\tCard Number");
        System.out.print(customer.getId() + "\t");
        System.out.print(customer.getFullName() + "\t");
        System.out.print(customer.getCardNumber() + "\n");
        customer.printDependents();
    }

    @Override
    public void viewAll() {
        // Print header
        System.out.println("Customer ID\tFull Name\tCard Number");
        int i = 1;
        for (Customer customer : customers) {
            System.out.print(i + "\t");
            System.out.print(customer.getId() + "\t");
            System.out.print(customer.getFullName() + "\t");
            System.out.print(customer.getCardNumber() + "\n");
            customer.printDependents();
            i++;
//            System.out.println();

        }
    }
}

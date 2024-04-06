package main.java.com.FP.insurance.model;

import java.security.Policy;
import java.util.ArrayList;
import java.util.List;

public class PolicyHolder extends Customer {
    private List<Dependent> dependents;


    public PolicyHolder(String id, String fullName, String cardNumber) {
        super(id, fullName, cardNumber);
        this.dependents = new ArrayList<>();
    }

    public PolicyHolder(String id, Dependent dep) {
        super(id, null, null);
        this.dependents = new ArrayList<>();
        this.dependents.add(dep);
    }

    @Override
    public void printDependents() {
        if (dependents.isEmpty()) {
            System.out.println("No Dependent.");
        } else {
            for (int i = 0; i < dependents.size(); i++) {
                Dependent dependent = dependents.get(i);
                System.out.println(i + ". " + dependent);

            }
        }
    }

    /**
     * @return
     */
    @Override
    public PolicyHolder getPolicyHolder() {
        return null;
    }

    @Override
    public void addDependent(Dependent dep) {
        if (!dependents.contains(dep)) {
            dependents.add(dep);
        } else {
            System.out.println(dep.getFullName() + "is one of the dependents.");
        }
    }

}

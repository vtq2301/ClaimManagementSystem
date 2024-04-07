/**
 *@author Vu Tien Quang - s3981278
 */
package main.java.com.FP.insurance.model;

public class Dependent extends Customer {
    private PolicyHolder policyHolder;

    public Dependent() {}

    public Dependent(String id, String fullName, String cardNumber) {
        super(id, fullName, cardNumber);
        this.policyHolder = null;
    }

    /**
     * @param dep
     */
    @Override
    public void addDependent(Dependent dep) {
        return;
    }

    /**
     *
     */
    @Override
    public void printDependents() {

    }

    public Dependent(String id, String fullName, String cardNumber, PolicyHolder policyHolder) {
        super(id, fullName, cardNumber);
        this.policyHolder = policyHolder;
    }

    public PolicyHolder getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(PolicyHolder policyHolder) {
        this.policyHolder = policyHolder;
    }
}

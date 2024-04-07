/**
 *@author Vu Tien Quang - s3981278
 */
package main.java.com.FP.insurance.model;

import java.util.List;

public abstract class Customer {
    private String id;
    private String fullName;
    private String cardNumber;

    public Customer() {};

    public Customer(String id, String fullName, String cardNumber) {
        this.id = id;
        this.fullName = fullName;
        this.cardNumber = cardNumber;
    }

    public abstract void addDependent(Dependent dep);
    public abstract void printDependents();

    public abstract PolicyHolder getPolicyHolder();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", cardNumber=" + cardNumber + '\'' +
                '}';
    }
}

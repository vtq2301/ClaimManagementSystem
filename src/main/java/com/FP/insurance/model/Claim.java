package main.java.com.FP.insurance.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;

public class Claim {
    private String id;
    private Date claimDate;
    private Date examDate;
    private Customer insuredPerson;
    private InsuranceCard card;
    private List<String> documents;
    int claimAmount;
    private ClaimStatus status;
    private String receiverInfo; // format: bank-name-number

    public Claim(Date claimDate, Date examDate, Customer insuredPerson, InsuranceCard card, int claimAmount, ClaimStatus status, String receiverInfo) {
        this.claimDate = claimDate;
        this.examDate = examDate;
        this.insuredPerson = insuredPerson;
        this.card = card;
        this.claimAmount = claimAmount;
        this.status = status;
        this.receiverInfo = receiverInfo;
        this.id = null;
    }

    public Claim(String id, Date claimDate, Customer insuredPerson, InsuranceCard card, Date examDate, List<String> documents, int claimAmount, ClaimStatus status, String receiverInfo) {
        this.id = id;
        this.claimDate = claimDate;
        this.examDate = examDate;
        this.insuredPerson = insuredPerson;
        this.card = card;
        this.documents = documents;
        this.claimAmount = claimAmount;
        this.status = status;
        this.receiverInfo = receiverInfo;
    }

    public String getId() {
        return id;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public Date getExamDate() {
        return examDate;
    }

    public Customer getInsuredPerson() {
        return insuredPerson;
    }

    public InsuranceCard getCard() {
        return card;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public int getClaimAmount() {
        return claimAmount;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public String getReceiverInfo() {
        return receiverInfo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public void setInsuredPerson(Customer insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public void setCard(InsuranceCard card) {
        this.card = card;
    }

    public void setClaimAmount(int claimAmount) {
        this.claimAmount = claimAmount;
    }

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }

    public void setReceiverInfo(String receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public void setDocuments(List<String> documents) {
        this.documents = documents;
    }

    @Override
    public String toString() {
        return "Claim{" +
                "id='" + id + '\'' +
                ", claimDate=" + claimDate +
                ", examDate=" + examDate +
                ", insuredPerson=" + insuredPerson +
                ", card=" + card +
                ", documents=" + documents +
                ", status=" + status +
                ", receiverInfo='" + receiverInfo + '\'' +
                '}';
    }
}

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

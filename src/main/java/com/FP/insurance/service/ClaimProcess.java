/**
 *@author Vu Tien Quang - s3981278
 */
package main.java.com.FP.insurance.service;

import main.java.com.FP.insurance.model.Claim;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClaimProcess implements ProcessManager<Claim> {

    private List<Claim> claims;

    public ClaimProcess(List<Claim> claims) {
        this.claims = claims;
    }

    /**
     * @param newData
     */
    @Override
    public void add(Claim newData) {
        claims.add(newData);
    }

    /**
     * @param id
     * @param updatedData
     */
    @Override
    public void update(String id, Claim updatedData) {
        delete(id);
        add(updatedData);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public String delete(String id) {
        Claim claimToDelete = getOne(id);
        claims.remove(claimToDelete);
        return claimToDelete.getId();

    }

    /**
     * @param id
     * @return
     */
    @Override
    public Claim getOne(String id) {
        for (Claim c : claims) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    /**
     * @return
     */
    @Override
    public List<Claim> getAll() {
        return claims;
    }

    /**
     * @param id
     */
    @Override
    public void viewOne(String id) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Claim claim = getOne(id);
        System.out.println("ID\tClaim Date\tExam Date\tClaim Amount\tStatus");
        System.out.println(claim.getId() + "\t");
        System.out.print(dateFormat.format(claim.getClaimDate()) + "\t");
        System.out.print(dateFormat.format(claim.getExamDate()) + "\t");
        System.out.print("$"+ claim.getClaimAmount() + "\t");
        System.out.print(claim.getStatus().name() + "\t");

        System.out.println("Insured Person Information:");
        System.out.println("> " + claim.getInsuredPerson());
        System.out.println("Receiver Info (Bank - Name - Number):" + claim.getReceiverInfo());

        System.out.println("Documents: ");
        claim.getDocuments().forEach(System.out::println);
    }

    /**
     *
     */
    @Override
    public void viewAll() {
        System.out.println("No.\tID\tClaim Date\tInsured Person ID\tExam Date\tClaim Amount\tStatus\n");
        int i = 1;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // sort by ID
        claims.sort(Comparator.comparing(Claim::getId));
        for (Claim claim : claims) {
            System.out.print(i + "\t");
            System.out.print(claim.getId() + "\t");
            System.out.print(dateFormat.format(claim.getClaimDate()) + "\t");
            System.out.print(claim.getInsuredPerson().getId() + "\t");
            System.out.print(dateFormat.format(claim.getExamDate()) + "\t");
            System.out.print("$"+ claim.getClaimAmount() + "\t");
            System.out.print(claim.getStatus().name() + "\n");
            System.out.println("> Documents: ");
            claim.getDocuments().forEach(System.out::println);
            i++;
        }
    }

}

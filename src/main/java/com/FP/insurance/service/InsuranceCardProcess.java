/**
 *@author Vu Tien Quang - s3981278
 */
package main.java.com.FP.insurance.service;

import main.java.com.FP.insurance.model.InsuranceCard;

import java.util.List;

public class InsuranceCardProcess implements ProcessManager<InsuranceCard> {
    private List<InsuranceCard> cards;

    public InsuranceCardProcess(List<InsuranceCard> cards) {
        this.cards = cards;
    }

    /**
     * @param newData
     */
    @Override
    public void add(InsuranceCard newData) {
        cards.add(newData);
    }

    /**
     * @param id
     * @param updatedData
     */
    @Override
    public void update(String id, InsuranceCard updatedData) {

    }

    /**
     * @param id
     * @return
     */
    @Override
    public String delete(String id) {
        InsuranceCard cardToDelete = getOne(id);
        cards.remove(cardToDelete);
        return cardToDelete.getCardNumber();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public InsuranceCard getOne(String id) {
        for (InsuranceCard c : cards) {
            if (c.getCardNumber().equals(id)) {
                return c;
            }
        }
        return null;
    }

    /**
     * @return
     */
    @Override
    public List<InsuranceCard> getAll() {
        return cards;
    }

    /**
     * @param id
     */
    @Override
    public void viewOne(String id) {
        InsuranceCard card = getOne(id);
        System.out.println("Card Number\tPolicy Owner\texpiration Date");
        System.out.println(card.getCardNumber() + "\t" +
                card.getPolicyOwner() + "\t" +
                card.getExpDate().toString());
        System.out.println("Card Holder Information:");
        System.out.println(card.getCardHolder());
    }

    public void viewAll() {
        System.out.println("No.\tCard Number\tCard Holder ID\tPolicy Owner\texpiration Date");
        int i = 1;
        for (InsuranceCard c : cards) {
            System.out.print(i + "\t");
            System.out.print(c.getCardNumber() + "\t");
            System.out.print(c.getCardHolder().getId() + "\t");
            System.out.print(c.getPolicyOwner() + "\t");
            System.out.print(c.getExpDate().toString() + "\n");
            i++;
        }
    }
}

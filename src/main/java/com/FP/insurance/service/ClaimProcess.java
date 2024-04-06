package main.java.com.FP.insurance.service;

import main.java.com.FP.insurance.model.Claim;

import java.util.List;

public class ClaimProcess implements ProcessManager<Claim> {

    private List<Claim> claims;


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
        Claim claim = getOne(id);

    }

    /**
     *
     */
    @Override
    public void viewAll() {

    }
}

package dao;

import entities.Prestito;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class PrestitiDAO {

    private final EntityManager em;

    public PrestitiDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Prestito nuovoPrestito) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(nuovoPrestito);
        transaction.commit();
        System.out.println("Prestito salvato correttamente!");
    }
}
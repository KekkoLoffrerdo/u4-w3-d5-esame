package dao;

import entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class UtentiDAO {

    private final EntityManager em;

    public UtentiDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Utente nuovoUtente) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(nuovoUtente);
        transaction.commit();
        System.out.println("Utente salvato correttamente!");
    }
}
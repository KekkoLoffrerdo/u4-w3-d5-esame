package catalogo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Application {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("catalogoPU");

    public static void main(String[] args) {
        EntityManager entityManager = emf.createEntityManager();

        System.out.println("Connessione avviata correttamente!");
        System.out.println("Controlla pgAdmin: Hibernate dovrebbe aver creato le tabelle.");

        entityManager.close();
        emf.close();
    }
}

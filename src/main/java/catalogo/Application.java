package catalogo;

import dao.CatalogoDAO;
import dao.NotFoundException;
import dao.PrestitiDAO;
import dao.UtentiDAO;
import entities.Libro;
import entities.Periodicita;
import entities.Prestito;
import entities.Rivista;
import entities.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Application {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("catalogoPU");

    public static void main(String[] args) {
        EntityManager entityManager = emf.createEntityManager();

        CatalogoDAO catalogoDAO = new CatalogoDAO(entityManager);
        UtentiDAO utentiDAO = new UtentiDAO(entityManager);
        PrestitiDAO prestitiDAO = new PrestitiDAO(entityManager);

        Libro libro1 = new Libro("ISBN001", "Harry Potter e La Pietra Filosofale", 1997, 223, "J.K. ROWLING", "Fantasy");
        Libro libro2 = new Libro("ISBN002", "HARRY POTTER E LA CAMERA DEI SEGRETI", 1998, 251, "J.K. ROWLING", "Distopico");
        Rivista rivista1 = new Rivista("ISBN003", "National Geographic", 2024, 100, Periodicita.MENSILE);

        Utente utente1 = new Utente("Francesco", "Loffredo", LocalDate.of(2000, 7, 24), "TESS001");
        Utente utente2 = new Utente("Aldo", "Baglio", LocalDate.of(1958, 9, 28), "TESS002");


       catalogoDAO.save(libro1);
        catalogoDAO.save(libro2);
        catalogoDAO.save(rivista1);

        utentiDAO.save(utente1);
        utentiDAO.save(utente2);

        Prestito prestito1 = new Prestito(utente1, libro1, LocalDate.now().minusDays(40));
        Prestito prestito2 = new Prestito(utente2, rivista1, LocalDate.now().minusDays(10));

        prestitiDAO.save(prestito1);
        prestitiDAO.save(prestito2);

        try {
            System.out.println("RICERCA PER ISBN:");
            System.out.println(catalogoDAO.findByISBN("ISBN001"));
        } catch (NotFoundException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("RICERCA PER ANNO PUBBLICAZIONE:");
        catalogoDAO.findByAnnoPubblicazione(1949).forEach(System.out::println);

        System.out.println("RICERCA PER AUTORE:");
        catalogoDAO.findByAutore("George Orwell").forEach(System.out::println);

        System.out.println("RICERCA PER TITOLO O PARTE DI ESSO:");
        catalogoDAO.findByTitoloOrParteDiEsso("signore").forEach(System.out::println);

        System.out.println("ELEMENTI ATTUALMENTE IN PRESTITO PER NUMERO TESSERA:");
        catalogoDAO.findElementiAttualmenteInPrestitoByNumeroTessera("TESS001").forEach(System.out::println);

        System.out.println("PRESTITI SCADUTI E NON RESTITUITI:");
        catalogoDAO.findPrestitiScadutiENonRestituiti().forEach(System.out::println);


        entityManager.close();
        emf.close();
    }
}

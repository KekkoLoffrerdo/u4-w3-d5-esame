package dao;

import entities.ElementoCatalogo;
import entities.Libro;
import entities.Prestito;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class CatalogoDAO {

    private final EntityManager em;

    public CatalogoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(ElementoCatalogo nuovoElemento) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(nuovoElemento);
        transaction.commit();
        System.out.println("L'elemento con ISBN " + nuovoElemento.getIsbn() + " è stato salvato correttamente!");
    }
    public ElementoCatalogo findByISBN(String isbn) {
        TypedQuery<ElementoCatalogo> query = em.createQuery(
                "SELECT e FROM ElementoCatalogo e WHERE e.isbn = :isbn",
                ElementoCatalogo.class
        );
        query.setParameter("isbn", isbn);

        List<ElementoCatalogo> risultati = query.getResultList();

        if (risultati.isEmpty()) {
            throw new NotFoundException("Elemento con ISBN " + isbn + " non trovato");
        }

        return risultati.get(0);
    }
    public void deleteByISBN(String isbn) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Query query = em.createQuery("DELETE FROM ElementoCatalogo e WHERE e.isbn = :isbn");
        query.setParameter("isbn", isbn);

        int deleted = query.executeUpdate();
        transaction.commit();

        if (deleted == 0) {
            throw new NotFoundException("Nessun elemento trovato con ISBN " + isbn);
        }

        System.out.println("Elemento con ISBN " + isbn + " eliminato correttamente!");
    }
    public List<ElementoCatalogo> findByAnnoPubblicazione(int anno) {
        TypedQuery<ElementoCatalogo> query = em.createQuery(
                "SELECT e FROM ElementoCatalogo e WHERE e.annoPubblicazione = :anno",
                ElementoCatalogo.class
        );
        query.setParameter("anno", anno);
        return query.getResultList();
    }
    public List<Libro> findByAutore(String autore) {
        TypedQuery<Libro> query = em.createQuery(
                "SELECT l FROM Libro l WHERE LOWER(l.autore) = LOWER(:autore)",
                Libro.class
        );
        query.setParameter("autore", autore);
        return query.getResultList();
    }
    public List<ElementoCatalogo> findByTitoloOrParteDiEsso(String titoloParziale) {
        TypedQuery<ElementoCatalogo> query = em.createQuery(
                "SELECT e FROM ElementoCatalogo e WHERE LOWER(e.titolo) LIKE LOWER(:titolo)",
                ElementoCatalogo.class
        );
        query.setParameter("titolo", "%" + titoloParziale + "%");
        return query.getResultList();
    }
    public List<ElementoCatalogo> findElementiAttualmenteInPrestitoByNumeroTessera(String numeroTessera) {
        TypedQuery<ElementoCatalogo> query = em.createQuery(
                "SELECT p.elementoPrestato FROM Prestito p " +
                        "WHERE p.utente.numeroTessera = :numeroTessera " +
                        "AND p.dataRestituzioneEffettiva IS NULL",
                ElementoCatalogo.class
        );
        query.setParameter("numeroTessera", numeroTessera);
        return query.getResultList();
    }
    public List<Prestito> findPrestitiScadutiENonRestituiti() {
        TypedQuery<Prestito> query = em.createQuery(
                "SELECT p FROM Prestito p " +
                        "WHERE p.dataRestituzionePrevista < :oggi " +
                        "AND p.dataRestituzioneEffettiva IS NULL",
                Prestito.class
        );
        query.setParameter("oggi", LocalDate.now());
        return query.getResultList();
    }
}

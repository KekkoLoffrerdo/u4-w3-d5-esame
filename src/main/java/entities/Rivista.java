package entities;

import jakarta.persistence.*;

@Entity
@Table(name = "riviste")
public class Rivista extends ElementoCatalogo {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Periodicita periodicita;

    protected Rivista() {
    }

    public Rivista(String isbn, String titolo, int annoPubblicazione, int numeroPagine, Periodicita periodicita) {
        super(isbn, titolo, annoPubblicazione, numeroPagine);
        this.periodicita = periodicita;
    }

    public Periodicita getPeriodicita() {
        return periodicita;
    }

    @Override
    public String toString() {
        return "Rivista{" +
                "periodicita=" + periodicita +
                "} " + super.toString();
    }
}

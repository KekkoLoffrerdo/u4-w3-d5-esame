package entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "elementi_catalogo")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ElementoCatalogo {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String titolo;

    @Column(name = "anno_pubblicazione", nullable = false)
    private int annoPubblicazione;

    @Column(name = "numero_pagine", nullable = false)
    private int numeroPagine;

    protected ElementoCatalogo() {
    }

    public ElementoCatalogo(String isbn, String titolo, int annoPubblicazione, int numeroPagine) {
        this.isbn = isbn;
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.numeroPagine = numeroPagine;
    }

    public UUID getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitolo() {
        return titolo;
    }

    public int getAnnoPubblicazione() {
        return annoPubblicazione;
    }

    public int getNumeroPagine() {
        return numeroPagine;
    }

    @Override
    public String toString() {
        return "ElementoCatalogo{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", titolo='" + titolo + '\'' +
                ", annoPubblicazione=" + annoPubblicazione +
                ", numeroPagine=" + numeroPagine +
                '}';
    }
}

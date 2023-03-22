/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.hsog;

import javax.swing.Icon;

/**
 *
 * @author student
 */
public class DTO {

    private String isbn;
    private String email;
    private String title;
    private Icon cover;
    private String kategorie;
    private int seitenanzahl;
    //private String kommentar;
    //private double bewertung;

    public DTO(String isbn, String email, String title, Icon cover, String kategorie, int seitenanzahl) {
        this.isbn = isbn;
        this.email = email;
        this.title = title;
        this.cover = cover;
        this.kategorie = kategorie;
        this.seitenanzahl = seitenanzahl;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;

    }

    public Icon getCover() {
        return cover;
    }

    public String getKategorie() {
        return kategorie;
    }

    public int getSeitenanzahl() {
        return seitenanzahl;
    }
/*
    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }

    public void setBewertung(double bewertung) {
        this.bewertung = bewertung;
    }

    public double getBewertung() {
        return bewertung;
    }
*/
}

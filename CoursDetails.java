/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetdegestion;

import java.time.LocalDate;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author anasa
 */
public class CoursDetails {
    private final SimpleStringProperty nomEleve;
    private final SimpleIntegerProperty nombreHeures;
    private final SimpleStringProperty typeSeance;
    private final SimpleObjectProperty<LocalDate> date;

    public CoursDetails(String nomEleve, int nombreHeures, String typeSeance, LocalDate date) {
        this.nomEleve = new SimpleStringProperty(nomEleve);
        this.nombreHeures = new SimpleIntegerProperty(nombreHeures);
        this.typeSeance = new SimpleStringProperty(typeSeance);
        this.date = new SimpleObjectProperty<>(date);
    }
    
    public String getNomEleve() {
        return nomEleve.get();
    }

    public void setNomEleve(String nomEleve) {
        this.nomEleve.set(nomEleve);
    }

    public SimpleStringProperty nomEleveProperty() {
        return nomEleve;
    }

    // Getter and setter for nombreHeures
    public int getNombreHeures() {
        return nombreHeures.get();
    }

    public void setNombreHeures(int nombreHeures) {
        this.nombreHeures.set(nombreHeures);
    }

    public SimpleIntegerProperty nombreHeuresProperty() {
        return nombreHeures;
    }

    // Getter and setter for typeSeance
    public String getTypeSeance() {
        return typeSeance.get();
    }

    public void setTypeSeance(String typeSeance) {
        this.typeSeance.set(typeSeance);
    }

    public SimpleStringProperty typeSeanceProperty() {
        return typeSeance;
    }

    // Getter and setter for date
    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public SimpleObjectProperty<LocalDate> dateProperty() {
        return date;
    }
}

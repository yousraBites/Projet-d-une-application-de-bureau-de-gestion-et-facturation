/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetdegestion;


import java.time.LocalDate;

public class Course {
    private int id;
    private int eleveId;
    private int nombreHeures;
    private String typeSeance;
    private LocalDate date;

    // Constructor
    public Course(int id, int eleveId, int nombreHeures, String typeSeance, LocalDate date) {
        this.id = id;
        this.eleveId = eleveId;
        this.nombreHeures = nombreHeures;
        this.typeSeance = typeSeance;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEleveId() {
        return eleveId;
    }

    public void setEleveId(int eleveId) {
        this.eleveId = eleveId;
    }

    public int getNombreHeures() {
        return nombreHeures;
    }

    public void setNombreHeures(int nombreHeures) {
        this.nombreHeures = nombreHeures;
    }

    public String getTypeSeance() {
        return typeSeance;
    }

    public void setTypeSeance(String typeSeance) {
        this.typeSeance = typeSeance;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", eleveId=" + eleveId +
                ", nombreHeures=" + nombreHeures +
                ", typeSeance='" + typeSeance + '\'' +
                ", date=" + date +
                '}';
    }
}

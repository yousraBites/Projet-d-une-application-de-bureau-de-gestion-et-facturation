/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetdegestion;

import javafx.beans.property.SimpleStringProperty;



public class Eleves {
    private final SimpleStringProperty nom;
    private final SimpleStringProperty type; // "Individuel" (oui/non)
    private final SimpleStringProperty nomParent;
    private final SimpleStringProperty phone;
    private final SimpleStringProperty mail;
    private final SimpleStringProperty classe;
    private final SimpleStringProperty matiere;
    private final SimpleStringProperty acquitte; // Dernier mois payé
    private final SimpleStringProperty debut;
    private final SimpleStringProperty jours;

    public Eleves(String nom, String type, String nomParent, String phone, String mail, 
                  String classe, String matiere, String acquitte, String debut, String jours) {
        this.nom = new SimpleStringProperty(nom);
        this.type = new SimpleStringProperty(type.equals("Individuel") ? "oui" : "non");
        this.nomParent = new SimpleStringProperty(nomParent);
        this.phone = new SimpleStringProperty(phone);
        this.mail = new SimpleStringProperty(mail);
        this.classe = new SimpleStringProperty(classe);
        this.matiere = new SimpleStringProperty(matiere);
        this.acquitte = new SimpleStringProperty(acquitte);  // Exemple: "Mars 2024"
        this.debut = new SimpleStringProperty(debut);
        this.jours = new SimpleStringProperty(jours);
    }

    public String getNom() {
        return nom.get();
    }

    public String getType() {
        return type.get();
    }

    public String getNomParent() {
        return nomParent.get();
    }

    public String getPhone() {
        return phone.get();
    }

    public String getMail() {
        return mail.get();
    }

    public String getClasse() {
        return classe.get();
    }

    public String getMatiere() {
        return matiere.get();
    }

    public String getAcquitte() {
        return acquitte.get();
    }

    public String getDebut() {
        return debut.get();
    }

    public String getJours() {
        return jours.get();
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetdegestion;


public class Parameterss {
    private String nomGestionnaire;
    private String prenomGestionnaire;
    private String adresseGestionnaire;
    private String emailGestionnaire;
    private String telephoneGestionnaire;
    private String numeroSiret;
    private String nomAssociation;
    private String adresseAssociation;
    private String emailAssociation;
    private String telephoneAssociation;
    private float prixIndividuel;
    private float prixGroupe;
    private String mentionLegale;

    public Parameterss(String nomGestionnaire, String prenomGestionnaire, String adresseGestionnaire, String emailGestionnaire, String telephoneGestionnaire, String numeroSiret, String nomAssociation, String adresseAssociation, String emailAssociation, String telephoneAssociation, float prixIndividuel, float prixGroupe, String mentionLegale) {
        this.nomGestionnaire = nomGestionnaire;
        this.prenomGestionnaire = prenomGestionnaire;
        this.adresseGestionnaire = adresseGestionnaire;
        this.emailGestionnaire = emailGestionnaire;
        this.telephoneGestionnaire = telephoneGestionnaire;
        this.numeroSiret = numeroSiret;
        this.nomAssociation = nomAssociation;
        this.adresseAssociation = adresseAssociation;
        this.emailAssociation = emailAssociation;
        this.telephoneAssociation = telephoneAssociation;
        this.prixIndividuel = prixIndividuel;
        this.prixGroupe = prixGroupe;
        this.mentionLegale = mentionLegale;
    }


    public String getNomGestionnaire() {
        return nomGestionnaire;
    }

    public void setNomGestionnaire(String nomGestionnaire) {
        this.nomGestionnaire = nomGestionnaire;
    }

    public String getPrenomGestionnaire() {
        return prenomGestionnaire;
    }

    public void setPrenomGestionnaire(String prenomGestionnaire) {
        this.prenomGestionnaire = prenomGestionnaire;
    }

    public String getAdresseGestionnaire() {
        return adresseGestionnaire;
    }

    public void setAdresseGestionnaire(String adresseGestionnaire) {
        this.adresseGestionnaire = adresseGestionnaire;
    }

    public String getEmailGestionnaire() {
        return emailGestionnaire;
    }

    public void setEmailGestionnaire(String emailGestionnaire) {
        this.emailGestionnaire = emailGestionnaire;
    }

    public String getTelephoneGestionnaire() {
        return telephoneGestionnaire;
    }

    public void setTelephoneGestionnaire(String telephoneGestionnaire) {
        this.telephoneGestionnaire = telephoneGestionnaire;
    }

    public String getNumeroSiret() {
        return numeroSiret;
    }

    public void setNumeroSiret(String numeroSiret) {
        this.numeroSiret = numeroSiret;
    }

    public String getNomAssociation() {
        return nomAssociation;
    }

    public void setNomAssociation(String nomAssociation) {
        this.nomAssociation = nomAssociation;
    }

    public String getAdresseAssociation() {
        return adresseAssociation;
    }

    public void setAdresseAssociation(String adresseAssociation) {
        this.adresseAssociation = adresseAssociation;
    }

    public String getEmailAssociation() {
        return emailAssociation;
    }

    public void setEmailAssociation(String emailAssociation) {
        this.emailAssociation = emailAssociation;
    }

    public String getTelephoneAssociation() {
        return telephoneAssociation;
    }

    public void setTelephoneAssociation(String telephoneAssociation) {
        this.telephoneAssociation = telephoneAssociation;
    }

    public float getPrixIndividuel() {
        return prixIndividuel;
    }

    public void setPrixIndividuel(float prixIndividuel) {
        this.prixIndividuel = prixIndividuel;
    }

    public float getPrixGroupe() {
        return prixGroupe;
    }

    public void setPrixGroupe(float prixGroupe) {
        this.prixGroupe = prixGroupe;
    }

    public String getMentionLegale() {
        return mentionLegale;
    }

    public void setMentionLegale(String mentionLegale) {
        this.mentionLegale = mentionLegale;
    }
}

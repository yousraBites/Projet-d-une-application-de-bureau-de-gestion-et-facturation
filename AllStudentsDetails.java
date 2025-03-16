/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetdegestion;

public class AllStudentsDetails {
    private String studentName;
    private int individuelHours;
    private int groupeHours;
    private String monthPayments;

    public AllStudentsDetails(String studentName, int individuelHours, int groupeHours, String monthPayments) {
        this.studentName = studentName;
        this.individuelHours = individuelHours;
        this.groupeHours = groupeHours;
        this.monthPayments = monthPayments;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getIndividuelHours() {
        return individuelHours;
    }

    public int getGroupeHours() {
        return groupeHours;
    }

    public String getMonthPayments() {
        return monthPayments;
    }
}

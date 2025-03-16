/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetdegestion;

public class StudentCourseDetails {
    private String month;
    private int week;
    private int totalHours;

    public StudentCourseDetails(String month, int week, int totalHours) {
        this.month = month;
        this.week = week;
        this.totalHours = totalHours;
    }

    public String getMonth() {
        return month;
    }

    public int getWeek() {
        return week;
    }

    public int getTotalHours() {
        return totalHours;
    }
}


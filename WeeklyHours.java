/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package projetdegestion;

public class WeeklyHours {
    private String month;
    private Integer s1;
    private Integer s2;
    private Integer s3;
    private Integer s4;

    public WeeklyHours(int s1, int s2, int s3, int s4) {
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.s4 = s4;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getS1() {
        return s1;
    }

    public Integer getS2() {
        return s2;
    }

    public Integer getS3() {
        return s3;
    }

    public Integer getS4() {
        return s4;
    }
}



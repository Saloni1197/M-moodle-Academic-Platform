package com.example.firstpage;

public class StudentLoginInfo {


    public String enrollnumber;
    public String password;
    public String year;
    public String lastName;
    public String firstName;
    public String confirmPassword;

    public StudentLoginInfo()
    {

    }

    public StudentLoginInfo(String enrollnumber, String password, String firstName, String lastName,String year) {
        this.enrollnumber = enrollnumber;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.year = year;

    }


    public void setEnrollnumber(String enrollnumber) {
        this.enrollnumber = enrollnumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setYear(String year) {
        this.year = year;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    public String getEnrollnumber() {
        return enrollnumber;
    }

    public String getPassword() {
        return password;
    }

    public String getYear() {
        return year;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

}

package io.github.httpsabhisheksy.myapplication;

/**
 * Created by Yadav on 4/10/2018.
 */

public class UserData {
    String userName;
    String userID;
    String BloodGroup;
    String HT;
    String age;
    String drugAllergy;
    String asthama;
    String pregnant;
    String RecentSurgery;
    String phoneNumber;
    String bloodSugar;
    String weight;
    String height;
    public class Loc{
        Double Lati;
        Double Longi;
    }

    public UserData(){}

    public UserData(String userName, String userID, String bloodGroup, String HT, String age, String drugAllergy, String asthama, String pregnant, String recentSurgery, String phoneNumber, String bloodSugar, String weight, String height, Double lati, Double longi) {
        this.userName = userName;
        this.userID = userID;
        BloodGroup = bloodGroup;
        this.HT = HT;
        this.age = age;
        this.drugAllergy = drugAllergy;
        this.asthama = asthama;
        this.pregnant = pregnant;
        RecentSurgery = recentSurgery;
        this.phoneNumber = phoneNumber;
        this.bloodSugar = bloodSugar;
        this.weight = weight;
        this.height = height;
        UserData userData = new UserData();
        UserData.Loc loc=userData.new Loc();
        loc.Lati=lati;
        loc.Longi=longi;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserID() {
        return userID;
    }

    public String getBloodGroup() {
        return BloodGroup;
    }

    public String getHT() {
        return HT;
    }

    public String getAge() {
        return age;
    }

    public String getDrugAllergy() {
        return drugAllergy;
    }

    public String getAsthama() {
        return asthama;
    }

    public String getPregnant() {
        return pregnant;
    }

    public String getRecentSurgery() {
        return RecentSurgery;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBloodSugar() {
        return bloodSugar;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setBloodGroup(String bloodGroup) {
        BloodGroup = bloodGroup;
    }

    public void setHT(String HT) {
        this.HT = HT;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setDrugAllergy(String drugAllergy) {
        this.drugAllergy = drugAllergy;
    }

    public void setAsthama(String asthama) {
        this.asthama = asthama;
    }

    public void setPregnant(String pregnant) {
        this.pregnant = pregnant;
    }

    public void setRecentSurgery(String recentSurgery) {
        RecentSurgery = recentSurgery;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setBloodSugar(String bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setHeight(String height) {
        this.height = height;
    }

}

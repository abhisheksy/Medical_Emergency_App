package io.github.httpsabhisheksy.myapplication;

/**
 * Created by Yadav on 4/29/2018.
 */

public class HospitalData {
    String HospitalName;
    String HospitalEMail;
    String PhoneNumber;
    String UID;
    class Loc{
        Double Longi;
        Double Lati;
    }

    public HospitalData(){

    }

    public HospitalData(String hospitalName, String HospitalEMail, String phoneNumber,Double lati, Double longi, String uid) {
        HospitalName = hospitalName;
        this.HospitalEMail = HospitalEMail;
        PhoneNumber = phoneNumber;
        HospitalData hospitalData = new HospitalData();
        HospitalData.Loc loc=hospitalData.new Loc();
        loc.Lati=lati;
        loc.Longi=longi;
        this.UID=uid;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getHospitalName() {
        return HospitalName;
    }

    public String getHospitalEMail() {
        return HospitalEMail;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setHospitalName(String hospitalName) {
        HospitalName = hospitalName;
    }

    public void setHospitalEMail(String HospitalEMail) {
        this.HospitalEMail = HospitalEMail;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}



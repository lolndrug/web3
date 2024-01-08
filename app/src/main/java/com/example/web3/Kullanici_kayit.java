package com.example.web3;

public class Kullanici_kayit {
    private String kisiKey;
    private String name;
    private String surname;
    private String motherName;
   private String fatherName;
    private String id;
    private String dof;
    private String gender;
    private String password;

    public Kullanici_kayit(String kisiKey, String name, String surname, String motherName, String fatherName, String id, String dof, String gender, String password) {
        this.kisiKey = kisiKey;
        this.name = name;
        this.surname = surname;
        this.motherName = motherName;
        this.fatherName = fatherName;
        this.id = id;
        this.dof = dof;
        this.gender = gender;
        this.password = password;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDof() {
        return dof;
    }

    public void setDof(String dof) {
        this.dof = dof;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}



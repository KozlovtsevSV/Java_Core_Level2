package ru.geekbrains;


import java.util.Objects;

public class Person {
    private String surName = "";
    private String phone = "";
    private String eMail = "";

    Person(String surName, String phone, String eMail){
        this.surName = surName;
        this.phone = phone;
        this.eMail = eMail;
    }

    @Override
    public String toString() {
        return "\n[Фамилия: "+ surName + " телефон: " + phone + " e-mail: " + eMail + "]";
    }


    public String getSurName() {
        return surName;
    }

    public String getPhone() {
        return phone;
    }

    public String get_eMail() {
        return eMail;
    }

}


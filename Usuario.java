package com.example.registro_app;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Usuario implements Serializable {

    private  String name;
    private String email;
    private String age;
    private String password;
    private int gender1 = 0;
    private int gender2 = 0;
    private int gender3 = 0;
    private int gender4 = 0;
    private int gender5 = 0;
    private int total = 0;

    Usuario(String name, String email, String age, String password)
    {
        this.name = name;
        this.email = email;
        this.age = age;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGender1() {
        return gender1;
    }

    public void setGender1(int gender1) {
        this.gender1 = gender1;
    }

    public int getGender2() {
        return gender2;
    }

    public void setGender2(int gender2) {
        this.gender2 = gender2;
    }

    public int getGender3() {
        return gender3;
    }

    public void setGender3(int gender3) {
        this.gender3 = gender3;
    }

    public int getGender4() {
        return gender4;
    }

    public void setGender4(int gender4) {
        this.gender4 = gender4;
    }

    public int getGender5() {
        return gender5;
    }

    public void setGender5(int gender5) {
        this.gender5 = gender5;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age='" + age + '\'' +
                ", password ='" + password + '\'' +
                ", genere 1 =" + gender1 +
                ", genere 2 =" + gender2 +
                ", genere 3 =" + gender3 +
                ", genere 4 =" + gender4 +
                ", genere 5 =" + gender5 +
                ", total=" + total +
                '}';
    }
}

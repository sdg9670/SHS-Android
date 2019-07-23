package com.example.nabot.domain;

import java.io.Serializable;

public class ClientDTO implements Serializable {
    private int id;
    private String id_name;
    private String password;
    private String name;
    private int ho;
    private int dong;
    private String fcm;
    public ClientDTO(){

    }

    public ClientDTO(int id, String id_name, String password, String name, int ho, int dong, String fcm) {
        this.id = id;
        this.id_name = id_name;
        this.password = password;
        this.name = name;
        this.ho = ho;
        this.dong = dong;
        this.fcm = fcm;
    }

    public ClientDTO(int id, String fcm) {
        this.id = id;
        this.fcm = fcm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getHo() {
        return ho;
    }

    public void setHo(int ho) {
        this.ho = ho;
    }

    public int getDong() {
        return dong;
    }

    public void setDong(int dong) {
        this.dong = dong;
    }

    public String getFcm() {
        return fcm;
    }

    public void setFcm(String fcm) {
        this.fcm = fcm;
    }

    public String getId_name() {
        return id_name;
    }

    public void setId_name(String id_name) {
        this.id_name = id_name;
    }
}

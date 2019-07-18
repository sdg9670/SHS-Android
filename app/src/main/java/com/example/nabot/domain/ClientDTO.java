package com.example.nabot.domain;

import java.io.Serializable;

public class ClientDTO implements Serializable {
    private int id;
    private String password;
    private String name;
    private int ho_id;
    private int dong_id;
    public ClientDTO(int id, String name){
        this.id = id;
        this.name = name;
    }

    public ClientDTO(int id, String password, String name, int ho_id, int dong_id) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.ho_id = ho_id;
        this.dong_id = dong_id;
    }

    public ClientDTO() {

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

    public int getHo_id() {
        return ho_id;
    }

    public void setHo_id(int ho_id) {
        this.ho_id = ho_id;
    }

    public int getDong_id() {
        return dong_id;
    }

    public void setDong_id(int dong_id) {
        this.dong_id = dong_id;
    }
}

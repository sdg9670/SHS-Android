package com.example.nabot.domain;

import java.io.Serializable;

public class DoorlockDTO implements Serializable {
 private  int id;
 private String path;
 private String upload_time;
 private String state;
 private int dong;
 private int ho;
    public DoorlockDTO(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getDong() {
        return dong;
    }

    public void setDong(int dong) {
        this.dong = dong;
    }

    public int getHo() {
        return ho;
    }

    public void setHo(int ho) {
        this.ho = ho;
    }

    public DoorlockDTO(int id, String path, String upload_time, String state, int dong, int ho) {
        this.id = id;
        this.path = path;
        this.upload_time = upload_time;
        this.state = state;
        this.dong = dong;
        this.ho = ho;
    }
}
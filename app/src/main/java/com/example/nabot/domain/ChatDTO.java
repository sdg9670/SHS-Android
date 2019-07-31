package com.example.nabot.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class ChatDTO implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private int sendid;
    private int recvid;
    private String msg;
    private String datetime;
    private String someidname;
    private String sendname;
    private String recvname;

    public String getRecvname(){
        return recvname;
    }
    public void setRecvname(String recvname){
        this.recvname = recvname;
    }

    public String getSendname(){return sendname;}
    public void setSendname(String sendname){
        this.sendname = sendname;
    }

    public String getRoomnum() {
        return roomnum;
    }

    private String roomnum;

    public String getSomeidname() {
        return someidname;
    }

    public void setSomeidname(String someidname) {
        this.someidname = someidname;
    }

    public ChatDTO() {

    }

    public ChatDTO(String roomnum, int id, String sendname, int recvid, String recvname, String msg, String datetime) {
        this.roomnum = roomnum;
        this.id = id;
        this.sendname = sendname;
        this.recvid = recvid;
        this.recvname = recvname;
        this.msg = msg;
        this.datetime = datetime;
    }

    public ChatDTO(String roomnum, int sendid, int recvid, String msg) {
        this.roomnum = roomnum;
        this.sendid = sendid;
        this.recvid = recvid;
        this.msg = msg;
    }


    public ChatDTO(int sendid, String someidname, String msg) {
        this.sendid = sendid;
        this.someidname = someidname;
        this.msg = msg;
    }

    public int getSendid() {
        return sendid;
    }

    public void setSendid(int sendid) {
        this.sendid = sendid;
    }

    public int getRecvid() {
        return recvid;
    }

    public void setRecvid(int recvid) {
        this.recvid = recvid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}


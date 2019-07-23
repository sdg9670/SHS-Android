package com.example.nabot.domain;

import java.io.Serializable;

public class ContactDTO implements Serializable {
    private int id;
    private int clientid;
    private int someid;
    private int newmsg;
    private String somename;
    private String somedong;
    private String someho;
    private String someidname;

    public int getClientid(){
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSomeid() {
        return someid;
    }

    public void setSomeid(int someid) {
        this.someid = someid;
    }

    public int getNewmsg() {
        return newmsg;
    }

    public void setNewmsg(int newmsg) {
        this.newmsg = newmsg;
    }

    public String getSomename() {
        return somename;
    }

    public String getSomedong() {
        return somedong;
    }

    public void setSomedong(String somedong) {
        this.somedong = somedong;
    }

    public String getSomeho() {
        return someho;
    }

    public void setSomeho(String someho) {
        this.someho = someho;
    }

    public void setSomename(String somename) {
        this.somename = somename;
    }

    public String getSomeidname() {
        return someidname;
    }

    public void setSomeidname(String someidname) {
        this.someidname = someidname;
    }

    public ContactDTO(int id, int clientid, int someid, int newmsg, String somename, String somedong, String someho, String someidname) {
        this.id = id;
        this.clientid = clientid;
        this.someid = someid;
        this.newmsg = newmsg;
        this.somename = somename;
        this.somedong = somedong;
        this.someho = someho;
        this.someidname = someidname;
    }

    public ContactDTO(int clientid) {
        this.clientid = clientid;
    }

    public ContactDTO(int clientid, String someidname) {
        this.clientid = clientid;
        this.someidname = someidname;
    }
}

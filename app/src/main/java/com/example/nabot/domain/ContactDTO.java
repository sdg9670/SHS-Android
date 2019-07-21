package com.example.nabot.domain;

import java.io.Serializable;

public class ContactDTO implements Serializable {
    private int id;
    private int clientid;
    private int someid;
    private int newmsg;

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
    public ContactDTO(int clientId, int id) {
        this.id = this.id;
        this.clientid = clientid;
        this.someid = someid;
        this.newmsg = newmsg;
    }
    public ContactDTO(int id) {

    }
}

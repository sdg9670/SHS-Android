package com.example.nabot.domain;

import java.io.Serializable;

public class CheckVoteDTO implements Serializable {
    private int writing_id;
    private int clientid;

    public int getWriting_id() {
        return writing_id;
    }

    public void setWriting_id(int writing_id) {
        this.writing_id = writing_id;
    }

    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    public CheckVoteDTO(int writing_id, int clientid) {
        this.writing_id = writing_id;
        this.clientid = clientid;
    }
}
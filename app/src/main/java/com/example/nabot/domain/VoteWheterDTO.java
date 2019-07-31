package com.example.nabot.domain;

import java.io.Serializable;

public class VoteWheterDTO implements Serializable {
    private int vote_id;
    private int client_id;

    public VoteWheterDTO(){

    }
    public int getVote_id() {
        return vote_id;
    }

    public void setVote_id(int vote_id) {
        this.vote_id = vote_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public VoteWheterDTO(int vote_id, int client_id) {
        this.vote_id = vote_id;
        this.client_id = client_id;
    }
}
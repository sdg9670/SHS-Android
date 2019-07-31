package com.example.nabot.domain;

import java.io.Serializable;

public class VoteDTO implements Serializable {
 private  int id;
 private String name;
 private int amount;
 private int writing_id;

    public VoteDTO(String name){
        this.name=name;
    }


    public VoteDTO(int id, String name, int amount, int writing_id) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.writing_id = writing_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getWriting_id() {
        return writing_id;
    }

    public void setWriting_id(int writing_id) {
        this.writing_id = writing_id;
    }
}
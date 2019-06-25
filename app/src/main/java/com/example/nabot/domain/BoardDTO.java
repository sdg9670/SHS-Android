package com.example.nabot.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BoardDTO  implements Serializable {
    private  int id;
    private String name;
    private  int type;

    public  BoardDTO(){

    }
    public BoardDTO(int id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

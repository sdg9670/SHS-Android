package com.example.nabot.domain;

import java.io.Serializable;

public class ChatImageDTO implements Serializable {
    private int id;
    private String name;
    private String path;
    private int chat_id;

    public ChatImageDTO() {
    }
    public ChatImageDTO(String path, int chat_id , String name) {
        this.path = path;
        this.chat_id = chat_id;
        this.name = name;
    }
    public ChatImageDTO(int id, String name, String path, int chat_id) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.chat_id = chat_id;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getChat_id() {
        return chat_id;
    }

    public void setchat_id(int writing_id) {
        this.chat_id = chat_id;
    }
}

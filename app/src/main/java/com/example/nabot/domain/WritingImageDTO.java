package com.example.nabot.domain;

import java.io.Serializable;

public class WritingImageDTO implements Serializable {
    private int id;
    private String path;
    private int writing_id;

    public WritingImageDTO( String path, int writing_id) {
        this.path = path;
        this.writing_id = writing_id;
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

    public int getWriting_id() {
        return writing_id;
    }

    public void setWriting_id(int writing_id) {
        this.writing_id = writing_id;
    }


}
package com.example.nabot.domain;

public class CurtainDTO {
    private int id;
    private int status;
    private int lux;

    public CurtainDTO(int id, int status, int lux) {
        this.id = id;
        this.status = status;
        this.lux = lux;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLux() {
        return lux;
    }

    public void setLux(int lux) {
        this.lux = lux;
    }
}

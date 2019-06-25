package com.example.nabot.domain;

public class WindowDTO {
    private int id;
    private int status;
    private double temp;
    private double humi;
    private double rain;
    private double dust;
    private int onoff;
    private int over;

    public WindowDTO(int id, int status, double temp, double humi, double rain, double dust, int onoff, int over) {
        this.id = id;
        this.status = status;
        this.temp = temp;
        this.humi = humi;
        this.rain = rain;
        this.dust = dust;
        this.onoff = onoff;
        this.over = over;
    }
    public WindowDTO(int status, double temp, double humi, double rain, double dust, int onoff, int over) {
        this.status = status;
        this.temp = temp;
        this.humi = humi;
        this.rain = rain;
        this.dust = dust;
        this.onoff = onoff;
        this.over = over;
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

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getHumi() {
        return humi;
    }

    public void setHumi(double humi) {
        this.humi = humi;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getDust() {
        return dust;
    }

    public void setDust(double dust) {
        this.dust = dust;
    }

    public int getOnoff() {
        return onoff;
    }

    public void setOnoff(int onoff) {
        this.onoff = onoff;
    }

    public int getOver() {
        return over;
    }

    public void setOver(int over) {
        this.over = over;
    }
}

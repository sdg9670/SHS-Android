package com.example.nabot.domain;

public class WindowDTO {
    private int id;
    private int status;
    private double temp;
    private double humi;
    private double rain;
    private double dust;
    private double temp_set;
    private int temp_over;
    private double humi_set;
    private int humi_over;
    private double rain_set;
    private int rain_over;
    private double dust_set;
    private int dust_over;

    public WindowDTO(int id, double temp_set, int temp_over, double humi_set, int humi_over, double rain_set, int rain_over, double dust_set, int dust_over) {
        this.id = id;
        this.status = status;
        this.temp = temp;
        this.humi = humi;
        this.rain = rain;
        this.dust = dust;
        this.temp_set = temp_set;
        this.temp_over = temp_over;
        this.humi_set = humi_set;
        this.humi_over = humi_over;
        this.rain_set = rain_set;
        this.rain_over = rain_over;
        this.dust_set = dust_set;
        this.dust_over = dust_over;

    }
    public WindowDTO(int status, double temp, double humi, double rain, double dust) {
        this.status = status;
        this.temp = temp;
        this.humi = humi;
        this.rain = rain;
        this.dust = dust;
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


}

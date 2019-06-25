package com.example.nabot.domain;

public class SensorDTO {
    private int clientid;
    private double temp;
    private double humi;
    private int gas;

    public SensorDTO(int clientid, double temp, double humi, int gas) {
        this.clientid = clientid;
        this.temp = temp;
        this.humi = humi;
        this.gas = gas;
    }

    public int getClientid() {
        return clientid;
    }

    public void setClientid(int clientid) {
        this.clientid = clientid;
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

    public int getGas() {
        return gas;
    }

    public void setGas(int gas) {
        this.gas = gas;
    }
}

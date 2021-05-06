package com.example.bluewagon.ecalculo.dtos;

public class DashboardItemServiceDTO {
    private String serviceName;
    private double soldQty;
    private String unit;


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getSoldQty() {
        return soldQty;
    }

    public void setSoldQty(double soldQty) {
        this.soldQty = soldQty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}

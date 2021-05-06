package com.example.bluewagon.ecalculo.dtos;

public class DashboardItemsProductDTO {
    private String productName;
    private double availableQty;
    private String unit;
    private double salesRate;


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(double availableQty) {
        this.availableQty = availableQty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getSalesRate() {
        return salesRate;
    }

    public void setSalesRate(double salesRate) {
        this.salesRate = salesRate;
    }
}

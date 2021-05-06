package com.example.bluewagon.ecalculo.dtos;

public class DashboardProductsDTO {
    private String productName;
    private double quantity;
    private double unit;
    private double salesRate;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getUnit() {
        return unit;
    }

    public void setUnit(double unit) {
        this.unit = unit;
    }

    public double getSalesRate() {
        return salesRate;
    }

    public void setSalesRate(double salesRate) {
        this.salesRate = salesRate;
    }
}

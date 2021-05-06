package com.example.bluewagon.ecalculo.dtos;

public class AddItemServiceDTO {
    private String productServiceName;
    private double quantity;
    private String unit;
    private double rate;
    private double subTotal;

    public String getProductServiceName() {
        return productServiceName;
    }

    public void setProductServiceName(String productServiceName) {
        this.productServiceName = productServiceName;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}

package com.example.bluewagon.ecalculo.dtos;

public class DashboardAccountDTO {
    private String customerSupplierName;
    private double balance;

    public String getCustomerSupplierName() {
        return customerSupplierName;
    }

    public void setCustomerSupplierName(String customerSupplierName) {
        this.customerSupplierName = customerSupplierName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

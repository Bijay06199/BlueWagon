package com.example.bluewagon.ecalculo.dtos;

public class AddPaymentReceiptDTO {
    private String accountName;
    private double closingBalance;
    private double paidAmount;
    private String paidTo;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(double closingBalance) {
        this.closingBalance = closingBalance;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getPaidTo() {
        return paidTo;
    }

    public void setPaidTo(String paidTo) {
        this.paidTo = paidTo;
    }
}

package com.example.bluewagon.ecalculo.dtos;

public class AddEntryDTO {
    private String accountName;
    private double closingBalance;
    private double amount;
    private String purchaseBillDate;
    private String purchaseBillNumber;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPurchaseBillDate() {
        return purchaseBillDate;
    }

    public void setPurchaseBillDate(String purchaseBillDate) {
        this.purchaseBillDate = purchaseBillDate;
    }

    public String getPurchaseBillNumber() {
        return purchaseBillNumber;
    }

    public void setPurchaseBillNumber(String purchaseBillNumber) {
        this.purchaseBillNumber = purchaseBillNumber;
    }
}

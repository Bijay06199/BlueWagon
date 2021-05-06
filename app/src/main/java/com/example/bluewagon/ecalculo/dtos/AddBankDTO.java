package com.example.bluewagon.ecalculo.dtos;

public class AddBankDTO {
    private String bankName;
    private double closingBalance;
    private double amount;
    private String chequeDate;
    private String chequeNumber;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public String getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }
}

package com.example.bluewagon.ecalculo.dtos;

public class AccountMasterDTO {

    /**
     * id : 1
     * name : Cash
     * address : N/A
     * panNo : N/A
     * closingBalance : -8405.0
     * isCashAccount : true
     * isBankAccount : false
     */

    private int id;
    private String name;
    private String address;
    private String panNo;
    private double closingBalance;
    private boolean isCashAccount;
    private boolean isBankAccount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public double getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(double closingBalance) {
        this.closingBalance = closingBalance;
    }

    public boolean isIsCashAccount() {
        return isCashAccount;
    }

    public void setIsCashAccount(boolean isCashAccount) {
        this.isCashAccount = isCashAccount;
    }

    public boolean isIsBankAccount() {
        return isBankAccount;
    }

    public void setIsBankAccount(boolean isBankAccount) {
        this.isBankAccount = isBankAccount;
    }
}

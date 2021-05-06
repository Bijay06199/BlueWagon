package com.example.bluewagon.ecalculo.dtos;

public class BankLedgerDTO {


    /**
     * id : 1
     * name : Pravhu Bank Limited
     * type : Saving
     * accountNumber : 010101201
     * branch : Butwal
     * drCr : Dr
     * accountMasterId : 7
     * openingBalance : 0.0
     * closingBalance : 0.0
     */

    private int id;
    private String name;
    private String type;
    private String accountNumber;
    private String branch;
    private String drCr;
    private int accountMasterId;
    private double openingBalance;
    private double closingBalance;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDrCr() {
        return drCr;
    }

    public void setDrCr(String drCr) {
        this.drCr = drCr;
    }

    public int getAccountMasterId() {
        return accountMasterId;
    }

    public void setAccountMasterId(int accountMasterId) {
        this.accountMasterId = accountMasterId;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public double getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(double closingBalance) {
        this.closingBalance = closingBalance;
    }
}

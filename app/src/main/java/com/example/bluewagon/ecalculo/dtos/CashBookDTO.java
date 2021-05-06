package com.example.bluewagon.ecalculo.dtos;

public class CashBookDTO {

    /**
     * transactionDate :
     * voucher :
     * accountName : Closing Cash Balance
     * drCr : Dr
     * transactionDateNepali :
     * moduleType :
     * openingBalance : 0
     * closingBalance : 8405
     * debitBalance : 0
     * creditBalance : 0
     * totalSumDebit : 0
     * totalSumCredit : 0
     */

    private String transactionDate;
    private String voucher;
    private String accountName;
    private String drCr;
    private String transactionDateNepali;
    private String moduleType;
    private int openingBalance;
    private int closingBalance;
    private int debitBalance;
    private int creditBalance;
    private int totalSumDebit;
    private int totalSumCredit;

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getDrCr() {
        return drCr;
    }

    public void setDrCr(String drCr) {
        this.drCr = drCr;
    }

    public String getTransactionDateNepali() {
        return transactionDateNepali;
    }

    public void setTransactionDateNepali(String transactionDateNepali) {
        this.transactionDateNepali = transactionDateNepali;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public int getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(int openingBalance) {
        this.openingBalance = openingBalance;
    }

    public int getClosingBalance() {
        return closingBalance;
    }

    public void setClosingBalance(int closingBalance) {
        this.closingBalance = closingBalance;
    }

    public int getDebitBalance() {
        return debitBalance;
    }

    public void setDebitBalance(int debitBalance) {
        this.debitBalance = debitBalance;
    }

    public int getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(int creditBalance) {
        this.creditBalance = creditBalance;
    }

    public int getTotalSumDebit() {
        return totalSumDebit;
    }

    public void setTotalSumDebit(int totalSumDebit) {
        this.totalSumDebit = totalSumDebit;
    }

    public int getTotalSumCredit() {
        return totalSumCredit;
    }

    public void setTotalSumCredit(int totalSumCredit) {
        this.totalSumCredit = totalSumCredit;
    }
}

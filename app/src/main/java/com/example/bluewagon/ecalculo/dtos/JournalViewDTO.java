package com.example.bluewagon.ecalculo.dtos;


public class JournalViewDTO{

    /**
     * id : 71
     * transactionDate : 2020-06-27
     * voucher : PV-1
     * accountName : Cash
     * entryType : pay
     * transactionDateNepali : 2077-03-13
     * tranModuleType : PV
     * openingBalance : 0.0
     * closingBalance : 0.0
     * debitBalance : 100.0
     * creditBalance : 0.0
     * remarks : Payment voucher no  PV - 1
     * repatedTransaction : false
     * totalSumDebit : 100.0
     * totalSumCredit : 0.0
     * tranId : 1
     */

    private int id;
    private String transactionDate;
    private String voucher;
    private String accountName;
    private String entryType;
    private String transactionDateNepali;
    private String tranModuleType;
    private double openingBalance;
    private double closingBalance;
    private double debitBalance;
    private double creditBalance;
    private String remarks;
    private boolean repatedTransaction;
    private double totalSumDebit;
    private double totalSumCredit;
    private int tranId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getTransactionDateNepali() {
        return transactionDateNepali;
    }

    public void setTransactionDateNepali(String transactionDateNepali) {
        this.transactionDateNepali = transactionDateNepali;
    }

    public String getTranModuleType() {
        return tranModuleType;
    }

    public void setTranModuleType(String tranModuleType) {
        this.tranModuleType = tranModuleType;
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

    public double getDebitBalance() {
        return debitBalance;
    }

    public void setDebitBalance(double debitBalance) {
        this.debitBalance = debitBalance;
    }

    public double getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(double creditBalance) {
        this.creditBalance = creditBalance;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean isRepatedTransaction() {
        return repatedTransaction;
    }

    public void setRepatedTransaction(boolean repatedTransaction) {
        this.repatedTransaction = repatedTransaction;
    }

    public double getTotalSumDebit() {
        return totalSumDebit;
    }

    public void setTotalSumDebit(double totalSumDebit) {
        this.totalSumDebit = totalSumDebit;
    }

    public double getTotalSumCredit() {
        return totalSumCredit;
    }

    public void setTotalSumCredit(double totalSumCredit) {
        this.totalSumCredit = totalSumCredit;
    }

    public int getTranId() {
        return tranId;
    }

    public void setTranId(int tranId) {
        this.tranId = tranId;
    }
}

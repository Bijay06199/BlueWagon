package com.example.bluewagon.ecalculo.dtos;

public class AccountStatementLedgerDTO {

    /**
     * transactionDate : 2020-07-03.
     * voucher : RV - 7.
     * accountName : Stock.
     * drCr : Dr.
     * entryType : rec
     * transactionDateNepali : 2077-03-19.
     * tranModuleType : RV
     * moduleType : rec.
     * openingBalance : 0.
     * closingBalance : 10600.
     * debitBalance : 500.
     * creditBalance : 0.
     * remarks : Receipt voucher no RV - 7
     * totalSumDebit : 0.
     * totalSumCredit : 0.
     * tranId : 7
     */

    private String transactionDate;
    private String voucher;
    private String accountName;
    private String drCr;
    private String entryType;
    private String transactionDateNepali;
    private String tranModuleType;
    private String moduleType;
    private double openingBalance;
    private double closingBalance;
    private double debitBalance;
    private double creditBalance;
    private String remarks;
    private double totalSumDebit;
    private double totalSumCredit;
    private int tranId;
    private String chequeNumber;
    private String chequeDate;
    private String chequeClearingDate;

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

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
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

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public String getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
    }

    public String getChequeClearingDate() {
        return chequeClearingDate;
    }

    public void setChequeClearingDate(String chequeClearingDate) {
        this.chequeClearingDate = chequeClearingDate;
    }
}

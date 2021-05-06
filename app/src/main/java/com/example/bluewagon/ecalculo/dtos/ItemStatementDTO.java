package com.example.bluewagon.ecalculo.dtos;

public class ItemStatementDTO {

    /**
     * itemId : 0
     * partyName : Omkar Organization
     * date : 2019-07-17
     * nepaliDate : 2076-04-01
     * voucher : N/A
     * voucherType : 5
     * inQuantity : 0.0
     * outQuantity : 16.0
     * openingQuantity : 0.0
     * closingQuantity : -16.0
     * inOutAmount : 160000.0
     * closingValue : 0.0
     */

    private int itemId;
    private String partyName;
    private String date;
    private String nepaliDate;
    private String voucher;
    private String voucherType;
    private double inQuantity;
    private double outQuantity;
    private double openingQuantity;
    private double closingQuantity;
    private double inOutAmount;
    private double closingValue;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNepaliDate() {
        return nepaliDate;
    }

    public void setNepaliDate(String nepaliDate) {
        this.nepaliDate = nepaliDate;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public double getInQuantity() {
        return inQuantity;
    }

    public void setInQuantity(double inQuantity) {
        this.inQuantity = inQuantity;
    }

    public double getOutQuantity() {
        return outQuantity;
    }

    public void setOutQuantity(double outQuantity) {
        this.outQuantity = outQuantity;
    }

    public double getOpeningQuantity() {
        return openingQuantity;
    }

    public void setOpeningQuantity(double openingQuantity) {
        this.openingQuantity = openingQuantity;
    }

    public double getClosingQuantity() {
        return closingQuantity;
    }

    public void setClosingQuantity(double closingQuantity) {
        this.closingQuantity = closingQuantity;
    }

    public double getInOutAmount() {
        return inOutAmount;
    }

    public void setInOutAmount(double inOutAmount) {
        this.inOutAmount = inOutAmount;
    }

    public double getClosingValue() {
        return closingValue;
    }

    public void setClosingValue(double closingValue) {
        this.closingValue = closingValue;
    }
}

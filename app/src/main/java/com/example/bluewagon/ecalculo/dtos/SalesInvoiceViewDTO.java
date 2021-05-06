package com.example.bluewagon.ecalculo.dtos;

public class SalesInvoiceViewDTO {

    private String date;
    private String party;
    private String billNumber;
    private String name;
    private String narration;
    private double total;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}

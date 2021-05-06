package com.example.bluewagon.ecalculo.dtos;

public class CustomerSupplierLedgerDTO {

    /**
     * id : 31
     * name : sushant
     * primaryContactNumber : 98
     * telContact :
     * shortName : sush
     * contactName : sushant
     * drCr : Cr
     * address : N/A
     * email :
     * panNo : 685957877
     * accountHeadName : Current Liabilities
     * accountSubHeadName : Sundry Creditors
     * accountNatureName : Liabilities
     * accountTypeName : Supplier A/c
     * accountSubHead : {"id":11,"name":"Sundry Creditors"}
     * accountHead : {"id":3,"name":"Current Liabilities ","systemOnly":false}
     * openingBalance : 0.0
     * closingBalance : 100.0
     * accountDetailId : 6
     * isCashAccount : false
     * isBankAccount : false
     * isSystemOnly : false
     */

    private int id;
    private String name;
    private String primaryContactNumber;
    private String telContact;
    private String shortName;
    private String contactName;
    private String drCr;
    private String address;
    private String email;
    private String panNo;
    private String accountHeadName;
    private String accountSubHeadName;
    private String accountNatureName;
    private String accountTypeName;
    private AccountSubHeadBean accountSubHead;
    private AccountHeadBean accountHead;
    private double openingBalance;
    private double closingBalance;
    private int accountDetailId;
    private boolean isCashAccount;
    private boolean isBankAccount;
    private boolean isSystemOnly;

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

    public String getPrimaryContactNumber() {
        return primaryContactNumber;
    }

    public void setPrimaryContactNumber(String primaryContactNumber) {
        this.primaryContactNumber = primaryContactNumber;
    }

    public String getTelContact() {
        return telContact;
    }

    public void setTelContact(String telContact) {
        this.telContact = telContact;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getDrCr() {
        return drCr;
    }

    public void setDrCr(String drCr) {
        this.drCr = drCr;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getAccountHeadName() {
        return accountHeadName;
    }

    public void setAccountHeadName(String accountHeadName) {
        this.accountHeadName = accountHeadName;
    }

    public String getAccountSubHeadName() {
        return accountSubHeadName;
    }

    public void setAccountSubHeadName(String accountSubHeadName) {
        this.accountSubHeadName = accountSubHeadName;
    }

    public String getAccountNatureName() {
        return accountNatureName;
    }

    public void setAccountNatureName(String accountNatureName) {
        this.accountNatureName = accountNatureName;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public void setAccountTypeName(String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }

    public AccountSubHeadBean getAccountSubHead() {
        return accountSubHead;
    }

    public void setAccountSubHead(AccountSubHeadBean accountSubHead) {
        this.accountSubHead = accountSubHead;
    }

    public AccountHeadBean getAccountHead() {
        return accountHead;
    }

    public void setAccountHead(AccountHeadBean accountHead) {
        this.accountHead = accountHead;
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

    public int getAccountDetailId() {
        return accountDetailId;
    }

    public void setAccountDetailId(int accountDetailId) {
        this.accountDetailId = accountDetailId;
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

    public boolean isIsSystemOnly() {
        return isSystemOnly;
    }

    public void setIsSystemOnly(boolean isSystemOnly) {
        this.isSystemOnly = isSystemOnly;
    }

    public static class AccountSubHeadBean {
        /**
         * id : 11
         * name : Sundry Creditors
         */

        private int id;
        private String name;

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
    }

    public static class AccountHeadBean {
        /**
         * id : 3
         * name : Current Liabilities
         * systemOnly : false
         */

        private int id;
        private String name;
        private boolean systemOnly;

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

        public boolean isSystemOnly() {
            return systemOnly;
        }

        public void setSystemOnly(boolean systemOnly) {
            this.systemOnly = systemOnly;
        }
    }
}

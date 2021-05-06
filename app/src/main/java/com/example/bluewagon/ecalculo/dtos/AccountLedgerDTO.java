package com.example.bluewagon.ecalculo.dtos;

public class AccountLedgerDTO {

    /**
     * id : 1
     * name : Cash
     * shortName : cas
     * drCr : Dr
     * accountHeadName : Test
     * accountSubHeadName : Cash in hand
     * accountNatureName : Liabilities
     * accountTypeName : Office A/c
     * accountSubHead : {"id":1,"name":"Cash in hand "}
     * accountHead : {"id":1,"name":"Test","systemOnly":false}
     * openingBalance : 0.0
     * closingBalance : 0.0
     * isCashAccount : true
     * isBankAccount : false
     * isSystemOnly : true
     */

    private int id;
    private String name;
    private String shortName;
    private String drCr;
    private String accountHeadName;
    private String accountSubHeadName;
    private String accountNatureName;
    private String accountTypeName;
    private AccountSubHeadBean accountSubHead;
    private AccountHeadBean accountHead;
    private double openingBalance;
    private double closingBalance;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDrCr() {
        return drCr;
    }

    public void setDrCr(String drCr) {
        this.drCr = drCr;
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
         * id : 1
         * name : Cash in hand
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
         * id : 1
         * name : Test
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

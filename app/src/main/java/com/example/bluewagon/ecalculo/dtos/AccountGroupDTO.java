package com.example.bluewagon.ecalculo.dtos;

public class AccountGroupDTO {
    /**
     * id : 1
     * createdDate : 6/6/20 12:00 AM
     * name : Current Assets
     * natureName : Assets
     * accountNature : {"id":1,"name":"Assets"}
     * systemOnly : true
     */

    private int id;
    private String createdDate;
    private String name;
    private String natureName;
    private AccountNatureBean accountNature;
    private boolean systemOnly;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNatureName() {
        return natureName;
    }

    public void setNatureName(String natureName) {
        this.natureName = natureName;
    }

    public AccountNatureBean getAccountNature() {
        return accountNature;
    }

    public void setAccountNature(AccountNatureBean accountNature) {
        this.accountNature = accountNature;
    }

    public boolean isSystemOnly() {
        return systemOnly;
    }

    public void setSystemOnly(boolean systemOnly) {
        this.systemOnly = systemOnly;
    }

    public static class AccountNatureBean {
        /**
         * id : 1
         * name : Assets
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
}

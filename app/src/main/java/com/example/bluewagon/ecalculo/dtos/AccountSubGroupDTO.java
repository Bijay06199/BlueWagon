package com.example.bluewagon.ecalculo.dtos;

public class AccountSubGroupDTO {


    /**
     * id : 1
     * createdDate : 6/6/20 12:00 AM
     * name : Cash in hand
     * headName : Test
     * natureName : Liabilities
     * shortName :
     * parentId : 0
     * accountHeadId : {"id":1,"createdDate":"6/6/20 12:00 AM","updatedDate":"6/22/20 12:00 AM","name":"Test","natureName":"Liabilities","accountNature":{"id":2,"name":"Liabilities"},"systemOnly":true}
     * isSystemOnly : true
     */

    private int id;
    private String createdDate;
    private String name;
    private String headName;
    private String natureName;
    private String shortName;
    private int parentId;
    private AccountHeadIdBean accountHeadId;
    private boolean isSystemOnly;

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

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getNatureName() {
        return natureName;
    }

    public void setNatureName(String natureName) {
        this.natureName = natureName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public AccountHeadIdBean getAccountHeadId() {
        return accountHeadId;
    }

    public void setAccountHeadId(AccountHeadIdBean accountHeadId) {
        this.accountHeadId = accountHeadId;
    }

    public boolean isIsSystemOnly() {
        return isSystemOnly;
    }

    public void setIsSystemOnly(boolean isSystemOnly) {
        this.isSystemOnly = isSystemOnly;
    }

    public static class AccountHeadIdBean {
        /**
         * id : 1
         * createdDate : 6/6/20 12:00 AM
         * updatedDate : 6/22/20 12:00 AM
         * name : Test
         * natureName : Liabilities
         * accountNature : {"id":2,"name":"Liabilities"}
         * systemOnly : true
         */

        private int id;
        private String createdDate;
        private String updatedDate;
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

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
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
             * id : 2
             * name : Liabilities
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
}

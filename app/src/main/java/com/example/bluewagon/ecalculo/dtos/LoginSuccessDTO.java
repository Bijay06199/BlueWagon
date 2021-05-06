package com.example.bluewagon.ecalculo.dtos;

public class LoginSuccessDTO {

    /**
     * access_token : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYW1hamRhciIsImF1dGhvcml0aWVzIjpbInVzZXIiXSwicm9sZXMiOltdLCJpYXQiOjE1OTI3NjEyNjcsImV4cCI6MTU5Mjg0NzY2N30.LRPPydg6LiHE72PyQ_2zjjaMMCR3bedLg3z6xZhSpMjIoH3IGc9H4JYLunxanLdfxMKPEi8Q_xLEG4ZzL_MTkg
     * organisationId : 1
     * isEnglishDateEnabled : true
     * endDate : 2020-01-21
     * nepaliNewDate : null
     * expiry : 1592847667981
     * transactionDate : 2020-01-21
     * user : {"id":2,"username":"Samajdar","name":"Samajdar","roleId":2,"role":{"id":2,"updatedDate":"6/9/20 12:00 AM","name":"Admin","modules":",1.01,1.02,1.03,1.04,1.05,1.06,1.07,1.08,1.09,1.11,1.12,1.13,1.14,1.15,1.16,\r\n1.17,2.01,2.02,2.03,2.04,2.05,2.06,2.07,2.08,2.09,2.11,2.12,2.13,2.14,2.15,2.16,2.17,3.01,3.02,\r\n3.03,3.04,3.05,3.06,3.07,3.08,3.09,4.01,4.02,4.03,4.04,4.05,4.06,4.07,4.08,4.09,5.01,5.03,5.04,5.02,3.11,3.12,3.13,3.03,3.18,4.11,1.17,3.19"}}
     * fyId : 1
     * startDate : 2020-01-03
     */

    private String access_token;
    private String organisationId;
    private boolean isEnglishDateEnabled;
    private String endDate;
    private Object nepaliNewDate;
    private long expiry;
    private String transactionDate;
    private UserBean user;
    private String fyId;
    private String startDate;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }

    public boolean isIsEnglishDateEnabled() {
        return isEnglishDateEnabled;
    }

    public void setIsEnglishDateEnabled(boolean isEnglishDateEnabled) {
        this.isEnglishDateEnabled = isEnglishDateEnabled;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Object getNepaliNewDate() {
        return nepaliNewDate;
    }

    public void setNepaliNewDate(Object nepaliNewDate) {
        this.nepaliNewDate = nepaliNewDate;
    }

    public long getExpiry() {
        return expiry;
    }

    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getFyId() {
        return fyId;
    }

    public void setFyId(String fyId) {
        this.fyId = fyId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public static class UserBean {
        /**
         * id : 2
         * username : Samajdar
         * name : Samajdar
         * roleId : 2
         * role : {"id":2,"updatedDate":"6/9/20 12:00 AM","name":"Admin","modules":",1.01,1.02,1.03,1.04,1.05,1.06,1.07,1.08,1.09,1.11,1.12,1.13,1.14,1.15,1.16,\r\n1.17,2.01,2.02,2.03,2.04,2.05,2.06,2.07,2.08,2.09,2.11,2.12,2.13,2.14,2.15,2.16,2.17,3.01,3.02,\r\n3.03,3.04,3.05,3.06,3.07,3.08,3.09,4.01,4.02,4.03,4.04,4.05,4.06,4.07,4.08,4.09,5.01,5.03,5.04,5.02,3.11,3.12,3.13,3.03,3.18,4.11,1.17,3.19"}
         */

        private int id;
        private String username;
        private String name;
        private int roleId;
        private RoleBean role;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public RoleBean getRole() {
            return role;
        }

        public void setRole(RoleBean role) {
            this.role = role;
        }

        public static class RoleBean {
            /**
             * id : 2
             * updatedDate : 6/9/20 12:00 AM
             * name : Admin
             * modules : ,1.01,1.02,1.03,1.04,1.05,1.06,1.07,1.08,1.09,1.11,1.12,1.13,1.14,1.15,1.16,
             1.17,2.01,2.02,2.03,2.04,2.05,2.06,2.07,2.08,2.09,2.11,2.12,2.13,2.14,2.15,2.16,2.17,3.01,3.02,
             3.03,3.04,3.05,3.06,3.07,3.08,3.09,4.01,4.02,4.03,4.04,4.05,4.06,4.07,4.08,4.09,5.01,5.03,5.04,5.02,3.11,3.12,3.13,3.03,3.18,4.11,1.17,3.19
             */

            private int id;
            private String updatedDate;
            private String name;
            private String modules;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getModules() {
                return modules;
            }

            public void setModules(String modules) {
                this.modules = modules;
            }
        }
    }
}

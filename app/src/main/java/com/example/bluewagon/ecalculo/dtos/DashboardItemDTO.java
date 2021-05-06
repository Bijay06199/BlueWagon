package com.example.bluewagon.ecalculo.dtos;

public class DashboardItemDTO {

    /**
     * id : 1
     * itemCode : 0
     * name : ZKT K40 Attendance Device
     * groupName : Attendance Device
     * subGroupName : ZKT Attendance Device
     * unitName : Pcs
     * availableQty : 0.0
     * openingStock : 25.0
     * closingStock : -39930.0
     * openingRate : 8000.0
     * vatAble : true
     * serviceType : false
     * vatType : Exclusive
     * status : ACTIVE
     * measurementId : {"id":2,"name":"Pieces","shortName":"Pcs","description":""}
     * salesRate : 10000.0
     * mrp : 10000.0
     * purchaseRate : 8000.0
     * salesMarginPercentage : 25.0
     * discountPercentage : 0.0
     * wholesaleRate : 0.0
     * itemGroupId : {"id":1,"name":"Attendance Device"}
     * itemSubGroupId : {"id":1,"name":"ZKT Attendance Device","itemGroupId":{"id":1,"name":"Attendance Device"}}
     */

    private int id;
    private String itemCode;
    private String name;
    private String groupName;
    private String subGroupName;
    private String unitName;
    private double availableQty;
    private double openingStock;
    private double closingStock;
    private double openingRate;
    private boolean vatAble;
    private boolean serviceType;
    private String vatType;
    private String status;
    private MeasurementIdBean measurementId;
    private double salesRate;
    private double mrp;
    private double purchaseRate;
    private double salesMarginPercentage;
    private double discountPercentage;
    private double wholesaleRate;
    private ItemGroupIdBean itemGroupId;
    private ItemSubGroupIdBean itemSubGroupId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSubGroupName() {
        return subGroupName;
    }

    public void setSubGroupName(String subGroupName) {
        this.subGroupName = subGroupName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public double getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(double availableQty) {
        this.availableQty = availableQty;
    }

    public double getOpeningStock() {
        return openingStock;
    }

    public void setOpeningStock(double openingStock) {
        this.openingStock = openingStock;
    }

    public double getClosingStock() {
        return closingStock;
    }

    public void setClosingStock(double closingStock) {
        this.closingStock = closingStock;
    }

    public double getOpeningRate() {
        return openingRate;
    }

    public void setOpeningRate(double openingRate) {
        this.openingRate = openingRate;
    }

    public boolean isVatAble() {
        return vatAble;
    }

    public void setVatAble(boolean vatAble) {
        this.vatAble = vatAble;
    }

    public boolean isServiceType() {
        return serviceType;
    }

    public void setServiceType(boolean serviceType) {
        this.serviceType = serviceType;
    }

    public String getVatType() {
        return vatType;
    }

    public void setVatType(String vatType) {
        this.vatType = vatType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MeasurementIdBean getMeasurementId() {
        return measurementId;
    }

    public void setMeasurementId(MeasurementIdBean measurementId) {
        this.measurementId = measurementId;
    }

    public double getSalesRate() {
        return salesRate;
    }

    public void setSalesRate(double salesRate) {
        this.salesRate = salesRate;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public double getPurchaseRate() {
        return purchaseRate;
    }

    public void setPurchaseRate(double purchaseRate) {
        this.purchaseRate = purchaseRate;
    }

    public double getSalesMarginPercentage() {
        return salesMarginPercentage;
    }

    public void setSalesMarginPercentage(double salesMarginPercentage) {
        this.salesMarginPercentage = salesMarginPercentage;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getWholesaleRate() {
        return wholesaleRate;
    }

    public void setWholesaleRate(double wholesaleRate) {
        this.wholesaleRate = wholesaleRate;
    }

    public ItemGroupIdBean getItemGroupId() {
        return itemGroupId;
    }

    public void setItemGroupId(ItemGroupIdBean itemGroupId) {
        this.itemGroupId = itemGroupId;
    }

    public ItemSubGroupIdBean getItemSubGroupId() {
        return itemSubGroupId;
    }

    public void setItemSubGroupId(ItemSubGroupIdBean itemSubGroupId) {
        this.itemSubGroupId = itemSubGroupId;
    }

    public static class MeasurementIdBean {
        /**
         * id : 2
         * name : Pieces
         * shortName : Pcs
         * description :
         */

        private int id;
        private String name;
        private String shortName;
        private String description;

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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class ItemGroupIdBean {
        /**
         * id : 1
         * name : Attendance Device
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

    public static class ItemSubGroupIdBean {
        /**
         * id : 1
         * name : ZKT Attendance Device
         * itemGroupId : {"id":1,"name":"Attendance Device"}
         */

        private int id;
        private String name;
        private ItemGroupIdBeanX itemGroupId;

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

        public ItemGroupIdBeanX getItemGroupId() {
            return itemGroupId;
        }

        public void setItemGroupId(ItemGroupIdBeanX itemGroupId) {
            this.itemGroupId = itemGroupId;
        }

        public static class ItemGroupIdBeanX {
            /**
             * id : 1
             * name : Attendance Device
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

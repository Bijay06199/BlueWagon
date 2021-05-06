package com.example.bluewagon.ecalculo.dtos;

import java.util.List;

public class SalesOrderDTO {

    /**
     * id : 2
     * orderNo : 1
     * paymentMethod : Credit
     * purchaseSalesDateNepali : 2076-04-05
     * accountName : Zenith IT Hub Pvt. Ltd.
     * address :
     * remarks : Sales Order no  1
     * referenceNumber :
     * reportType : Order
     * loadFrom : 0
     * loadedFromChallanOrOrder : false
     * applyExpensesOnBill : false
     * warehouseId : 1
     * fiscalYearId : 1
     * totalBillAmount : 150000.0
     * taxableAmount : 0.0
     * nonTaxableAmount : 150000.0
     * subTotalAmount : 150000.0
     * vatPercentage : 0.0
     * vatAmount : 0.0
     * discount : 0.0
     * discountPercentage : 0.0
     * cashPaidReceived : 0.0
     * export : 0.0
     * purchaseSalesDate : 2019-07-21
     * transactionDate : 2020-07-25
     * warehouse : {"id":1,"name":"Main"}
     * accountMaster : {"id":66,"name":"Zenith IT Hub Pvt. Ltd.","contactName":"","panNo":"","closingBalance":-60000,"isCashAccount":false,"isBankAccount":false}
     * cashTransactionMaster : {}
     * inventoryInOutDetailList : [{"id":2,"rate":5000,"costPerUnitRate":0,"quantity":30,"total":0,"userId":0,"discount":0,"itemMaster":{"id":2,"name":"xLab Barcode Scanner(Wired Laser Barcode Reader)","unitName":"Pcs","availableQty":0,"openingStock":0,"closingStock":50,"openingRate":0,"vatAble":true,"vatType":"Exclusive","salesRate":5000,"mrp":0,"purchaseRate":0,"salesMarginPercentage":0,"discountPercentage":0,"wholesaleRate":0},"multiUnitSales":false,"returnQty":0,"alternateUnitId":0,"alternateQty":0}]
     * inventoryInOutDetailDeleteList : []
     * inventoryExtraExpensesList : []
     */

    private int id;
    private int orderNo;
    private String paymentMethod;
    private String purchaseSalesDateNepali;
    private String accountName;
    private String address;
    private String remarks;
    private String referenceNumber;
    private String reportType;
    private int loadFrom;
    private boolean loadedFromChallanOrOrder;
    private boolean applyExpensesOnBill;
    private int warehouseId;
    private int fiscalYearId;
    private double totalBillAmount;
    private double taxableAmount;
    private double nonTaxableAmount;
    private double subTotalAmount;
    private double vatPercentage;
    private double vatAmount;
    private double discount;
    private double discountPercentage;
    private double cashPaidReceived;
    private double export;
    private String purchaseSalesDate;
    private String transactionDate;
    private WarehouseBean warehouse;
    private AccountMasterBean accountMaster;
    private CashTransactionMasterBean cashTransactionMaster;
    private List<InventoryInOutDetailListBean> inventoryInOutDetailList;
    private List<?> inventoryInOutDetailDeleteList;
    private List<?> inventoryExtraExpensesList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPurchaseSalesDateNepali() {
        return purchaseSalesDateNepali;
    }

    public void setPurchaseSalesDateNepali(String purchaseSalesDateNepali) {
        this.purchaseSalesDateNepali = purchaseSalesDateNepali;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public int getLoadFrom() {
        return loadFrom;
    }

    public void setLoadFrom(int loadFrom) {
        this.loadFrom = loadFrom;
    }

    public boolean isLoadedFromChallanOrOrder() {
        return loadedFromChallanOrOrder;
    }

    public void setLoadedFromChallanOrOrder(boolean loadedFromChallanOrOrder) {
        this.loadedFromChallanOrOrder = loadedFromChallanOrOrder;
    }

    public boolean isApplyExpensesOnBill() {
        return applyExpensesOnBill;
    }

    public void setApplyExpensesOnBill(boolean applyExpensesOnBill) {
        this.applyExpensesOnBill = applyExpensesOnBill;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getFiscalYearId() {
        return fiscalYearId;
    }

    public void setFiscalYearId(int fiscalYearId) {
        this.fiscalYearId = fiscalYearId;
    }

    public double getTotalBillAmount() {
        return totalBillAmount;
    }

    public void setTotalBillAmount(double totalBillAmount) {
        this.totalBillAmount = totalBillAmount;
    }

    public double getTaxableAmount() {
        return taxableAmount;
    }

    public void setTaxableAmount(double taxableAmount) {
        this.taxableAmount = taxableAmount;
    }

    public double getNonTaxableAmount() {
        return nonTaxableAmount;
    }

    public void setNonTaxableAmount(double nonTaxableAmount) {
        this.nonTaxableAmount = nonTaxableAmount;
    }

    public double getSubTotalAmount() {
        return subTotalAmount;
    }

    public void setSubTotalAmount(double subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }

    public double getVatPercentage() {
        return vatPercentage;
    }

    public void setVatPercentage(double vatPercentage) {
        this.vatPercentage = vatPercentage;
    }

    public double getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(double vatAmount) {
        this.vatAmount = vatAmount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getCashPaidReceived() {
        return cashPaidReceived;
    }

    public void setCashPaidReceived(double cashPaidReceived) {
        this.cashPaidReceived = cashPaidReceived;
    }

    public double getExport() {
        return export;
    }

    public void setExport(double export) {
        this.export = export;
    }

    public String getPurchaseSalesDate() {
        return purchaseSalesDate;
    }

    public void setPurchaseSalesDate(String purchaseSalesDate) {
        this.purchaseSalesDate = purchaseSalesDate;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public WarehouseBean getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseBean warehouse) {
        this.warehouse = warehouse;
    }

    public AccountMasterBean getAccountMaster() {
        return accountMaster;
    }

    public void setAccountMaster(AccountMasterBean accountMaster) {
        this.accountMaster = accountMaster;
    }

    public CashTransactionMasterBean getCashTransactionMaster() {
        return cashTransactionMaster;
    }

    public void setCashTransactionMaster(CashTransactionMasterBean cashTransactionMaster) {
        this.cashTransactionMaster = cashTransactionMaster;
    }

    public List<InventoryInOutDetailListBean> getInventoryInOutDetailList() {
        return inventoryInOutDetailList;
    }

    public void setInventoryInOutDetailList(List<InventoryInOutDetailListBean> inventoryInOutDetailList) {
        this.inventoryInOutDetailList = inventoryInOutDetailList;
    }

    public List<?> getInventoryInOutDetailDeleteList() {
        return inventoryInOutDetailDeleteList;
    }

    public void setInventoryInOutDetailDeleteList(List<?> inventoryInOutDetailDeleteList) {
        this.inventoryInOutDetailDeleteList = inventoryInOutDetailDeleteList;
    }

    public List<?> getInventoryExtraExpensesList() {
        return inventoryExtraExpensesList;
    }

    public void setInventoryExtraExpensesList(List<?> inventoryExtraExpensesList) {
        this.inventoryExtraExpensesList = inventoryExtraExpensesList;
    }

    public static class WarehouseBean {
        /**
         * id : 1
         * name : Main
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

    public static class AccountMasterBean {
    }

    public static class CashTransactionMasterBean {
    }

    public static class InventoryInOutDetailListBean {
        /**
         * id : 2
         * rate : 5000.0
         * costPerUnitRate : 0.0
         * quantity : 30.0
         * total : 0.0
         * userId : 0
         * discount : 0.0
         * itemMaster : {"id":2,"name":"xLab Barcode Scanner(Wired Laser Barcode Reader)","unitName":"Pcs","availableQty":0,"openingStock":0,"closingStock":50,"openingRate":0,"vatAble":true,"vatType":"Exclusive","salesRate":5000,"mrp":0,"purchaseRate":0,"salesMarginPercentage":0,"discountPercentage":0,"wholesaleRate":0}
         * multiUnitSales : false
         * returnQty : 0.0
         * alternateUnitId : 0
         * alternateQty : 0.0
         */

        private int id;
        private double rate;
        private double costPerUnitRate;
        private double quantity;
        private double total;
        private int userId;
        private double discount;
        private ItemMasterBean itemMaster;
        private boolean multiUnitSales;
        private double returnQty;
        private int alternateUnitId;
        private double alternateQty;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public double getCostPerUnitRate() {
            return costPerUnitRate;
        }

        public void setCostPerUnitRate(double costPerUnitRate) {
            this.costPerUnitRate = costPerUnitRate;
        }

        public double getQuantity() {
            return quantity;
        }

        public void setQuantity(double quantity) {
            this.quantity = quantity;
        }

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public double getDiscount() {
            return discount;
        }

        public void setDiscount(double discount) {
            this.discount = discount;
        }

        public ItemMasterBean getItemMaster() {
            return itemMaster;
        }

        public void setItemMaster(ItemMasterBean itemMaster) {
            this.itemMaster = itemMaster;
        }

        public boolean isMultiUnitSales() {
            return multiUnitSales;
        }

        public void setMultiUnitSales(boolean multiUnitSales) {
            this.multiUnitSales = multiUnitSales;
        }

        public double getReturnQty() {
            return returnQty;
        }

        public void setReturnQty(double returnQty) {
            this.returnQty = returnQty;
        }

        public int getAlternateUnitId() {
            return alternateUnitId;
        }

        public void setAlternateUnitId(int alternateUnitId) {
            this.alternateUnitId = alternateUnitId;
        }

        public double getAlternateQty() {
            return alternateQty;
        }

        public void setAlternateQty(double alternateQty) {
            this.alternateQty = alternateQty;
        }

        public static class ItemMasterBean {
            /**
             * id : 2
             * name : xLab Barcode Scanner(Wired Laser Barcode Reader)
             * unitName : Pcs
             * availableQty : 0.0
             * openingStock : 0.0
             * closingStock : 50.0
             * openingRate : 0.0
             * vatAble : true
             * vatType : Exclusive
             * salesRate : 5000.0
             * mrp : 0.0
             * purchaseRate : 0.0
             * salesMarginPercentage : 0.0
             * discountPercentage : 0.0
             * wholesaleRate : 0.0
             */

            private int id;
            private String name;
            private String unitName;
            private double availableQty;
            private double openingStock;
            private double closingStock;
            private double openingRate;
            private boolean vatAble;
            private String vatType;
            private double salesRate;
            private double mrp;
            private double purchaseRate;
            private double salesMarginPercentage;
            private double discountPercentage;
            private double wholesaleRate;

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

            public String getVatType() {
                return vatType;
            }

            public void setVatType(String vatType) {
                this.vatType = vatType;
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
        }
    }
}

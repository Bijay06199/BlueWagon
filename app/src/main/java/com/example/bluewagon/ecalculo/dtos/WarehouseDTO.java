package com.example.bluewagon.ecalculo.dtos;

public class WarehouseDTO {

    /**
     * id : 1
     * name : Butwal
     * location : Butwal
     * groupName : Test
     * warehouseGroup : {"id":3,"name":"Test"}
     * isMasterWarehouse : true
     */

    private int id;
    private String name;
    private String location;
    private String groupName;
    private WarehouseGroupBean warehouseGroup;
    private boolean isMasterWarehouse;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public WarehouseGroupBean getWarehouseGroup() {
        return warehouseGroup;
    }

    public void setWarehouseGroup(WarehouseGroupBean warehouseGroup) {
        this.warehouseGroup = warehouseGroup;
    }

    public boolean isIsMasterWarehouse() {
        return isMasterWarehouse;
    }

    public void setIsMasterWarehouse(boolean isMasterWarehouse) {
        this.isMasterWarehouse = isMasterWarehouse;
    }

    public static class WarehouseGroupBean {
        /**
         * id : 3
         * name : Test
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

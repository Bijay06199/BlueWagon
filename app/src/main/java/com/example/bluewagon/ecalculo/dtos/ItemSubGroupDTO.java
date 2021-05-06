package com.example.bluewagon.ecalculo.dtos;

public class ItemSubGroupDTO {

    /**
     * id : 1
     * createdDate : 5/21/20 12:00 AM
     * updatedDate : 5/21/20 12:00 AM
     * name : Plastic
     * groupName : Test Group
     * itemGroupId : {"id":2,"name":"Test Group"}
     */

    private int id;
    private String createdDate;
    private String updatedDate;
    private String name;
    private String groupName;
    private ItemGroupIdBean itemGroupId;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ItemGroupIdBean getItemGroupId() {
        return itemGroupId;
    }

    public void setItemGroupId(ItemGroupIdBean itemGroupId) {
        this.itemGroupId = itemGroupId;
    }

    public static class ItemGroupIdBean {
        /**
         * id : 2
         * name : Test Group
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

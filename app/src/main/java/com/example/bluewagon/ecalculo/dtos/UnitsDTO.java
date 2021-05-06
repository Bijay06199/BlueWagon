package com.example.bluewagon.ecalculo.dtos;

public class UnitsDTO {
    /**
     * id : 1
     * createdDate : 5/21/20 12:00 AM
     * name : Kilogram
     * shortName : kg
     * description : sasas
     */

    private int id;
    private String createdDate;
    private String name;
    private String shortName;
    private String description;

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


//    private String unitName;
//    private String unitShortName;
//
//    public String getUnitName() {
//        return unitName;
//    }
//
//    public void setUnitName(String unitName) {
//        this.unitName = unitName;
//    }
//
//    public String getUnitShortName() {
//        return unitShortName;
//    }
//
//    public void setUnitShortName(String unitShortName) {
//        this.unitShortName = unitShortName;
//    }
}

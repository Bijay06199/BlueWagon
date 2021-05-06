package com.example.bluewagon.ecalculo.dtos;

import java.util.List;

public class AccountNatureDTO {

    /**
     * id : 1
     * name : Assets
     * accountHeadList : []
     */

    private int id;
    private String name;
    private List<?> accountHeadList;

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

    public List<?> getAccountHeadList() {
        return accountHeadList;
    }

    public void setAccountHeadList(List<?> accountHeadList) {
        this.accountHeadList = accountHeadList;
    }
}

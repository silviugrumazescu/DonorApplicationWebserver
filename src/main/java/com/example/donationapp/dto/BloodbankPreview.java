package com.example.donationapp.dto;

import com.example.donationapp.model.District;

public class BloodbankPreview {

    private Integer id;
    private String name;
    private String district;

    public BloodbankPreview(Integer id, String name, District district) {
        this.id = id;
        this.name = name;
        this.district = district.name();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district.name();
    }
}

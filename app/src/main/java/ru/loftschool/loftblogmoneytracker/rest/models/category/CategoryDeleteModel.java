package ru.loftschool.loftblogmoneytracker.rest.models.category;

import com.google.gson.annotations.Expose;

/**
 * Created by Александр on 20.09.2015.
 */
public class CategoryDeleteModel {

    @Expose
    private String status;
    @Expose
    private Integer data;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getData() {
        return data;
    }
    public void setData(Integer data) {
        this.data = data;
    }
}

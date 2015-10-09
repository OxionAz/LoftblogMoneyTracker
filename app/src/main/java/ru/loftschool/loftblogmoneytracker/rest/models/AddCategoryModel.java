package ru.loftschool.loftblogmoneytracker.rest.models;

import com.google.gson.annotations.Expose;

/**
 * Created by Александр on 20.09.2015.
 */
public class AddCategoryModel {

    @Expose
    private String status;
    @Expose
    private Data data;

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The data
     */
    public Data getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(Data data) {
        this.data = data;
    }
}

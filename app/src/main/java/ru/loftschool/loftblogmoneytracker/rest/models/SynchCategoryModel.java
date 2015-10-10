package ru.loftschool.loftblogmoneytracker.rest.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Александр on 10.10.2015.
 */
public class SynchCategoryModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<SynchCategoryDataModel> data = new ArrayList<SynchCategoryDataModel>();

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
    public List<SynchCategoryDataModel> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<SynchCategoryDataModel> data) {
        this.data = data;
    }
}

package ru.loftschool.loftblogmoneytracker.rest.models.category;

import com.google.gson.annotations.Expose;

/**
 * Created by Александр on 20.09.2015.
 */
public class CategoryModel {

    @Expose
    private String status;
    @Expose
    private CategoryDataModel data;

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
    public CategoryDataModel getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(CategoryDataModel data) {
        this.data = data;
    }
}

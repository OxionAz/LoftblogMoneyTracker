package ru.loftschool.loftblogmoneytracker.rest.models;

import com.google.gson.annotations.Expose;

/**
 * Created by Александр on 20.09.2015.
 */
public class AddCategoryModel {

    @Expose
    private String status;
    @Expose
    private AddCategoryDataModel addCategoryDataModel;

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
     * The addCategoryDataModel
     */
    public AddCategoryDataModel getAddCategoryDataModel() {
        return addCategoryDataModel;
    }

    /**
     *
     * @param addCategoryDataModel
     * The addCategoryDataModel
     */
    public void setAddCategoryDataModel(AddCategoryDataModel addCategoryDataModel) {
        this.addCategoryDataModel = addCategoryDataModel;
    }
}

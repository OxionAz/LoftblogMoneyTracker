package ru.loftschool.loftblogmoneytracker.rest.models.category;

import com.google.gson.annotations.Expose;

/**
 * Created by Александр on 20.09.2015.
 */
public class CategoryModel {

    @Expose
    private String status;
    @Expose
    private CategoryDataModel categoryDataModel;

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
     * The categoryDataModel
     */
    public CategoryDataModel getCategoryDataModel() {
        return categoryDataModel;
    }

    /**
     *
     * @param categoryDataModel
     * The categoryDataModel
     */
    public void setCategoryDataModel(CategoryDataModel categoryDataModel) {
        this.categoryDataModel = categoryDataModel;
    }
}

package ru.loftschool.loftblogmoneytracker.rest.models.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Александр on 11.10.2015.
 */
public class GetCategoryModel {

    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<GetCategoryDataModel> categories = new ArrayList<GetCategoryDataModel>();

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
    public List<GetCategoryDataModel> getCategories() {
        return categories;
    }

    /**
     *
     * @param categoriesItems
     * The data
     */
    public void setCategories(List<GetCategoryDataModel> categoriesItems) {
        this.categories = categoriesItems;
    }
}

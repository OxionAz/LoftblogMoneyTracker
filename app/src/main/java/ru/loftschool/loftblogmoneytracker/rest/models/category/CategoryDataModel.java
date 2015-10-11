package ru.loftschool.loftblogmoneytracker.rest.models.category;

import com.google.gson.annotations.Expose;

/**
 * Created by Александр on 20.09.2015.
 */
public class CategoryDataModel {

    @Expose
    private String title;
    @Expose
    private Integer id;

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }
}

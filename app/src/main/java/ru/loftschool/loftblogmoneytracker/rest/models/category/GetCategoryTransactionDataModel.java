package ru.loftschool.loftblogmoneytracker.rest.models.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Александр on 11.10.2015.
 */
public class GetCategoryTransactionDataModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("sum")
    @Expose
    private Integer sum;
    @SerializedName("tr_date")
    @Expose
    private String trDate;

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

    /**
     *
     * @return
     * The categoryId
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @param categoryId
     * The category_id
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     *
     * @return
     * The comment
     */
    public String getComment() {
        return comment;
    }

    /**
     *
     * @param comment
     * The comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     *
     * @return
     * The sum
     */
    public Integer getSum() {
        return sum;
    }

    /**
     *
     * @param sum
     * The sum
     */
    public void setSum(Integer sum) {
        this.sum = sum;
    }

    /**
     *
     * @return
     * The trDate
     */
    public String getTrDate() {
        return trDate;
    }

    /**
     *
     * @param trDate
     * The tr_date
     */
    public void setTrDate(String trDate) {
        this.trDate = trDate;
    }
}

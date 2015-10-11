package ru.loftschool.loftblogmoneytracker.rest.models.transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Александр on 10.10.2015.
 */
public class SynchTransactionDataModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("sum")
    @Expose
    private String sum;
    @SerializedName("tr_date")
    @Expose
    private String trDate;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The categoryId
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @param categoryId
     * The category_id
     */
    public void setCategoryId(String categoryId) {
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
    public String getSum() {
        return sum;
    }

    /**
     *
     * @param sum
     * The sum
     */
    public void setSum(String sum) {
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

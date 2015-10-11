package ru.loftschool.loftblogmoneytracker.rest.models.category;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Александр on 11.10.2015.
 */
public class GetCategoryTransactionModel {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("transactions")
    @Expose
    private List<GetCategoryTransactionDataModel> transactions = new ArrayList<GetCategoryTransactionDataModel>();

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
     * The transactions
     */
    public List<GetCategoryTransactionDataModel> getTransactions() {
        return transactions;
    }

    /**
     *
     * @param transactions
     * The transactions
     */
    public void setTransactions(List<GetCategoryTransactionDataModel> transactions) {
        this.transactions = transactions;
    }
}

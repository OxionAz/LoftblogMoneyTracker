package ru.loftschool.loftblogmoneytracker.rest.models.transaction;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetTransactionByCategoriesModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("transactions")
    @Expose
    private List<GetTransactionDataModel> transactions = new ArrayList<>();

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
    public List<GetTransactionDataModel> getTransactions() {
        return transactions;
    }

    /**
     *
     * @param transactions
     * The transactions
     */
    public void setTransactions(List<GetTransactionDataModel> transactions) {
        this.transactions = transactions;
    }

}
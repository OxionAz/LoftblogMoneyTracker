package ru.loftschool.moneytrackerbyoxion.rest.models.transaction;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ru.loftschool.moneytrackerbyoxion.rest.models.category.GetCategoryTransactionDataModel;

/**
 * Created by Александр on 10.10.2015.
 */
public class GetTransactionModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<GetCategoryTransactionDataModel> data = new ArrayList<GetCategoryTransactionDataModel>();

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
    public List<GetCategoryTransactionDataModel> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<GetCategoryTransactionDataModel> data) {
        this.data = data;
    }
}

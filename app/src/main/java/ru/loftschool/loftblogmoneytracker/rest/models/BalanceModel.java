package ru.loftschool.loftblogmoneytracker.rest.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by Александр on 11.10.2015.
 */
public class BalanceModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("balance")
    @Expose
    private String balance;

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
     * The balance
     */
    public String getBalance() {
        return balance;
    }

    /**
     *
     * @param balance
     * The balance
     */
    public void setBalance(String balance) {
        this.balance = balance;
    }

}

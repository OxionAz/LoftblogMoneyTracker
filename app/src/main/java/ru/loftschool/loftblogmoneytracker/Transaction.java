package ru.loftschool.loftblogmoneytracker;

import java.util.Date;

/**
 * Created by Александр on 26.08.2015.
 */
public class Transaction {

    public String title;
    public Integer sum;
    public String date;

    public Transaction(String title, Integer sum, String date){
        this.sum = sum;
        this.title = title;
        this.date = date;
    }

    public String getSum(){
        return  Integer.toString(sum);
    }
}

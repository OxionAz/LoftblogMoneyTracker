package ru.loftschool.loftblogmoneytracker;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Александр on 26.08.2015.
 */
public class Transaction {

    private String title;
    private int sum;
    private Date date;

    public Transaction(String title, int sum, Date date){
        this.sum = sum;
        this.title = title;
        this.date = date;
    }

    public String getTitle(){
        return title;
    }
    public String getSum(){
        return  Integer.toString(sum);
    }
    public String getDate(){
        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy");
        return  format1.format(date);
    }
}

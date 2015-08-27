package ru.loftschool.loftblogmoneytracker;

/**
 * Created by Александр on 26.08.2015.
 */
public class Transaction {

    public String title;
    public String sum;

    public Transaction(String title, String sum){
        this.sum=sum;
        this.title = title;

    }
}

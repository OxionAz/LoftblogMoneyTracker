package ru.loftschool.loftblogmoneytracker.database.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Александр on 08.09.2015.
 */

@Table(name = "Expenses")
public class Expenses extends Model {

    @Column(name = "name")
    public String name;

    @Column(name = "sum")
    public String sum;

    @Column(name = "date")
    public String date;

    public Expenses(){
        super();
    }

    public Expenses(String name, String sum, String date){
        super();
        this.name = name;
        this.sum = sum;
        this.date = date;
    }

    @Column(name = "Categories")
    public Categories categories;
}

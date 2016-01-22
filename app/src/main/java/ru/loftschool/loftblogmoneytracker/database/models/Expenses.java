package ru.loftschool.loftblogmoneytracker.database.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.Date;

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
    public Date date;

    @Column(name = "Category", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    public Categories category;

    public Expenses(){
        super();
    }

    public Expenses(String name, String sum, Date date, Categories category){
        super();
        this.name = name;
        this.sum = sum;
        this.date = date;
        this.category = category;
    }
}

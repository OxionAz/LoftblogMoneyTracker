package ru.loftschool.loftblogmoneytracker.database.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import java.util.List;

/**
 * Created by Александр on 08.09.2015.
 */

@Table(name = "Categories")
public class Categories extends Model {

    @Column(name = "category")
    public String category;

    public Categories(){
        super();
    }

    public Categories(String category){
        super();
        this.category = category;
    }

    public List<Expenses> expenses() {
        return getMany(Expenses.class, "Categories");
    }
}

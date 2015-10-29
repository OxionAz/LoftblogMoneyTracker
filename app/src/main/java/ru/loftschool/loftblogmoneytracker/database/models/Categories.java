package ru.loftschool.loftblogmoneytracker.database.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.util.SQLiteUtils;

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

    @Override
    public String toString() {
        return category;
    }

    public List<Expenses> expenses() {
        return getMany(Expenses.class, "Category");
    }

    public static List<Categories> selectByNameCaseInsensitive(String name) {
        return SQLiteUtils.rawQuery(Categories.class, "SELECT * from Categories where lower(category) = ?", new String[]{name.toLowerCase()});
    }
}

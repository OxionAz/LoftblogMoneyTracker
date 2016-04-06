package ru.loftschool.loftblogmoneytracker.util;

import com.activeandroid.query.Select;
import java.util.List;
import ru.loftschool.loftblogmoneytracker.database.models.Categories;

public class CheckUserData {

    public static void setCategories(){
        if (getCategories().isEmpty()){
            new Categories("Food").save();
            new Categories("Stuff").save();
            new Categories("Clothes").save();
            new Categories("Fun").save();
            new Categories("Other").save();
        }
    }

    private static List<Categories> getCategories(){
        return new Select().from(Categories.class).execute();
    }
}
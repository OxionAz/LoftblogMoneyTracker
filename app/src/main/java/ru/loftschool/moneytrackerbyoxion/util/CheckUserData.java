package ru.loftschool.moneytrackerbyoxion.util;

import com.activeandroid.query.Select;
import java.util.List;
import ru.loftschool.moneytrackerbyoxion.database.models.Categories;

public class CheckUserData {

    public static void setCategories(){
        if (getCategories().isEmpty()){
            new Categories("Еда").save();
            new Categories("Вещи").save();
            new Categories("Одежда").save();
            new Categories("Развлечения").save();
            new Categories("Другое").save();
        }
    }

    private static List<Categories> getCategories(){
        return new Select().from(Categories.class).execute();
    }
}
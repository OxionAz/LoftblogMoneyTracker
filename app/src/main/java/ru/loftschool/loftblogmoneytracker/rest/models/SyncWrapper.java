package ru.loftschool.loftblogmoneytracker.rest.models;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.List;

import ru.loftschool.loftblogmoneytracker.rest.models.category.SCD;

/**
 * Created by Александр on 31.10.2015.
 */
public class SyncWrapper {

    public List<SCD> categories;

    public SyncWrapper(List<SCD> categories) {
        this.categories = categories;}

    @SuppressLint("LongLogTag")
    @Override
    public String toString(){
        String syncCategories = "";
        String comma = ",";
        for (int i=0; i<categories.size(); i++){
            syncCategories+=categories.get(i);
            if(i<categories.size()-1) syncCategories+=comma;
        }
        Log.d("Запрос на синхронизацию: ", syncCategories);
        return "["+syncCategories+"]";
    }
}

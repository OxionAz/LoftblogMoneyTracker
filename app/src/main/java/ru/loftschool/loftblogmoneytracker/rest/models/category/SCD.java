package ru.loftschool.loftblogmoneytracker.rest.models.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SCD {

    private int id;
    private String title;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id+1;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString(){
        return "{\"id\":"+id+",\"title\":\""+title+"\"}";
    }
}

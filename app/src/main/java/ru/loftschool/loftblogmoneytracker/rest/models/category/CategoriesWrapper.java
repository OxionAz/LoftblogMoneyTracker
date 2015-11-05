package ru.loftschool.loftblogmoneytracker.rest.models.category;

public class CategoriesWrapper {

    private int id;
    private String title;

    public void setId(int id) {
        this.id = id+1;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString(){
        return "{\"id\":"+id+",\"title\":\""+title+"\"}";
    }
}

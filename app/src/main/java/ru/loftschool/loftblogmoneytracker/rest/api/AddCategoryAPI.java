package ru.loftschool.loftblogmoneytracker.rest.api;

import retrofit.http.GET;
import retrofit.http.Query;
import ru.loftschool.loftblogmoneytracker.rest.models.AddCategoryModel;

/**
 * Created by Александр on 20.09.2015.
 */
public interface AddCategoryAPI {

    @GET("/categories/add")
    AddCategoryModel addCategory(@Query("title") String title, @Query("auth_token") String token);
}

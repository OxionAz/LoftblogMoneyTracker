package ru.loftschool.loftblogmoneytracker.rest.api;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import ru.loftschool.loftblogmoneytracker.database.models.Categories;
import ru.loftschool.loftblogmoneytracker.rest.models.AddCategoryModel;
import ru.loftschool.loftblogmoneytracker.rest.models.SynchCategoryModel;

/**
 * Created by Александр on 10.10.2015.
 */
public interface UserCategoryAPI {

    @GET("/categories/add")
    AddCategoryModel addCategory(@Query("title") String title,
                                 @Query("google_token") String gToken,
                                 @Query("auth_token") String token);

    @GET("/categories/synch")
    SynchCategoryModel synchCategory(@Query("data") List<Categories> categories,
                                     @Query("auth_token") String token);
}

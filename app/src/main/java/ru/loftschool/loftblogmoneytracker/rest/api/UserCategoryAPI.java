package ru.loftschool.loftblogmoneytracker.rest.api;

import java.util.ArrayList;
import java.util.List;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import ru.loftschool.loftblogmoneytracker.database.models.Categories;
import ru.loftschool.loftblogmoneytracker.rest.models.category.CategoryDataModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.CategoryModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.GetCategoryModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.GetCategoryTransactionModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.SynchCategoryDataModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.SynchCategoryModel;

/**
 * Created by Александр on 10.10.2015.
 */
public interface UserCategoryAPI {

    @GET("/categories")
    GetCategoryModel getAllCategories(@Query("google_token") String gToken,
                                      @Query("auth_token") String token);

    @GET("/categories/add")
    CategoryModel addCategory(@Query("title") String title,
                                 @Query("google_token") String gToken,
                                 @Query("auth_token") String token);

    @GET("/categories/edit")
    CategoryModel editCategory(@Query("title") String title,
                               @Query("id") Integer id,
                               @Query("google_token") String gToken,
                               @Query("auth_token") String token);

    @GET("/categories/del")
    CategoryModel deleteCategory(@Query("id") Integer id,
                                 @Query("google_token") String gToken,
                                 @Query("auth_token") String token);

    @GET("/categories/synch")
    SynchCategoryModel synchCategory(@Query("data") List<SynchCategoryDataModel> data,
                                     @Query("auth_token") String token);

    @GET("/categories/{id}")
    ArrayList<GetCategoryTransactionModel> getCT(@Path("id") int id,
                                                 @Query("google_token") String gToken,
                                                 @Query("auth_token") String token);
}

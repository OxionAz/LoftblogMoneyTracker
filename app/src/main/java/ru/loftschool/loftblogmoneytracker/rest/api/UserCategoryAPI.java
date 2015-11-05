package ru.loftschool.loftblogmoneytracker.rest.api;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import ru.loftschool.loftblogmoneytracker.rest.models.category.CategoryModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.GetCategoryModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.GetCategoryTransactionModel;

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
    void synchCategory (@Query(value = "data", encodeName = false, encodeValue = false) String data,
                        @Query("google_token") String gToken,
                        @Query("auth_token") String token,
                        Callback<GetCategoryModel> synch);

    @GET("/categories/{id}")
    ArrayList<GetCategoryTransactionModel> getCT(@Path("id") int id,
                                                 @Query("google_token") String gToken,
                                                 @Query("auth_token") String token);


}

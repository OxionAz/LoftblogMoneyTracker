package ru.loftschool.loftblogmoneytracker.rest.api;

import retrofit.http.GET;
import retrofit.http.Query;
import ru.loftschool.loftblogmoneytracker.rest.models.AddCategoryModel;
import ru.loftschool.loftblogmoneytracker.rest.models.UserLogoutModel;

/**
 * Created by Александр on 20.09.2015.
 */
public interface LogoutAPI {

    @GET("/logout")
    UserLogoutModel logoutUser();
}

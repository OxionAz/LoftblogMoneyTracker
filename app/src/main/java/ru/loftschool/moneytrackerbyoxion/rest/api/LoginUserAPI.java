package ru.loftschool.moneytrackerbyoxion.rest.api;

import retrofit.http.GET;
import retrofit.http.Query;
import ru.loftschool.moneytrackerbyoxion.rest.models.login.UserLoginModel;

/**
 * Created by Александр on 16.09.2015.
 */
public interface LoginUserAPI {

    @GET("/auth")
    UserLoginModel loginUser(@Query("login") String login,
                             @Query("password") String password);
}
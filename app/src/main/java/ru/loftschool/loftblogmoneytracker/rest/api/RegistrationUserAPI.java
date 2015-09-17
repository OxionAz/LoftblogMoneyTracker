package ru.loftschool.loftblogmoneytracker.rest.api;

import retrofit.http.GET;
import retrofit.http.Query;
import ru.loftschool.loftblogmoneytracker.rest.models.UserRegisterModel;

/**
 * Created by Александр on 16.09.2015.
 */
public interface RegistrationUserAPI {

    @GET("/auth")
    UserRegisterModel registerUser(@Query("login") String login,
                                   @Query("password") String password,
                                   @Query("register") String register);
}

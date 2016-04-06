package ru.loftschool.moneytrackerbyoxion.rest.api;

import retrofit.http.GET;
import ru.loftschool.moneytrackerbyoxion.rest.models.login.UserLogoutModel;

/**
 * Created by Александр on 20.09.2015.
 */
public interface LogoutAPI {

    @GET("/logout")
    UserLogoutModel logoutUser();
}

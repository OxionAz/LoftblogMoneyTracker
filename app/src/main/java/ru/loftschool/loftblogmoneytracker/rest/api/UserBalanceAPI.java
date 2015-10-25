package ru.loftschool.loftblogmoneytracker.rest.api;

import retrofit.http.GET;
import retrofit.http.Query;
import ru.loftschool.loftblogmoneytracker.rest.models.BalanceModel;

/**
 * Created by Александр on 11.10.2015.
 */
public interface UserBalanceAPI {

    @GET("/balance")
    BalanceModel getBalance(@Query("google_token") String gToken,
                            @Query("auth_token") String token);

    @GET("/balance")
    BalanceModel setBalance(@Query("set") String balance,
                            @Query("google_token") String gToken,
                            @Query("auth_token") String token);
}

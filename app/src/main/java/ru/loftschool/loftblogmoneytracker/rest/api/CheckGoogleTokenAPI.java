package ru.loftschool.loftblogmoneytracker.rest.api;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import ru.loftschool.loftblogmoneytracker.rest.models.GoogleTokenStatusModel;

/**
 * Created by Александр on 06.10.2015.
 */
public interface CheckGoogleTokenAPI {
    @GET("/gcheck")
    void tokenStatus (@Query("google_token") String gToken, Callback<GoogleTokenStatusModel> modelCallback);

    @GET("/gjson")
    GoogleTokenStatusModel googleJson (@Query("google_token") String gToken);
}

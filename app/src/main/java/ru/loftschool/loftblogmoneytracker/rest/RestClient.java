package ru.loftschool.loftblogmoneytracker.rest;

import retrofit.RestAdapter;
import ru.loftschool.loftblogmoneytracker.rest.api.RegistrationUserAPI;

/**
 * Created by Александр on 16.09.2015.
 */
public class RestClient {

    private static final String BASE_URL = "http://62.109.17.114/";

    private RegistrationUserAPI registrationUserAPI;

    RestClient(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .build();

        registrationUserAPI = restAdapter.create(RegistrationUserAPI.class);
    }

    public RegistrationUserAPI getRegistrationUserAPI(){
        return registrationUserAPI;
    }
}

package ru.loftschool.loftblogmoneytracker.rest;

import retrofit.RestAdapter;
import ru.loftschool.loftblogmoneytracker.rest.api.LoginUserAPI;
import ru.loftschool.loftblogmoneytracker.rest.api.LogoutAPI;
import ru.loftschool.loftblogmoneytracker.rest.api.RegistrationUserAPI;
import ru.loftschool.loftblogmoneytracker.rest.api.CheckGoogleTokenAPI;
import ru.loftschool.loftblogmoneytracker.rest.api.UserCategoryAPI;

/**
 * Created by Александр on 16.09.2015.
 */
public class RestClient {

    private static final String BASE_URL = "http://62.109.17.114/";

    private RegistrationUserAPI registrationUserAPI;
    private LoginUserAPI loginUserAPI;
    private UserCategoryAPI categoryAPI;
    private LogoutAPI logoutAPI;
    private CheckGoogleTokenAPI checkGoogleTokenAPI;

    public RestClient(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .build();

        registrationUserAPI = restAdapter.create(RegistrationUserAPI.class);
        loginUserAPI = restAdapter.create(LoginUserAPI.class);
        categoryAPI = restAdapter.create(UserCategoryAPI.class);
        logoutAPI = restAdapter.create(LogoutAPI.class);
        checkGoogleTokenAPI = restAdapter.create(CheckGoogleTokenAPI.class);
    }

    public RegistrationUserAPI getRegistrationUserAPI(){return registrationUserAPI;}
    public LoginUserAPI getLoginUserAPI(){return loginUserAPI;}
    public UserCategoryAPI getCategoryAPI(){return categoryAPI;}
    public LogoutAPI getLogoutAPI(){return logoutAPI;}
    public CheckGoogleTokenAPI getCheckGoogleTokenAPI(){return checkGoogleTokenAPI;}
}

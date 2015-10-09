package ru.loftschool.loftblogmoneytracker.rest;

import retrofit.RestAdapter;
import ru.loftschool.loftblogmoneytracker.rest.api.AddCategoryAPI;
import ru.loftschool.loftblogmoneytracker.rest.api.LoginUserAPI;
import ru.loftschool.loftblogmoneytracker.rest.api.LogoutAPI;
import ru.loftschool.loftblogmoneytracker.rest.api.RegistrationUserAPI;

/**
 * Created by Александр on 16.09.2015.
 */
public class RestClient {

    private static final String BASE_URL = "http://62.109.17.114/";

    private RegistrationUserAPI registrationUserAPI;
    private LoginUserAPI loginUserAPI;
    private AddCategoryAPI addCategoryAPI;
    private LogoutAPI logoutAPI;

    RestClient(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .build();

        registrationUserAPI = restAdapter.create(RegistrationUserAPI.class);
        loginUserAPI = restAdapter.create(LoginUserAPI.class);
        addCategoryAPI = restAdapter.create(AddCategoryAPI.class);
        logoutAPI = restAdapter.create(LogoutAPI.class);
    }

    public RegistrationUserAPI getRegistrationUserAPI(){return registrationUserAPI;}
    public LoginUserAPI getLoginUserAPI(){return loginUserAPI;}
    public AddCategoryAPI getAddCategoryAPI(){return addCategoryAPI;}
    public LogoutAPI getLogoutAPI(){return logoutAPI;}
}

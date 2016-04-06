package ru.loftschool.moneytrackerbyoxion.rest;

import retrofit.RestAdapter;
import ru.loftschool.moneytrackerbyoxion.rest.api.LoginUserAPI;
import ru.loftschool.moneytrackerbyoxion.rest.api.LogoutAPI;
import ru.loftschool.moneytrackerbyoxion.rest.api.RegistrationUserAPI;
import ru.loftschool.moneytrackerbyoxion.rest.api.CheckGoogleTokenAPI;
import ru.loftschool.moneytrackerbyoxion.rest.api.UserBalanceAPI;
import ru.loftschool.moneytrackerbyoxion.rest.api.UserCategoryAPI;
import ru.loftschool.moneytrackerbyoxion.rest.api.UserTransactionAPI;

/**
 * Created by Александр on 16.09.2015.
 */
public class RestClient {

    private static final String BASE_URL = "http://lmt.loftblog.tmweb.ru/";

    private RegistrationUserAPI registrationUserAPI;
    private LoginUserAPI loginUserAPI;
    private LogoutAPI logoutAPI;
    private CheckGoogleTokenAPI checkGoogleTokenAPI;
    private UserCategoryAPI userCategoryAPI;
    private UserTransactionAPI userTransactionAPI;
    private UserBalanceAPI userBalanceAPI;

    public RestClient(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .build();

        registrationUserAPI = restAdapter.create(RegistrationUserAPI.class);
        loginUserAPI = restAdapter.create(LoginUserAPI.class);
        logoutAPI = restAdapter.create(LogoutAPI.class);
        checkGoogleTokenAPI = restAdapter.create(CheckGoogleTokenAPI.class);
        userCategoryAPI = restAdapter.create(UserCategoryAPI.class);
        userTransactionAPI = restAdapter.create(UserTransactionAPI.class);
        userBalanceAPI = restAdapter.create(UserBalanceAPI.class);
    }

    public RegistrationUserAPI getRegistrationUserAPI(){return registrationUserAPI;}
    public LoginUserAPI getLoginUserAPI(){return loginUserAPI;}
    public LogoutAPI getLogoutAPI(){return logoutAPI;}
    public CheckGoogleTokenAPI getCheckGoogleTokenAPI(){return checkGoogleTokenAPI;}
    public UserCategoryAPI getUserCategoryAPI(){return userCategoryAPI;}
    public UserTransactionAPI getUserTransactionAPI(){return userTransactionAPI;}
    public UserBalanceAPI getUserBalanceAPI(){return userBalanceAPI;}
}
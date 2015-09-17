package ru.loftschool.loftblogmoneytracker.rest;

import ru.loftschool.loftblogmoneytracker.rest.models.UserRegisterModel;

/**
 * Created by Александр on 16.09.2015.
 */
public class RestService {

    private static final String FLAG = "1";

    public UserRegisterModel register(String login, String password) {
        RestClient restClient = new RestClient();

        return restClient.getRegistrationUserAPI().registerUser(login, password, FLAG);
    }
}

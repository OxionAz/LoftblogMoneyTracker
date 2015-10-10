package ru.loftschool.loftblogmoneytracker.rest;

import java.util.List;

import ru.loftschool.loftblogmoneytracker.database.models.Categories;
import ru.loftschool.loftblogmoneytracker.rest.models.AddCategoryModel;
import ru.loftschool.loftblogmoneytracker.rest.models.SynchCategoryModel;
import ru.loftschool.loftblogmoneytracker.rest.models.UserLoginModel;
import ru.loftschool.loftblogmoneytracker.rest.models.UserLogoutModel;
import ru.loftschool.loftblogmoneytracker.rest.models.UserRegisterModel;

/**
 * Created by Александр on 16.09.2015.
 */
public class RestService {

    private static final String FLAG = "1";
    RestClient restClient;

    public RestService(){
        restClient = new RestClient();
    }

    public UserRegisterModel register(String login, String password) {
        return restClient.getRegistrationUserAPI().registerUser(login, password, FLAG);
    }

    public UserLoginModel login(String login, String password){
        return restClient.getLoginUserAPI().loginUser(login, password);
    }

    public AddCategoryModel addCategory(String title, String gToken, String token){
        return restClient.getCategoryAPI().addCategory(title, gToken, token);
    }

    public UserLogoutModel logout(){
        return restClient.getLogoutAPI().logoutUser();
    }

    public SynchCategoryModel synchCategory (List<Categories> categories, String token){
        return restClient.getCategoryAPI().synchCategory(categories, token);
    }
}

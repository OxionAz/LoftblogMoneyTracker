package ru.loftschool.loftblogmoneytracker.rest;

import java.util.List;

import ru.loftschool.loftblogmoneytracker.database.models.Categories;
import ru.loftschool.loftblogmoneytracker.database.models.Expenses;
import ru.loftschool.loftblogmoneytracker.rest.models.BalanceModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.CategoryModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.GetCategoryModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.GetCategoryTransactionModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.SynchCategoryModel;
import ru.loftschool.loftblogmoneytracker.rest.models.login.UserLoginModel;
import ru.loftschool.loftblogmoneytracker.rest.models.login.UserLogoutModel;
import ru.loftschool.loftblogmoneytracker.rest.models.login.UserRegisterModel;
import ru.loftschool.loftblogmoneytracker.rest.models.transaction.AddTransactionModel;
import ru.loftschool.loftblogmoneytracker.rest.models.transaction.GetTransactionByCategoriesModel;
import ru.loftschool.loftblogmoneytracker.rest.models.transaction.GetTransactionModel;
import ru.loftschool.loftblogmoneytracker.rest.models.transaction.SynchTransactionModel;

/**
 * Created by Александр on 16.09.2015.
 */
public class RestService {

    private static final String FLAG = "1";
    RestClient restClient;

    public RestService(){
        restClient = new RestClient();
    }

    //LoginQueries

    public UserRegisterModel register(String login, String password) {
        return restClient.getRegistrationUserAPI().registerUser(login, password, FLAG);
    }

    public UserLoginModel login(String login, String password){
        return restClient.getLoginUserAPI().loginUser(login, password);
    }

    public UserLogoutModel logout(){
        return restClient.getLogoutAPI().logoutUser();
    }

    //CategoryQueries

    public CategoryModel addCategory(String title, String gToken, String token){
        return restClient.getUserCategoryAPI().addCategory(title, gToken, token);
    }

    public CategoryModel editCategory(String title, Integer id, String gToken, String token){
        return restClient.getUserCategoryAPI().editCategory(title, id, gToken, token);
    }

    public CategoryModel deleteCategory(Integer id, String gToken, String token){
        return restClient.getUserCategoryAPI().deleteCategory(id, gToken, token);
    }

    public GetCategoryModel getCategory(String gToken, String token){
        return restClient.getUserCategoryAPI().getAllCategories(gToken, token);
    }

    public GetCategoryTransactionModel getCT(Integer id, String gToken, String token){
        return restClient.getUserCategoryAPI().getCT(id, gToken, token);
    }

    public SynchCategoryModel synchCategory(List<Categories> categories, String token){
        return restClient.getUserCategoryAPI().synchCategory(categories, token);
    }

    //TransactionsQueries

    public AddTransactionModel addTransaction(String sum, String comment, Long category_id, String date, String gToken, String token){
        return  restClient.getUserTransactionAPI().addTransaction(sum, comment, category_id, date, gToken, token);
    }

    public GetTransactionModel getTransaction(String gToken, String token){
        return restClient.getUserTransactionAPI().getTransaction(gToken, token);
    }

    public GetTransactionByCategoriesModel getTransCat(String gToken, String token){
        return restClient.getUserTransactionAPI().getTransCat(gToken, token);
    }

    public SynchTransactionModel synchTransaction(List<Expenses> expenses,String token){
        return restClient.getUserTransactionAPI().synchTransaction(expenses, token);
    }

    //BalanceQueries

    public BalanceModel getBalance(String gToken, String token){
        return restClient.getUserBalanceAPI().getBalance(gToken, token);
    }

    public BalanceModel setBalance(int balance, String gToken, String token){
        return restClient.getUserBalanceAPI().setBalance(balance, gToken, token);
    }
}

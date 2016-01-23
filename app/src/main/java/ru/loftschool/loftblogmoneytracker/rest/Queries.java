package ru.loftschool.loftblogmoneytracker.rest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.activeandroid.query.Select;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ru.loftschool.loftblogmoneytracker.MoneyTrackerApp;
import ru.loftschool.loftblogmoneytracker.database.models.Categories;
import ru.loftschool.loftblogmoneytracker.database.models.Expenses;
import ru.loftschool.loftblogmoneytracker.rest.models.BalanceModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.CategoryDataModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.CategoryDeleteModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.CategoryModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.GetCategoryModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.GetCategoryTransactionDataModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.GetCategoryTransactionModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.CategoriesWrapper;
import ru.loftschool.loftblogmoneytracker.rest.models.login.UserLogoutModel;
import ru.loftschool.loftblogmoneytracker.rest.models.transaction.AddTransactionModel;
import ru.loftschool.loftblogmoneytracker.rest.models.transaction.GetTransactionModel;
import ru.loftschool.loftblogmoneytracker.rest.models.transaction.TransactionsWrapper;
import ru.loftschool.loftblogmoneytracker.rest.status.UserCategoriesStatus;
import ru.loftschool.loftblogmoneytracker.rest.status.UserTransactionStatus;
import ru.loftschool.loftblogmoneytracker.util.NotificationUtil;

/**
 * Created by Александр on 05.11.2015.
 */
@EBean
public class Queries {

    private Context context;
    private static final String LOG_TAG = "--Query information--\n";

    public Queries(Context context){
        this.context = context;
    }

    @Background
    public void editCategoryOnServer() {
        RestService restService = new RestService();
        CategoryModel editCategoryModel = restService.editCategory("Нечто", 1683,
                MoneyTrackerApp.getGoogleToken(context), MoneyTrackerApp.getToken(context));
        if (UserTransactionStatus.STATUS_SUCCESS.equalsIgnoreCase(editCategoryModel.getStatus())){
            Log.e(LOG_TAG, "Status: " + editCategoryModel.getStatus() +
                    ", Category: " + editCategoryModel.getData().getTitle() +
                    ", Id: " + editCategoryModel.getData().getId());
        } else {
            unknownError();
        }

    }

    @Background
    public void deleteCategoryOnServer() {
        RestService restService = new RestService();
        CategoryDeleteModel deleteCategoryModel = restService.deleteCategory(2129,
                MoneyTrackerApp.getGoogleToken(context), MoneyTrackerApp.getToken(context));
        if (UserTransactionStatus.STATUS_SUCCESS.equalsIgnoreCase(deleteCategoryModel.getStatus())){
            Log.e(LOG_TAG, "Status: " + deleteCategoryModel.getStatus() +
                    ", Data: " + deleteCategoryModel.getData());
        } else {
            unknownError();
        }

    }

    @Background
    public void getAllCategories() {
        RestService restService = new RestService();
        GetCategoryModel getAllCategories = restService.getCategory(MoneyTrackerApp.getGoogleToken(context), MoneyTrackerApp.getToken(context));
        if (UserCategoriesStatus.STATUS_SUCCESS.equalsIgnoreCase(getAllCategories.getStatus())){
            for (CategoryDataModel category : getAllCategories.getData()){
                Log.d(LOG_TAG, "Status: "+getAllCategories.getStatus()+
                        ", Category: "+category.getTitle()+
                        ", Id: "+category.getId());
            }
        } else {
            unknownError();
        }

    }

    @Background
    public void addToServerCategories(){
        RestService restService = new RestService();
        CategoryModel addCategoryResp;

        for(Categories categories : getCategories()){
            addCategoryResp = restService.addCategory(categories.category,
                    MoneyTrackerApp.getGoogleToken(context), MoneyTrackerApp.getToken(context));
            if (UserCategoriesStatus.STATUS_SUCCESS.equals(addCategoryResp.getStatus())){
                Log.d(LOG_TAG,
                        "Category: " + addCategoryResp.getData().getTitle() +
                                ", ID: " + addCategoryResp.getData().getId());
            } else {
                unknownError();
            }
        }
    }

    @Background
    public void addTransactionsToServer(){
        RestService restService = new RestService();
        AddTransactionModel addTransactionModel;

        if (!getExpenses().isEmpty()) {
            for (Expenses expenses : getExpenses()) {
                addTransactionModel = restService.addTransaction(expenses.sum, expenses.name, expenses.category.getId(), new SimpleDateFormat("yyyy/MM/dd").format(expenses.date),
                        MoneyTrackerApp.getGoogleToken(context), MoneyTrackerApp.getToken(context));
                if (UserTransactionStatus.STATUS_SUCCESS.equals(addTransactionModel.getStatus())) {
                    Log.d(LOG_TAG,"Status: "+addTransactionModel.getStatus()+
                            ", ID: "+addTransactionModel.getId());
                } else {
                    unknownError();
                }
            }
        }
    }

    @Background
    public void getAllTransaction() {
        RestService restService = new RestService();
        GetTransactionModel getAllTransactions = restService.getTransaction(MoneyTrackerApp.getGoogleToken(context), MoneyTrackerApp.getToken(context));
        if (UserTransactionStatus.STATUS_SUCCESS.equalsIgnoreCase(getAllTransactions.getStatus())){
            for (GetCategoryTransactionDataModel transaction : getAllTransactions.getData()){
                Log.d(LOG_TAG,
                        "Name: "+transaction.getComment()+
                                ", Id: "+transaction.getId()+
                                ", CatId: "+transaction.getCategoryId()+
                                ", Sum: "+transaction.getSum()+
                                ", Date: "+transaction.getTrDate());
            }
        } else {
            unknownError();
        }

    }

    @Background
    public void getCategoryInfo() {
        RestService restService = new RestService();
        ArrayList<GetCategoryTransactionModel> categoryResp = restService.getCT(1, MoneyTrackerApp.getGoogleToken(context), MoneyTrackerApp.getToken(context));
        Log.e(LOG_TAG, " * Category id: " + categoryResp.get(0).getId() +
                " * Category name: " + categoryResp.get(0).getTitle() +
                " * Transactions: ");
        for (GetCategoryTransactionDataModel expense : categoryResp.get(0).getTransactions()) {
            Log.e(LOG_TAG, "  **  Expense id: " + expense.getId() +
                    "  **  , Expense category id: " + expense.getCategoryId() +
                    "  **  , Expense comment: " + expense.getComment() +
                    "  **  , Expense summ: " + expense.getSum() +
                    "  **  , Expense date: " + expense.getTrDate());
        }
    }

    @Background
    public void getAllInfoFromCategories() {
        RestService restService = new RestService();
        ArrayList<GetCategoryTransactionModel> expensesResp = restService.getTransCat(MoneyTrackerApp.getGoogleToken(context), MoneyTrackerApp.getToken(context));
        for (GetCategoryTransactionModel transactionByCategorie : expensesResp) {
            Log.e(LOG_TAG, " | Category id: " + transactionByCategorie.getId() +
                    " | Category name: " + transactionByCategorie.getTitle() +
                    " | Transactions: ");
            for (GetCategoryTransactionDataModel expense : transactionByCategorie.getTransactions()) {
                Log.e(LOG_TAG, "  **  Expense id: " + expense.getId() +
                        "  ||  , Expense category id: " + expense.getCategoryId() +
                        "  ||  , Expense comment: " + expense.getComment() +
                        "  ||  , Expense summ: " + expense.getSum() +
                        "  ||  , Expense date: " + expense.getTrDate());
            }
        }
    }

    @SuppressLint("LongLogTag")
    private String createSynchCategoryQuery(){
        String syncCategories = "";
        List<Categories> categories = getCategories();
        List<CategoriesWrapper> data = new ArrayList<>();
        for (int i=0; i< categories.size(); i++) {
            CategoriesWrapper category = new CategoriesWrapper();
            category.setId(i);
            category.setTitle(categories.get(i).category);
            data.add(category);
        }
        for (int i=0; i<categories.size(); i++){
            syncCategories+=data.get(i);
            if(i<categories.size()-1) syncCategories+=",";
        }
        Log.d("Запрос на синхронизацию: ", syncCategories);
        return "["+syncCategories+"]";
    }

    public void synchCategories(){
        RestClient restClient = new RestClient();
        restClient.getUserCategoryAPI().synchCategory(createSynchCategoryQuery(),
                MoneyTrackerApp.getGoogleToken(context), MoneyTrackerApp.getToken(context),
                new Callback<GetCategoryModel>() {
                    @Override
                    public void success(GetCategoryModel synchCategoryModel, Response response) {
                        if (UserCategoriesStatus.STATUS_SUCCESS.equals(synchCategoryModel.getStatus())) {
                            NotificationUtil.updateNotification(context);
                            for (CategoryDataModel category : synchCategoryModel.getData()) {
                                Log.d(LOG_TAG, "Category: " + category.getTitle() + ", id: " + category.getId());
                            }
                        } else {
                            dataError();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        unknownError();
                    }
                });
    }

    @SuppressLint("LongLogTag")
    private String createSynchTransactionQuery(){
        String syncTransactions = "";
        List<Expenses> expenses = getExpenses();
        List<TransactionsWrapper> data = new ArrayList<>();
        for (Expenses expense : expenses) {
            TransactionsWrapper transaction = new TransactionsWrapper();
            transaction.setId(expense.getId());
            transaction.setCategoryId(expense.category.getId());
            transaction.setComment(expense.name);
            transaction.setSum(expense.sum);
            transaction.setDate(new SimpleDateFormat("yyyy/MM/dd").format(expense.date));
            data.add(transaction);
        }
        for (int i=0; i<expenses.size(); i++){
            syncTransactions+=data.get(i);
            if(i<expenses.size()-1) syncTransactions+=",";
        }
        Log.d("Запрос на синхронизацию: ", syncTransactions);
        return "["+syncTransactions+"]";
    }

    public void synchTransactions () {

        RestClient restClient = new RestClient();
        restClient.getUserTransactionAPI().synchTransaction(createSynchTransactionQuery(),
                MoneyTrackerApp.getGoogleToken(context), MoneyTrackerApp.getToken(context),
                new Callback<GetTransactionModel>() {
                    @Override
                    public void success(GetTransactionModel synchTransactionModel, Response response) {
                        if (UserTransactionStatus.STATUS_SUCCESS.equals(synchTransactionModel.getStatus())) {
                            NotificationUtil.updateNotification(context);
                            Log.d(LOG_TAG, "Status: " + synchTransactionModel.getStatus());
                            for (GetCategoryTransactionDataModel transaction : synchTransactionModel.getData()) {
                                Log.d(LOG_TAG,
                                        "Name: " + transaction.getComment() +
                                                ", Id: " + transaction.getId() +
                                                ", CatId: " + transaction.getCategoryId() +
                                                ", Sum: " + transaction.getSum() +
                                                ", Date: " + transaction.getTrDate());
                            }
                        } else {
                            dataError();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        unknownError();
                    }
                });

    }

    @Background
    public void getBalance() {
        RestService restService = new RestService();
        BalanceModel balanceResp = restService.getBalance(MoneyTrackerApp.getGoogleToken(context), MoneyTrackerApp.getToken(context));
        if ("success".equalsIgnoreCase(balanceResp.getStatus())) {
            Log.e(LOG_TAG, "Current balance: " + balanceResp.getBalance());
        } else {
            unknownError();
        }
    }

    @Background
    public void setBalance(String balance){
        RestService restService = new RestService();
        BalanceModel balanceResp = restService.setBalance(balance, MoneyTrackerApp.getGoogleToken(context), MoneyTrackerApp.getToken(context));
        if ("success".equalsIgnoreCase(balanceResp.getStatus())) {
            Log.e(LOG_TAG, "New balance: " + balanceResp.getBalance());
        } else {
            unknownError();
        }
    }

    @Background
    public void logout(){
        RestService restService = new RestService();
        UserLogoutModel logoutModel = restService.logout();
        if ("success".equalsIgnoreCase(logoutModel.getStatus())) {
            Log.e(LOG_TAG, "Вы успешно разлогинились");
        } else {
            unknownError();
        }
    }

    @UiThread
    void unknownError(){
        Toast.makeText(context, "Ошибка сервера", Toast.LENGTH_SHORT).show();
    }

    void dataError(){
        Toast.makeText(context, "Не удалось синхронизировать данные", Toast.LENGTH_SHORT).show();
    }

    private List<Expenses> getExpenses(){
        return new Select().from(Expenses.class).execute();
    }

    private List<Categories> getCategories(){
        return new Select().from(Categories.class).execute();
    }
}

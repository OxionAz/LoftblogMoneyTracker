package ru.loftschool.loftblogmoneytracker.ui.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.activeandroid.query.Select;
import com.squareup.picasso.Picasso;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import java.util.List;
import ru.loftschool.loftblogmoneytracker.MoneyTrackerApp;
import ru.loftschool.loftblogmoneytracker.database.models.Expenses;
import ru.loftschool.loftblogmoneytracker.rest.RestClient;
import ru.loftschool.loftblogmoneytracker.rest.RestService;
import ru.loftschool.loftblogmoneytracker.rest.models.category.CategoryModel;
import ru.loftschool.loftblogmoneytracker.rest.models.GoogleTokenStatusModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.GetCategoryDataModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.GetCategoryModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.SynchCategoryDataModel;
import ru.loftschool.loftblogmoneytracker.rest.models.category.SynchCategoryModel;
import ru.loftschool.loftblogmoneytracker.rest.models.transaction.AddTransactionModel;
import ru.loftschool.loftblogmoneytracker.rest.models.transaction.SynchTransactionDataModel;
import ru.loftschool.loftblogmoneytracker.rest.models.transaction.SynchTransactionModel;
import ru.loftschool.loftblogmoneytracker.rest.status.UserCategoriesStatus;
import ru.loftschool.loftblogmoneytracker.rest.status.UserTransactionStatus;
import ru.loftschool.loftblogmoneytracker.ui.fragments.CategoriesFragment_;
import ru.loftschool.loftblogmoneytracker.ui.fragments.ExpensesFragment_;
import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.ui.fragments.SettingsFragment_;
import ru.loftschool.loftblogmoneytracker.ui.fragments.StatisticsFragment_;
import ru.loftschool.loftblogmoneytracker.database.models.Categories;
import ru.loftschool.loftblogmoneytracker.util.NetworkConnectionUtil;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    @ViewById
    Toolbar toolbar;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @ViewById(R.id.navigation_view)
    NavigationView navView;

    @ViewById
    ImageView avatar;

    @ViewById
    TextView user_name, user_email;

    @OptionsItem(android.R.id.home)
    void drawer(){
        if (drawerLayout.isDrawerOpen(navView)){
            drawerLayout.closeDrawers();
        }else drawerLayout.openDrawer(navView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ExpensesFragment_()).commit();
        }
    }

    @AfterViews
    void ready(){
        initToolbar();
        initNavigationDrawer();
        setCategories();

        if (!MoneyTrackerApp.getGoogleToken(this).equals("1")
                && NetworkConnectionUtil.isNetworkConnected(this)) getUserInfo();
        if (NetworkConnectionUtil.isNetworkConnected(this)) postCategories();
        if (NetworkConnectionUtil.isNetworkConnected(this)) postTransactions();
        if (!MoneyTrackerApp.getToken(this).equals("1")
                && NetworkConnectionUtil.isNetworkConnected(this)) synchCategories();
        if (!MoneyTrackerApp.getToken(this).equals("1")
                && NetworkConnectionUtil.isNetworkConnected(this)) synchTransactions();
    }

    private void initToolbar(){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initNavigationDrawer(){
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                selectItem(menuItem);
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    @UiThread
    void setUserInfo(GoogleTokenStatusModel userInfo){
        Picasso.with(this).load(userInfo.getPicture()).into(avatar);
        user_name.setText(userInfo.getName());
        user_email.setText(userInfo.getEmail());
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(navView)){
            drawerLayout.closeDrawer(navView);
        }
        else{
            super.onBackPressed();
        }
    }

    public void setCategories(){
        if (getCategories().isEmpty()){
            new Categories("Food").save();
            new Categories("Stuff").save();
            new Categories("Clothes").save();
            new Categories("Fun").save();
            new Categories("Other").save();
        }
    }

    @Background
    void editCategoryOnServer() {
        RestService restService = new RestService();
        CategoryModel editCategoryModel = restService.editCategory("Нечто", 2,
                MoneyTrackerApp.getGoogleToken(this), MoneyTrackerApp.getToken(this));
        if (UserTransactionStatus.STATUS_SUCCESS.equalsIgnoreCase(editCategoryModel.getStatus())){
            Log.e(LOG_TAG, "Status: "+editCategoryModel.getStatus()+
                           ", Category: "+editCategoryModel.getCategoryDataModel().getTitle()+
                           ", Id: "+editCategoryModel.getCategoryDataModel().getId());
        } else {
            unknownError();
        }

    }

    @Background
    void deleteCategoryOnServer() {
        RestService restService = new RestService();
        CategoryModel deleteCategoryModel = restService.deleteCategory(2,
                MoneyTrackerApp.getGoogleToken(this), MoneyTrackerApp.getToken(this));
        if (UserTransactionStatus.STATUS_SUCCESS.equalsIgnoreCase(deleteCategoryModel.getStatus())){
            Log.e(LOG_TAG, "Status: "+deleteCategoryModel.getStatus()+
                    ", Category: "+deleteCategoryModel.getCategoryDataModel().getTitle()+
                    ", Id: "+deleteCategoryModel.getCategoryDataModel().getId());
        } else {
            unknownError();
        }

    }

    @Background
    void getAllCategories() {
        RestService restService = new RestService();
        GetCategoryModel getAllCategories = restService.getCategory(MoneyTrackerApp.getGoogleToken(this), MoneyTrackerApp.getToken(this));
        List<GetCategoryDataModel> data = getAllCategories.getCategories();
        if (UserTransactionStatus.STATUS_SUCCESS.equalsIgnoreCase(getAllCategories.getStatus())){
            for (int i=0; i < data.size(); i++){
                Log.d(LOG_TAG, "Status: "+getAllCategories.getStatus()+
                               "Category: "+data.get(i).getTitle()+
                               ", Id: "+data.get(i).getId());
            }
        } else {
            unknownError();
        }

    }

    @Background
    public void postCategories(){
        RestService restService = new RestService();
        CategoryModel addCategoryResp;

        for(Categories categories : getCategories()){
            addCategoryResp = restService.addCategory(categories.category,
                    MoneyTrackerApp.getGoogleToken(this), MoneyTrackerApp.getToken(this));
            if (UserCategoriesStatus.STATUS_SUCCESS.equals(addCategoryResp.getStatus())){
                Log.d(LOG_TAG,
                        "Category: " + addCategoryResp.getCategoryDataModel().getTitle() +
                        ", ID: " + addCategoryResp.getCategoryDataModel().getId());
            } else {
                unknownError();
            }
        }
    }

    @Background
    public void postTransactions(){
        RestService restService = new RestService();
        AddTransactionModel addTransactionModel;

        if (!getExpenses().isEmpty()) {
            for (Expenses expenses : getExpenses()) {
                addTransactionModel = restService.addTransaction(expenses.sum, expenses.name, expenses.category.getId(), expenses.date,
                        MoneyTrackerApp.getGoogleToken(this), MoneyTrackerApp.getToken(this));
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
    public void synchCategories(){
        RestService restService = new RestService();
        SynchCategoryModel synchCategoryModel = restService.synchCategory(getCategories(),
                MoneyTrackerApp.getToken(this));
        List<SynchCategoryDataModel> data = synchCategoryModel.getData();
        Log.d(LOG_TAG, "Status: "+synchCategoryModel.getStatus());
        if (UserCategoriesStatus.STATUS_SUCCESS.equalsIgnoreCase(synchCategoryModel.getStatus())){
        for (int i=0; i < data.size(); i++){
            Log.d(LOG_TAG, "Category: "+data.get(i).getTitle()+", id: "+data.get(i).getId());
        }
        } else {
            unknownError();
        }
    }

    @Background
    public void synchTransactions(){
        RestService restService = new RestService();
        SynchTransactionModel synchTransactionModel = restService.synchTransaction(getExpenses(),
                MoneyTrackerApp.getToken(this));
        List<SynchTransactionDataModel> data = synchTransactionModel.getData();
        Log.d(LOG_TAG, "Status: "+synchTransactionModel.getStatus());
        if (UserTransactionStatus.STATUS_SUCCESS.equalsIgnoreCase(synchTransactionModel.getStatus())){
            for (int i=0; i < data.size(); i++){
                Log.d(LOG_TAG,
                        "Name: "+data.get(i).getComment()+
                        ", Id: "+data.get(i).getId()+
                        ", CatId: "+data.get(i).getCategoryId()+
                        ", Sum: "+data.get(i).getSum()+
                        ", Date: "+data.get(i).getTrDate());
            }
        } else {
            unknownError();
        }
    }

    void unknownError(){
        Log.e(LOG_TAG, "Ошибка сервера");
    }

    @Background
    void getUserInfo(){
        RestClient restClient = new RestClient();
        GoogleTokenStatusModel userInfo = restClient.getCheckGoogleTokenAPI().googleJson(MoneyTrackerApp.getGoogleToken(this));
        Log.d(LOG_TAG, "Status: " + userInfo.getStatus() + ", User name: " + userInfo.getName() + ", email: " + userInfo.getEmail());

        if (userInfo.getStatus() == null) {
            setUserInfo(userInfo);
        } else {
            unknownError();
        }
    }

    private void selectItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.drawer_expenses:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ExpensesFragment_()).addToBackStack(null).commit();
                break;
            case R.id.drawer_categories:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new CategoriesFragment_()).addToBackStack(null).commit();
                break;
            case R.id.drawer_statistics:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new StatisticsFragment_()).addToBackStack(null).commit();
                break;
            case R.id.drawer_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new SettingsFragment_()).addToBackStack(null).commit();
                break;
        }
    }

    @UiThread
    void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity_.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Background
    public void logout(MenuItem item){
        MoneyTrackerApp.setToken(this, MoneyTrackerApp.DEFAULT_TOKEN_KEY);
        MoneyTrackerApp.setGoogleToken(this, MoneyTrackerApp.DEFAULT_TOKEN_KEY);
        startLoginActivity();
    }

    private List<Expenses> getExpenses(){
        return new Select().from(Expenses.class).execute();
    }

    private List<Categories> getCategories(){
        return new Select().from(Categories.class).execute();
    }
}

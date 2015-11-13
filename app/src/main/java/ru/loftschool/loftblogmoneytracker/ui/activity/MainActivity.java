package ru.loftschool.loftblogmoneytracker.ui.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import ru.loftschool.loftblogmoneytracker.MoneyTrackerApp;
import ru.loftschool.loftblogmoneytracker.rest.Queries;
import ru.loftschool.loftblogmoneytracker.rest.RestClient;
import ru.loftschool.loftblogmoneytracker.rest.models.GoogleTokenStatusModel;
import ru.loftschool.loftblogmoneytracker.ui.fragments.CategoriesFragment_;
import ru.loftschool.loftblogmoneytracker.ui.fragments.ExpensesFragment_;
import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.ui.fragments.SettingsFragment;
import ru.loftschool.loftblogmoneytracker.ui.fragments.StatisticsFragment_;
import ru.loftschool.loftblogmoneytracker.util.AddDefaultCategories;
import ru.loftschool.loftblogmoneytracker.util.NetworkConnectionUtil;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    @Bean
    Queries queries = new Queries(this);

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
        AddDefaultCategories.setCategories();

        if (NetworkConnectionUtil.isNetworkConnected(this)) {
            if (!MoneyTrackerApp.getGoogleToken(this).equals("1")) getUserInfo(); //Work!
//            queries.editCategoryOnServer(); //Work!
//            queries.addToServerCategories(); //Work!
//            queries.addTransactionsToServer(); //Work!
//            queries.deleteCategoryOnServer(); //Work!
//            queries.getAllTransaction(); //Work!
//            queries.getAllCategories(); //Work!
//            queries.getAllInfoFromCategories(); //Work!
//            queries.getCategoryInfo(); //Work!
//            queries.getBalance(); //Work!)
//            queries.setBalance("3200"); //Work!
        }
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

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(navView)){
            drawerLayout.closeDrawer(navView);
        }
        else{
            super.onBackPressed();
        }
    }

    @Background
    void getUserInfo(){
        RestClient restClient = new RestClient();
        GoogleTokenStatusModel userInfo = restClient.getCheckGoogleTokenAPI().googleJson(MoneyTrackerApp.getGoogleToken(this));
        Log.d(LOG_TAG, "Status: " + userInfo.getStatus() + ", User name: " + userInfo.getName() + ", email: " + userInfo.getEmail());

        if (userInfo.getStatus() == null) {
            setUserInfo(userInfo);
        } else {
            Log.e(LOG_TAG, "Ошибка сервера");
        }
    }

    @UiThread
    public void setUserInfo(GoogleTokenStatusModel userInfo){
        Picasso.with(this).load(userInfo.getPicture()).into(avatar);
        user_name.setText(userInfo.getName());
        user_email.setText(userInfo.getEmail());
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
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new SettingsFragment()).addToBackStack(null).commit();
                break;
            case R.id.drawer_exit:
                logout();
                break;
        }
    }

    void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity_.class);
        startActivity(intent);
        finish();
    }

    public void logout(){
        queries.logout();
        MoneyTrackerApp.setToken(this, MoneyTrackerApp.DEFAULT_TOKEN_KEY);
        MoneyTrackerApp.setGoogleToken(this, MoneyTrackerApp.DEFAULT_TOKEN_KEY);
        startLoginActivity();
        finish();
    }
}

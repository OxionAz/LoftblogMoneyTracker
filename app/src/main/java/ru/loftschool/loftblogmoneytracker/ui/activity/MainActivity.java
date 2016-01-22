package ru.loftschool.loftblogmoneytracker.ui.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import ru.loftschool.loftblogmoneytracker.ui.fragments.CategoriesFragment;
import ru.loftschool.loftblogmoneytracker.ui.fragments.CategoriesFragment_;
import ru.loftschool.loftblogmoneytracker.ui.fragments.ExpensesFragment;
import ru.loftschool.loftblogmoneytracker.ui.fragments.ExpensesFragment_;
import ru.loftschool.loftblogmoneytracker.R;
import ru.loftschool.loftblogmoneytracker.ui.fragments.StatisticsFragment;
import ru.loftschool.loftblogmoneytracker.ui.fragments.StatisticsFragment_;
import ru.loftschool.loftblogmoneytracker.util.AddDefaultCategories;
import ru.loftschool.loftblogmoneytracker.util.NetworkConnectionUtil;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    private int defaultItemId = R.id.drawer_expenses;

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
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                super.onBackPressed();
            } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                super.onBackPressed();
                navView.setCheckedItem(R.id.drawer_expenses);
                this.defaultItemId = R.id.drawer_expenses;
            } else {
                super.onBackPressed();
                getLastFragmentChecked();
            }
        }
    }

    private void getLastFragmentChecked() {
        FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1);
        String str = backEntry.getName();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(str);
        updateSelectedItem(fragment);
    }

    private void updateSelectedItem(Fragment fragment) {
        if (fragment instanceof ExpensesFragment) {
            navView.setCheckedItem(R.id.drawer_expenses);
            this.defaultItemId = R.id.drawer_expenses;
        } else if (fragment instanceof CategoriesFragment) {
            navView.setCheckedItem(R.id.drawer_categories);
            this.defaultItemId = R.id.drawer_categories;
        } else if (fragment instanceof StatisticsFragment) {
            navView.setCheckedItem(R.id.drawer_statistics);
            this.defaultItemId = R.id.drawer_statistics;
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
                checkBackstack(new ExpensesFragment_(), menuItem.getItemId());
                break;
            case R.id.drawer_categories:
                checkBackstack(new CategoriesFragment_(), menuItem.getItemId());
                break;
            case R.id.drawer_statistics:
                checkBackstack(new StatisticsFragment_(), menuItem.getItemId());
                break;
            case R.id.drawer_settings:
                startActivity(new Intent(this, SettingsActivity_.class));
                break;
            case R.id.drawer_exit:
                logout();
                break;
        }
    }

    private void checkBackstack(Fragment fragment, int itemId){
        if (itemId != defaultItemId){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null).commit();
            this.defaultItemId = itemId;
        }
    }

    void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity_.class);
        startActivity(intent);
        finish();
    }

    public void logout(){
        if (NetworkConnectionUtil.isNetworkConnected(this)) {
            queries.logout();
        }
        MoneyTrackerApp.setToken(this, MoneyTrackerApp.DEFAULT_TOKEN_KEY);
        MoneyTrackerApp.setGoogleToken(this, MoneyTrackerApp.DEFAULT_TOKEN_KEY);
        startLoginActivity();
        finish();
    }
}

package ru.loftschool.moneytrackerbyoxion.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import org.androidannotations.annotations.res.StringRes;
import ru.loftschool.moneytrackerbyoxion.MoneyTrackerApp;
import ru.loftschool.moneytrackerbyoxion.rest.Queries;
import ru.loftschool.moneytrackerbyoxion.rest.RestClient;
import ru.loftschool.moneytrackerbyoxion.rest.models.GoogleTokenStatusModel;
import ru.loftschool.moneytrackerbyoxion.ui.fragments.CategoriesFragment;
import ru.loftschool.moneytrackerbyoxion.ui.fragments.CategoriesFragment_;
import ru.loftschool.moneytrackerbyoxion.ui.fragments.ExpensesFragment;
import ru.loftschool.moneytrackerbyoxion.ui.fragments.ExpensesFragment_;
import ru.loftschool.moneytrackerbyoxion.R;
import ru.loftschool.moneytrackerbyoxion.ui.fragments.StatisticsFragment_;
import ru.loftschool.moneytrackerbyoxion.util.NetworkConnectionUtil;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";
    private static int defaultItemId = R.id.drawer_expenses;
    private static boolean exit = false;

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

    @StringRes
    String no_internet_connection;

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
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ExpensesFragment_())
                    .commit();
        }
    }

    @AfterViews
    void ready(){
        initToolbar();
        initNavigationDrawer();
//        if (NetworkConnectionUtil.isNetworkConnected(this)) {
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
//        }
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
        if (!MoneyTrackerApp.getToken(this).equals("1")) setUserInfo();
        if (NetworkConnectionUtil.isNetworkConnected(this)) {
            if (!MoneyTrackerApp.getGoogleToken(this).equals("1")) getUserInfo();
        }
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                selectItem(menuItem);
                drawerLayout.closeDrawers();
                return false;
            }
        });
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                hideKeyboard();
                if (ExpensesFragment.getActionMode() != null)
                    ExpensesFragment.finishActionMode();
                if (CategoriesFragment.getActionMode() != null)
                    CategoriesFragment.finishActionMode();
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(navView)) {
            drawerLayout.closeDrawer(navView);
        } else if (R.id.drawer_expenses != defaultItemId) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new ExpensesFragment_()).commit();
            navView.setCheckedItem(R.id.drawer_expenses);
            defaultItemId = R.id.drawer_expenses;
        } else {
            if (exit) super.onBackPressed();
            exit = true;
            Snackbar.make(drawerLayout, "Нажмите еще раз для выхода", Snackbar.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 2000);
        }
    }

    @Background
    void getUserInfo(){
        RestClient restClient = new RestClient();
        GoogleTokenStatusModel userInfo = restClient.getCheckGoogleTokenAPI().googleJson(MoneyTrackerApp.getGoogleToken(this));
        Log.d(LOG_TAG, "Status: " + userInfo.getStatus() + ", User name: " + userInfo.getName() + ", email: " + userInfo.getEmail());

        if (userInfo.getStatus() == null) {
            setUserInfoGoogle(userInfo);
        } else {
            Log.e(LOG_TAG, "Ошибка сервера");
        }
    }

    @UiThread
    public void setUserInfoGoogle(GoogleTokenStatusModel userInfo){
        Picasso.with(this).load(userInfo.getPicture()).into(avatar);
        user_name.setText(userInfo.getName());
        user_email.setText(userInfo.getEmail());
        user_email.setVisibility(View.VISIBLE);
    }

    private void setUserInfo(){
        user_name.setText(MoneyTrackerApp.getUserName(this));
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
            case R.id.drawer_item_sync:
                if (NetworkConnectionUtil.isNetworkConnected(this)) {
                    queries.synchCategories();
                    queries.synchTransactions();
                } else {
                    Snackbar.make(drawerLayout, no_internet_connection, Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.drawer_exit:
                logout();
                break;
        }
    }

    private void checkBackstack(Fragment fragment, int itemId){
        if (itemId != defaultItemId){
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
            defaultItemId = itemId;
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
        MoneyTrackerApp.setUserName(this, MoneyTrackerApp.DEFAULT_TOKEN_KEY);
        startLoginActivity();
        finish();
    }
}

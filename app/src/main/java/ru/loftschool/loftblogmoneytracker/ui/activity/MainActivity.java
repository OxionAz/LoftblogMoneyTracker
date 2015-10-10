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
import ru.loftschool.loftblogmoneytracker.rest.RestClient;
import ru.loftschool.loftblogmoneytracker.rest.RestService;
import ru.loftschool.loftblogmoneytracker.rest.models.AddCategoryModel;
import ru.loftschool.loftblogmoneytracker.rest.models.GoogleTokenStatusModel;
import ru.loftschool.loftblogmoneytracker.rest.models.SynchCategoryDataModel;
import ru.loftschool.loftblogmoneytracker.rest.models.SynchCategoryModel;
import ru.loftschool.loftblogmoneytracker.rest.status.UserCategoriesStatus;
import ru.loftschool.loftblogmoneytracker.sync.TrackerSyncAdapter;
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

        if (NetworkConnectionUtil.isNetworkConnected(this))postCategories();

        if (!MoneyTrackerApp.getToken(this).equals("1")
                && NetworkConnectionUtil.isNetworkConnected(this)) synchCategories();
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
    public void postCategories(){
        RestService restService = new RestService();
        AddCategoryModel addCategoryResp = null;

        for(Categories categories : getCategories()){
            addCategoryResp = restService.addCategory(categories.category,
                    MoneyTrackerApp.getGoogleToken(this), MoneyTrackerApp.getToken(this));
            if (UserCategoriesStatus.STATUS_SUCCESS.equals(addCategoryResp.getStatus())){
                Log.d(LOG_TAG,
                        "Category name: " + addCategoryResp.getAddCategoryDataModel().getTitle() +
                        ", ID: " + addCategoryResp.getAddCategoryDataModel().getId());
            } else {
                unknownError();
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
            Log.d(LOG_TAG, "Category: "+data.get(i).getTitle());
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

    private List<Categories> getCategories(){
        return new Select().from(Categories.class).execute();
    }
}

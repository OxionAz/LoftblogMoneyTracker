package ru.loftschool.loftblogmoneytracker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    private ListView listView;
    private List<Transaction> data = new ArrayList<>();
    private TransactionAdapter transactionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "onCreate method called!");
        listView = (ListView)findViewById(R.id.main_listview);
        List<Transaction> adapterData = getDataList();
        transactionAdapter = new TransactionAdapter(this, adapterData);
        listView.setAdapter(transactionAdapter);
    }

    private List<Transaction> getDataList(){
        Date d = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        data.add(new Transaction("Telephone",2000,format1.format(d)));
        data.add(new Transaction("Telephone",3000,format1.format(d)));
        data.add(new Transaction("Telephone",4000,format1.format(d)));
        data.add(new Transaction("Telephone",5000,format1.format(d)));
        data.add(new Transaction("Telephone",2000,format1.format(d)));
        data.add(new Transaction("Telephone",3000,format1.format(d)));
        data.add(new Transaction("Telephone",4000,format1.format(d)));
        data.add(new Transaction("Telephone",5000,format1.format(d)));
        data.add(new Transaction("Telephone",2000,format1.format(d)));
        data.add(new Transaction("Telephone",3000,format1.format(d)));
        data.add(new Transaction("Telephone",4000,format1.format(d)));
        data.add(new Transaction("Telephone",5000,format1.format(d)));
        return data;
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(LOG_TAG, "onResume method called!");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy method called!");
    }

}

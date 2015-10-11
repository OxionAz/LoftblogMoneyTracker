package ru.loftschool.loftblogmoneytracker.rest.api;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import ru.loftschool.loftblogmoneytracker.database.models.Expenses;
import ru.loftschool.loftblogmoneytracker.rest.models.transaction.AddTransactionModel;
import ru.loftschool.loftblogmoneytracker.rest.models.transaction.GetTransactionByCategoriesModel;
import ru.loftschool.loftblogmoneytracker.rest.models.transaction.GetTransactionModel;
import ru.loftschool.loftblogmoneytracker.rest.models.transaction.SynchTransactionModel;

/**
 * Created by Александр on 10.10.2015.
 */
public interface UserTransactionAPI {

    @GET("/transactions")
    GetTransactionModel getTransaction (@Query("google_token") String gToken,
                                        @Query("auth_token") String token);

    @GET("/transactions/add")
    AddTransactionModel addTransaction (@Query("sum") String sum,
                                        @Query("comment") String comment,
                                        @Query("category_id") Long category_id,
                                        @Query("tr_date") String date,
                                        @Query("google_token") String gToken,
                                        @Query("auth_token") String token);

    @GET("/transactions/synch")
    SynchTransactionModel synchTransaction(@Query("data") List<Expenses> expenses,
                                           @Query("auth_token") String token);

    @GET("/transcat")
    GetTransactionByCategoriesModel getTransCat(@Query("google_token") String gToken,
                                                @Query("auth_token") String token);
}

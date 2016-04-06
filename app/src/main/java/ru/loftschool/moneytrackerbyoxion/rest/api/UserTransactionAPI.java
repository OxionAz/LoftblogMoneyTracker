package ru.loftschool.moneytrackerbyoxion.rest.api;

import java.util.ArrayList;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;
import ru.loftschool.moneytrackerbyoxion.rest.models.category.GetCategoryTransactionModel;
import ru.loftschool.moneytrackerbyoxion.rest.models.transaction.AddTransactionModel;
import ru.loftschool.moneytrackerbyoxion.rest.models.transaction.GetTransactionModel;

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
    void synchTransaction(@Query("data") String expenses,
                          @Query("google_token") String gToken,
                          @Query("auth_token") String token,
                          Callback<GetTransactionModel> synch);

    @GET("/transcat")
    ArrayList<GetCategoryTransactionModel> getTransCat(@Query("google_token") String gToken,
                                                       @Query("auth_token") String token);
}

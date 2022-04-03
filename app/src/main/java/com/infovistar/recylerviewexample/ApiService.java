package com.infovistar.recylerviewexample;


import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("transaction/list")
    Call<TransactionModel> getTransactionList(@FieldMap HashMap<String, Object> hashMap);

}

package com.hassan.lalamove.api;

import com.hassan.lalamove.models.Delivery;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetofitInterface {

    @GET("deliveries")
    Call<ArrayList<Delivery>> getDeliveries(@Query("offset") int offset, @Query("limit") int limit);


}

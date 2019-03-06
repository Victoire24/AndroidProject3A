package com.example.myproject;

import com.example.myproject.model.Drink;
import com.example.myproject.model.RestDrinkAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GerritAPI {

    @GET("search.php")
    Call<RestDrinkAPI> loadChanges();
}

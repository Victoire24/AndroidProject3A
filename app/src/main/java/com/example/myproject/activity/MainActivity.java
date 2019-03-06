package com.example.myproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.myproject.GerritAPI;
import com.example.myproject.MyAdapter;
import com.example.myproject.R;
import com.example.myproject.model.Drink;
import com.example.myproject.model.RestDrinkAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        start();
    }


    static final String BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/";

    public void start() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GerritAPI gerritAPI = retrofit.create(GerritAPI.class);

        Call<RestDrinkAPI> call = gerritAPI.loadChanges();
        call.enqueue(new Callback<RestDrinkAPI>() {
            @Override
            public void onResponse(Call<RestDrinkAPI> call, Response<RestDrinkAPI> response) {
                RestDrinkAPI drink = response.body();
                List<Drink> listDrink = drink.getDrinks();
                showList(listDrink);

            }

            @Override
            public void onFailure(Call<RestDrinkAPI> call, Throwable t) {
                Log.d("ERROR", "Api Error");

            }
        });

    }

    private void showList(List<Drink> listDrink) {
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new MyAdapter(listDrink);
        recyclerView.setAdapter(mAdapter);
    }
}


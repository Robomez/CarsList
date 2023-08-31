package com.example.carslist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static CarsDatabase database;
    Button btnSortPrice, btnAddCar;
    Spinner filterSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(
                getApplicationContext(),
                CarsDatabase.class,
                "CarsDB.db")
                .createFromAsset("CarsList.db")
                .allowMainThreadQueries()
                .build();

        btnSortPrice = findViewById(R.id.button_sort_price);
        btnSortPrice.setOnClickListener(this);
        btnAddCar = findViewById(R.id.button_add_car);
        btnAddCar.setOnClickListener(this);
        filterSpinner = findViewById(R.id.filter_spinner);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.reload();
    }


    @Override
    public void onClick(View view) {
        if (view.equals(btnAddCar)) {
            Intent intent = new Intent(MainActivity.this, AddCarActivity.class);
            startActivity(intent);
        } else if (view.equals(btnSortPrice)) {

        } else {
            throw new IllegalStateException("Unexpected value: " + view);
        }
    }
}
package com.example.carslist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

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

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        btnSortPrice = findViewById(R.id.button_sort_price);
        btnAddCar = findViewById(R.id.button_add_car);
        filterSpinner = findViewById(R.id.filter_spinner);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.reload();
    }
}
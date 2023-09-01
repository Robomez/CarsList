package com.example.carslist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static CarsDatabase database;
    Button btnSortPrice, btnAddCar;
    Spinner filterSpinner;
    RecyclerViewAdapter adapter;

    static String ORDER_ASC = "1", ORDER_DESC = "2", ORDER_UNORDERED = "0";
    private String listOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listOrder = ORDER_UNORDERED;

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
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(spinnerAdapter);
        spinnerAdapter.add("Все марки");
        spinnerAdapter.addAll(database.carDao().getCarBrands());
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSpinnerItem = filterSpinner.getSelectedItem().toString();
                if (selectedSpinnerItem.equals("Все марки")) {
                    adapter.reload();
                } else {
                    adapter.showFilteredCars(selectedSpinnerItem);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerViewAdapter();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.reload();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.reload();
    }

    @Override
    public void onClick(View view) {
        if (view.equals(btnAddCar)) {
            Intent intent = new Intent(MainActivity.this, CarAddActivity.class);
            startActivity(intent);
        } else if (view.equals(btnSortPrice)) {
            sortPrice();
        } else {
            throw new IllegalStateException("Unexpected value: " + view);
        }
    }

    private void sortPrice() {
        adapter.sortByPrice(listOrder);
        if (Objects.equals(listOrder, ORDER_ASC)) {
            listOrder = ORDER_DESC;
            btnSortPrice.setText("Сорт по цене ⇑");
        } else {
            listOrder = ORDER_ASC;
            btnSortPrice.setText("Сорт по цене ⇓");
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}


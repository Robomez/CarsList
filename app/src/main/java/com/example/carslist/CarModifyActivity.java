package com.example.carslist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CarModifyActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextBrand, editTextModel, editTextColor, editTextPrice;
    private Button btnModified, btnSearch;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_car);

        editTextBrand = findViewById(R.id.editText_modify_brand);
        String modifyBrand = getIntent().getStringExtra("brand");
        editTextBrand.setText(modifyBrand);

        editTextModel = findViewById(R.id.editText_modify_model);
        String modifyModel = getIntent().getStringExtra("model");
        editTextModel.setText(modifyModel);

        editTextColor = findViewById(R.id.editText_modify_color);
        String modifyColor = getIntent().getStringExtra("color");
        editTextColor.setText(modifyColor);

        editTextPrice = findViewById(R.id.editText_modify_price);
        String modifyPrice = String.valueOf(getIntent().getIntExtra("price", 0));
        editTextPrice.setText(modifyPrice);

        id = getIntent().getIntExtra("id", 0);

        btnModified = findViewById(R.id.button_modified);
        btnModified.setOnClickListener(this);

        btnSearch = findViewById(R.id.button_search);
        btnSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnModified)) {
            // change values for that car
            MainActivity.database.carDao().modify(
                    editTextBrand.getText().toString(),
                    editTextModel.getText().toString(),
                    editTextColor.getText().toString(),
                    Integer.parseInt(editTextPrice.getText().toString()),
                    id
            );
            Intent intent = new Intent(CarModifyActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (v.equals(btnSearch)) {

            if (ContextCompat.checkSelfPermission(
                    CarModifyActivity.this,
                    Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                        CarModifyActivity.this,
                        new String[]{"android.permission.INTERNET"},
                        200);
            }

            String url = "https://www.google.com/search?q=каталог+запчастей+"+editTextBrand.getText().toString();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        }
    }
}
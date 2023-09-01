package com.example.carslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CarAddActivity extends AppCompatActivity {
    EditText editTextBrand, editTextModel, editTextColor, editTextPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        Button btnAdded = findViewById(R.id.button_added);
        btnAdded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.database.carDao().create(
                        editTextBrand.getText().toString(),
                        editTextModel.getText().toString(),
                        editTextColor.getText().toString(),
                        Integer.parseInt(editTextPrice.getText().toString())
                );
                Intent intent = new Intent(CarAddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        editTextBrand = findViewById(R.id.editText_modify_brand);
        editTextModel  = findViewById(R.id.editText_modify_model);
        editTextColor = findViewById(R.id.editText_modify_color);
        editTextPrice = findViewById(R.id.editText_modify_price);
    }
}
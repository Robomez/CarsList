package com.example.carslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CarAddActivity extends AppCompatActivity {
    EditText editTextBrand, editTextModel, editTextColor, editTextPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        editTextBrand = findViewById(R.id.editText_add_brand);
        editTextModel = findViewById(R.id.editText_add_model);
        editTextColor = findViewById(R.id.editText_add_color);
        editTextPrice = findViewById(R.id.editText_add_price);


        Button btnAdded = findViewById(R.id.button_added);
        btnAdded.setOnClickListener(view -> {

            if (editTextBrand.getText().toString().length() == 0 ||
                    editTextModel.getText().toString().length() == 0 ||
                    editTextPrice.getText().toString().length() == 0) {
                Toast.makeText(this, "Нужно заполнить все поля", Toast.LENGTH_SHORT).show();
            } else {
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
    }
}


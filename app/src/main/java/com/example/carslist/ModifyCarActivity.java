package com.example.carslist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ModifyCarActivity extends AppCompatActivity {

    private EditText editTextBrand, editTextModel, editTextColor, editTextPrice;
    private Button btnModified;
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
        btnModified.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // change values for that car
                MainActivity.database.carDao().modify(
                        editTextBrand.getText().toString(),
                        editTextModel.getText().toString(),
                        editTextColor.getText().toString(),
                        Integer.parseInt(editTextPrice.getText().toString()),
                        id
                );
                Intent intent = new Intent(ModifyCarActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
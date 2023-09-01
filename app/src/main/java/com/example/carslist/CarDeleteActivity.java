package com.example.carslist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class CarDeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_car);

        int id = getIntent().getIntExtra("id", 0);

        Button btnAdded = findViewById(R.id.button_deleted);
        btnAdded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.database.carDao().delete(id);
                Intent intent = new Intent(CarDeleteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
package com.example.carslist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.mlkit.common.MlKitException;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

public class CarModifyActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextBrand, editTextModel, editTextColor, editTextPrice;
    private TextView textViewQrcode;
    private Button btnModified, btnSearch, btnQrcode;
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

        textViewQrcode = findViewById(R.id.textView_qrcode);
        String modifyQrcode = getIntent().getStringExtra("qrcode");
        if (modifyQrcode == null) {
            textViewQrcode.setText("Qr код будет тут");
        } else {
            textViewQrcode.setText(modifyQrcode);
        }

        btnModified = findViewById(R.id.button_modified);
        btnModified.setOnClickListener(this);

        btnSearch = findViewById(R.id.button_search);
        btnSearch.setOnClickListener(this);

        btnQrcode = findViewById(R.id.button_qrcode);
        btnQrcode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btnModified)) {
            if (editTextBrand.getText().toString().length() == 0 ||
                    editTextModel.getText().toString().length() == 0 ||
                    editTextPrice.getText().toString().length() == 0) {
                Toast
                    .makeText(this, "Нужно заполнить все поля", Toast.LENGTH_SHORT)
                    .show();
            } else {
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
            }
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
        } else if (v.equals(btnQrcode)) {
            onScanButtonClicked();
        }
    }

    // Стандартная библиотека ML kit от google позволяет выполнять сканирование кодов
    // без явного вызова камеры с помощью GmsBarcodeScanner
    public void onScanButtonClicked() {
        GmsBarcodeScannerOptions.Builder optionsBuilder = new GmsBarcodeScannerOptions.Builder();
        GmsBarcodeScanner gmsBarcodeScanner =
                GmsBarcodeScanning.getClient(this, optionsBuilder.build());
        gmsBarcodeScanner
                .startScan()
                .addOnSuccessListener(this::updateQRcode)
                .addOnFailureListener(
                        e -> textViewQrcode.setText(getErrorMessage(e)))
                .addOnCanceledListener(
                        () -> textViewQrcode.setText(getString(R.string.error_scanner_cancelled)));
    }

    private void updateQRcode(Barcode barcode) {
        MainActivity.database.carDao().setQrcode(
                barcode.getDisplayValue(),
                id
        );
        String i = MainActivity.database.carDao().getQrcode(id);
        textViewQrcode.setText(i);
    }

    @SuppressLint("SwitchIntDef")
    private String getErrorMessage(Exception e) {
        if (e instanceof MlKitException) {
            switch (((MlKitException) e).getErrorCode()) {
                case MlKitException.CODE_SCANNER_CAMERA_PERMISSION_NOT_GRANTED:
                    return getString(R.string.error_camera_permission_not_granted);
                case MlKitException.CODE_SCANNER_APP_NAME_UNAVAILABLE:
                    return getString(R.string.error_app_name_unavailable);
                default:
                    return getString(R.string.error_default_message, e);
            }
        } else {
            return e.getMessage();
        }
    }
}
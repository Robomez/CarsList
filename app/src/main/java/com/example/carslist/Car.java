package com.example.carslist;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Класс для описания таблицы для библиотеки Room
@Entity (tableName = "cars")
public class Car {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "brand")
    @NonNull public String brand;
    @ColumnInfo(name = "model")
    @NonNull public String model;
    @ColumnInfo(name = "color")
    public String color;
    @ColumnInfo(name = "price")
    @NonNull public int price;
    @ColumnInfo(name = "qrcode")
    public String qrcode;
}

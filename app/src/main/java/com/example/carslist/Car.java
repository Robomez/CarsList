package com.example.carslist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Класс для описания таблицы для библиотеки Room
@Entity (tableName = "cars")
public class Car {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "brand")
    public String brand;
    @ColumnInfo(name = "model")
    public String model;
    @ColumnInfo(name = "color")
    public String color;
    @ColumnInfo(name = "price")
    public int price;
}

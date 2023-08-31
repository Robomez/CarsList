package com.example.carslist;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

// Интерфейс Data Access Object для исполнения запросов к БД
@Dao
public interface CarDao {

    // Запрос для отображения всех авто
    @Query("SELECT * FROM cars")
    List<Car> getAllCars();

    // Запрос для добавления нового автомобиля в базу
    @Query("INSERT INTO cars (brand, model, color, price) VALUES (:brand, :model, :color, :price)")
    void create(
            String brand,
            String model,
            String color,
            int price
    );

    // Запрос для обновления информации об автомобиле
    @Query("UPDATE cars SET brand = :brand, model = :model, color = :color, price = :price WHERE id = :id")
    void modify(String brand, String model, String color, int price, int id);
}

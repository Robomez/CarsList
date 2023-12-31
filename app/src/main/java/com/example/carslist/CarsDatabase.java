package com.example.carslist;

import androidx.room.Database;
import androidx.room.RoomDatabase;

// Класс БД, который собирает все Dao вместе. По сути для каждой таблицы в БД своя сущность (entity) Дао.
// Room сама реализует метод этого абстрактного кл при сборке.
@Database(entities = {Car.class}, version = 1)
public abstract class CarsDatabase extends RoomDatabase {
    public abstract CarDao carDao();
}

package com.hassan.lalamove.database;

import com.hassan.lalamove.models.Delivery;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DeliveryDao {

    @Query("SELECT * FROM deliveries  LIMIT :limit OFFSET :offset")
    List<Delivery> loadAllDeliveries(int offset ,int limit );

    @Insert(onConflict = REPLACE)
    void insertPerson(Delivery person);

    @Update
    void updatePerson(Delivery person);

    @Delete
    void delete(Delivery person);

    @Query("SELECT * FROM deliveries WHERE id = :id")
    Delivery loadPersonById(String id);
}


package com.example.rentalmanager.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface PropertyDao {

    @Query("Select * from Property")
    List<Property> getAllProperty();

    @Insert
    void insertProperty(Property... property);

    @Update
    void updateProperty(Property property);

    @Delete
    void deleteProperty(Property property);

//


}

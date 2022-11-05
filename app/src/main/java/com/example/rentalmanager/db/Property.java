package com.example.rentalmanager.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Property {


    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int uid2;

    @ColumnInfo(name = "address")
    public String address;

//    int image;
//    double bedrooms;
//    int squareFeet;
//    double bathrooms;
//    double units;
//    int lotSize;
//    int yearBuilt;
//    String purchaseMonth;
//    int purchaseYear;
//    int ageOfRoof;
//    boolean waterHeaterServiced;
//    boolean mortgage;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

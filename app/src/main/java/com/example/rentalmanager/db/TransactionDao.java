package com.example.rentalmanager.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//all the quiries will go inside this interface

@Dao
public interface TransactionDao {

    @Query("Select * from Transactions")
    List<Transactions> getAllTransactions();

    @Insert
    void insertTransaction(Transactions... transactions);

    @Update
    void updateTransaction(Transactions transactions);

    @Delete
    void deleteTransaction(Transactions transactions);

}
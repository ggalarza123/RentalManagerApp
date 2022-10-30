package com.example.rentalmanager.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// this @Entity will add the import above
@Entity
public class Transactions {

    // this will be our primary key for the Transactions class- another way would be to say Transactions table
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int uid;

    // Columns is the instructions on android dev and each @ should have () with name = "yourname"
    @ColumnInfo(name = "transactionTitle")
    public String transactionTitle;

    @ColumnInfo(name = "transactionAmount")
    public double transactionAmount;

    @ColumnInfo(name = "propertyAddress")
    public String propertyAddress;

    @ColumnInfo(name = "transactionCategory")
    public String transactionCategory;

    @ColumnInfo(name = "transactionNotes")
    public String transactionNotes;

    @ColumnInfo(name = "transactionDate")
    public String transactionDate;

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionTitle() {
        return transactionTitle;
    }

    public void setTransactionTitle(String transactionTitle) {
        this.transactionTitle = transactionTitle;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getProperty() {
        return propertyAddress;
    }

    public void setProperty(String property) {
        this.propertyAddress = property;
    }

    public String getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(String transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public String getTransactionNotes() {
        return transactionNotes;
    }

    public void setTransactionNotes(String transactionNotes) {
        this.transactionNotes = transactionNotes;
    }
}

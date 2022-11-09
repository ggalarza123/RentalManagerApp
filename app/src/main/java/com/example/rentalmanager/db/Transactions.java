package com.example.rentalmanager.db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// this @Entity will add the import above
@Entity
public class Transactions implements Parcelable {

    // this will be our primary key for the Transactions class- another way would be to say Transactions table
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int uid;

    // Columns is the instructions on android dev and each @ should have () with name = "yourname"
    @ColumnInfo(name = "transactionPaidTo")
    public String transactionPaidTo;

    @ColumnInfo(name = "tempPaidTo")
    public static String tempPaidTo;

    @ColumnInfo(name = "transactionAmount")
    public double transactionAmount;

    @ColumnInfo(name = "tempTransActionAmount")
    public static String tempTransActionAmount;

    @ColumnInfo(name = "propertyAddress")
    public String propertyAddress;

    @ColumnInfo(name = "tempAddress")
    public static String tempAddress;

    @ColumnInfo(name = "transactionCategory")
    public String transactionCategory;

    @ColumnInfo(name = "tempTransactionCategory")
    public static String tempTransactionCategory;

    @ColumnInfo(name = "transactionNotes")
    public String transactionNotes;

    @ColumnInfo(name = "tempNotes")
    public static String tempNotes;

    @ColumnInfo(name = "transactionDate")
    public String transactionDate;

    @ColumnInfo(name = "tempDate")
    public static String tempDate;

    public Transactions() {

    }
    protected Transactions(Parcel in) {
        uid = in.readInt();
        transactionPaidTo = in.readString();
        transactionAmount = in.readDouble();
        propertyAddress = in.readString();
        transactionCategory = in.readString();
        transactionNotes = in.readString();
        transactionDate = in.readString();
    }

    public static final Creator<Transactions> CREATOR = new Creator<Transactions>() {
        @Override
        public Transactions createFromParcel(Parcel in) {
            return new Transactions(in);
        }

        @Override
        public Transactions[] newArray(int size) {
            return new Transactions[size];
        }
    };

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionPaidTo() {
        return transactionPaidTo;
    }

    public void setTransactionPaidTo(String transactionPaidTo) {
        this.transactionPaidTo = transactionPaidTo;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(uid);
        parcel.writeString(transactionPaidTo);
        parcel.writeDouble(transactionAmount);
        parcel.writeString(propertyAddress);
        parcel.writeString(transactionCategory);
        parcel.writeString(transactionNotes);
        parcel.writeString(transactionDate);
    }
}

package com.example.rentalmanager.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TransactionCategories {

    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "categoryID")
    public int categoryID;
    @ColumnInfo(name = "admin")
    public String admin;
    @ColumnInfo(name = "LelegalAndProffgal")
    public String legalAndProff;
    @ColumnInfo(name = "insurance")
    public String insurance;
    @ColumnInfo(name = "managementFee")
    public String managementFee;
    @ColumnInfo(name = "repairAndMaint")
    public String repairAndMaint;
    @ColumnInfo(name = "taxes")
    public String taxes;
    @ColumnInfo(name = "utilities")
    public String utilities;
    @ColumnInfo(name = "mortgageAndLoans")
    public String mortgageAndLoans;
    @ColumnInfo(name = "capitalExpenses")
    public String capitalExpenses;
    @ColumnInfo(name = "securityDeposits")
    public String securityDeposits;
    @ColumnInfo(name = "transfers")
    public String transfers;
    @ColumnInfo(name = "incomeRefund")
    public String incomeRefund;

}

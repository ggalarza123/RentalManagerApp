package com.example.rentalmanager.db;

import android.content.Context;

import android.widget.Toast;

import com.example.rentalmanager.Adapter.PropertyAdapter;


import java.util.ArrayList;
import java.util.List;

public class ManageData {

    public static void savePropertyData(String address, Context context) {

        Property property = new Property();
        property.setAddress(address);
        AppDatabase.getDatabase(context).getPropertyDao().insertProperty(property);
        Toast.makeText(context, "Property Saved", Toast.LENGTH_SHORT).show();
    }

    public static ArrayList getPropertyList(Context context) {

        ArrayList<String> propertiesArray = new ArrayList<>();
        propertiesArray.add("All Properties");
        AppDatabase db = AppDatabase.getDatabase(context);
        List<Property> temp = db.getPropertyDao().getAllProperty();
        for (Property list : temp) {
            propertiesArray.add(list.address);
        }
        return propertiesArray;
    }

    public static void saveTransactionData(boolean editing, Transactions selectedTransaction,
                                           Context context, String paidTo, String amount, String notes,
                                           String date, String category, String property) {


        if (editing == false) {

            Transactions transaction = new Transactions();
            transaction.setTransactionAmount(Double.parseDouble(amount));
            transaction.setTransactionNotes(notes);
            transaction.setTransactionPaidTo(paidTo);
            transaction.setTransactionDate(date);
            transaction.setTransactionCategory(category);
            transaction.setProperty(property);

            PropertyAdapter adapter1 = new PropertyAdapter(context, AppDatabase.getDatabase(context).getPropertyDao().getAllProperty());
            AppDatabase.getDatabase(context).getTransactionDao().insertTransaction(transaction);
            Toast.makeText(context, "Data Successfully Saved", Toast.LENGTH_SHORT).show();

        }
    }

    public static void editTransactionData(boolean editing, Transactions selectedTransaction, Transactions savedTransaction,
                                           Context context, String paidTo, String amount, String notes,
                                           String date, String category, String property) {

        if (editing == true) {

            if (selectedTransaction != null) {
//                selectedTransaction = getIntent().getParcelableExtra("transaction");
                selectedTransaction.setTransactionNotes(notes);
                selectedTransaction.setTransactionPaidTo(paidTo);
                selectedTransaction.setTransactionDate(date);
                selectedTransaction.setTransactionCategory(category);
                selectedTransaction.setProperty(property);
                selectedTransaction.setTransactionAmount(Double.parseDouble(amount));
                AppDatabase.getDatabase(context).getTransactionDao().updateTransaction(selectedTransaction);
                Toast.makeText(context, "Data Successfully Updated", Toast.LENGTH_SHORT).show();
            }


            if (savedTransaction != null) {
                savedTransaction.setTransactionNotes(notes);
                savedTransaction.setTransactionPaidTo(paidTo);
                savedTransaction.setTransactionDate(date);
                savedTransaction.setTransactionCategory(category);
                savedTransaction.setProperty(property);
                savedTransaction.setTransactionAmount(Double.parseDouble(amount));
                AppDatabase.getDatabase(context).getTransactionDao().updateTransaction(savedTransaction);
                Toast.makeText(context, "Data Successfully Updated", Toast.LENGTH_SHORT).show();

            }

        }

    }


}

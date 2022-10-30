package com.example.rentalmanager.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.rentalmanager.Adapter.TransactionAdapter;
import com.example.rentalmanager.R;
import com.example.rentalmanager.db.AppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    // creating a button that will lead a new activity that adds a new transaction
    private FloatingActionButton addNewTransaction;

    //RecyclerView I will pass the data saved to a recyclerView, matching it by R.id.
    RecyclerView transactionListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //finds the ***floatingactionbutton we are interested in, has to be floating action button***
        addNewTransaction = (FloatingActionButton) findViewById(R.id.add_transaction_btn);
        //adds a click listener that then calls a function that I create in a method below
        addNewTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });

        transactionListView = findViewById(R.id.recyclerView_new_transaction);
        getData();

    }

    private void getData() {
        TransactionAdapter adapter = new TransactionAdapter(this, AppDatabase.getDatabase(getApplicationContext()).getDao().getAllTransactions());
        transactionListView.setAdapter(adapter);
        transactionListView.setLayoutManager(new LinearLayoutManager(this));
    }
    // this was missing details originally, above has it all!
//    private void getData() {
//        transactionListView.setAdapter(new TransactionAdapter(getApplicationContext(), AppDatabase.getDatabase(getApplicationContext()).getDao().getAllTransactions()));
//    }

    // this is the method that is called above in OnCreate, which this method then creates a new intent which is
    // a few lines of code that lead to the next page
    private void openActivity2() {
        Intent intent = new Intent(this, TransactionActivity.class);
        //selecting the activity we wish to start which is taking us to a new java file I wrote.
        startActivity(intent);
    }
    // another option, instead of having an Activity window be opened, we could also have
    //the app open a pop/dialog, where the informaiton can be plugges in. For that we could
    // create the following method. being given a different name just to show difference
//    private void showAddTransaction() {
//      AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
//      View dialogView = getLayoutInflater().inflate(R.layout.add_transaction_details, null);
//        EditText enterCategoryInput = dialogView.findViewById(R.id.textInputEditText);
//    }
}
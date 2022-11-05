package com.example.rentalmanager.viewmodel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentalmanager.R;
import com.example.rentalmanager.db.AppDatabase;
import com.example.rentalmanager.db.Property;
import com.example.rentalmanager.db.Transactions;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {

    // need to create an instance of what I will set with clicklistener to go back
    private ImageView backBtn;
    // need to create another instance of what I will set with clicklistner to pass on all
    //the input to the DB
    private TextView doneBtn;


    // these objects will be for the user input areas, and will then be passed on to a new object
    TextInputEditText enterPaidTo, enterAmount, enterNotes;
    EditText enterDate;
    View categoryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // down below add_tran... specifices which layout this page is refering to
        setContentView(R.layout.add_transaction_input_layout);



        // back button object is an image so passed in image type, found by ID
        backBtn = (ImageView) findViewById(R.id.transaction_back_button);
        // set a set on click listener
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToTransactions();
            }
        });
        // done text thud using TextView here and finding by name of view, named it a button but not actually a button
        doneBtn = (TextView) findViewById(R.id.transaction_done_button);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();

            }
        });

        enterPaidTo = findViewById(R.id.paidToInput);
        enterAmount = findViewById(R.id.textAmountInput);
        enterNotes = findViewById(R.id.editNotes);
        enterDate = findViewById(R.id.editTextDate2);

        categoryButton = findViewById(R.id.categoryClickArrow);
        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), CategoryActivity.class));
            }
        });

        spinnerCreate();
    }

    //this code could go above in onCreate but created a seperate method instead for better code
    private void backToTransactions() {
        finish();
    }

    private void saveData(){

        String paidTo = enterPaidTo.getText().toString().trim();
        String amount = enterAmount.getText().toString().trim();
        String notes = enterNotes.getText().toString().trim();
        String date = enterDate.getText().toString().trim();


//        if (paidTo != null && amount != null && notes != null && date != null) {

            Transactions transaction = new Transactions();

            transaction.setTransactionAmount(Double.parseDouble(amount));
            transaction.setTransactionNotes(notes);
            transaction.setTransactionTitle(paidTo);
            transaction.setTransactionDate(date);

            AppDatabase.getDatabase(getApplicationContext()).getTransactionDao().insertTransaction(transaction);

            enterPaidTo.setText("");
        enterAmount.setText("");
        enterNotes.setText("");
        enterDate.setText("");
            Toast.makeText(this, "Data Successfully Saved", Toast.LENGTH_SHORT).show();

//        }


    }
    private void spinnerCreate() {
        ArrayList<String> testing = new ArrayList<>();
        testing.add("All Properties");
        // code here to add properties to testing array before they get shown

        AppDatabase db = AppDatabase.getDatabase(this);
        List<Property> temp = db.getPropertyDao().getAllProperty();

        for (Property list: temp) {
            testing.add(list.address);
        }

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, testing);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}
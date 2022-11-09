package com.example.rentalmanager.viewmodel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {

    // these objects will be for the user input areas, and will then be passed on to a new object
    TextInputEditText enterPaidTo, enterAmount, enterNotes, enterDate;
    Spinner propertySpinner;
    View categoryButton;
    DatePickerDialog datePickerDialog;
    boolean editing;
    Transactions selectedTransaction;
    // need to create an instance of what I will set with clicklistener to go back
    private ImageView backBtn;
    // need to create another instance of what I will set with clicklistner to pass on all
    //the input to the DB
    private TextView doneBtn, showCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_transaction_input_layout);
        backBtn = (ImageView) findViewById(R.id.transaction_back_button);
        // set a set on click listener
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                backToTransactions();
                editing = false;
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
        doneBtn = (TextView) findViewById(R.id.transaction_done_button);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
        propertySpinner = findViewById(R.id.spinner);
        enterPaidTo = findViewById(R.id.paidToInput);
        enterAmount = findViewById(R.id.textAmountInput);
        enterNotes = findViewById(R.id.editNotes);
        enterDate = findViewById(R.id.editTextDate2);
        showCategory = findViewById(R.id.textView3);


        editing = getIntent().getBooleanExtra("editing", false);
        selectedTransaction = getIntent().getParcelableExtra("transaction");

        // this is getting executed as the category was selected, everything below
        // is getting correct variables
// NEW NEW second add start IS NULL, HOWEVER variables are NOT NULL so executed BUG HERE!!!!, several NullPointers like propAddress and proper DAT
        // NEW NEW 1st add trans is NOT null for savedTransaction
        try { // this is null on startup of clicked transaction goes here, also null when clicking add transaction initially but
            Transactions savedTransaction = getIntent().getParcelableExtra("editedTransaction");
        // NEW NEW after click category on add transaction, code goes here and savedTransaction is NOT NULL so if is executed
            // 2. HOWEVER BIG OOPS HERE savedTransaction.tempPaidTo, savedTransaction.tempTransActionAmount ARE NOT NULL
            // 3. AND THEY DO GET EXECUTED
            // code on startup goes here after click of transaction, if part is null so nothing is executed
            // after selecting a category and coming back this is no longer null so the if statements are executed
            // this makes sence since temp sets up the savedTransaction object and is called when clicking category
            if (savedTransaction.tempPaidTo != null) {
                enterPaidTo.setText(savedTransaction.tempPaidTo);
            }
            if (savedTransaction.tempTransActionAmount != null) {
                enterAmount.setText(savedTransaction.tempTransActionAmount);
            }
            if (savedTransaction.tempNotes != null) {
                enterNotes.setText(savedTransaction.tempNotes);
            }
            if (savedTransaction.tempTransactionCategory != null) {
                showCategory.setText(savedTransaction.tempTransactionCategory);
            }

            if (savedTransaction.transactionDate != null) {
                enterDate.setText(savedTransaction.transactionDate);
            }
            if (savedTransaction.propertyAddress != null) {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }



        if (editing == false) {
            enterDate.setText(getTodaysDate());
        }

        if (editing == true) {

            if (selectedTransaction != null) {



                enterPaidTo.setText(selectedTransaction.transactionPaidTo);
                enterAmount.setText(String.valueOf(selectedTransaction.transactionAmount));
                enterNotes.setText(selectedTransaction.transactionNotes);
                showCategory.setText(selectedTransaction.transactionCategory);
                enterDate.setText(selectedTransaction.transactionDate);
                // pending property spinner set

            }
        }


        // this code will will input temporary transaction information when adding a new transaction
        // after saving and clicking done the data will be reset.
        if (editing == false) {

            System.out.println("checking to see if it goes through here it must");
            if (Transactions.tempPaidTo != null) {
                enterPaidTo.setText(Transactions.tempPaidTo);
                Transactions.tempPaidTo = "";
            }
            if (Transactions.tempTransActionAmount != null) {
                enterAmount.setText(Transactions.tempTransActionAmount);
                Transactions.tempTransActionAmount = "";
            }
            if (Transactions.tempNotes != null) {
                enterNotes.setText(Transactions.tempNotes);
                Transactions.tempNotes = "";
            }
            if (Transactions.tempTransactionCategory != null) {
                showCategory.setText(Transactions.tempTransactionCategory);
                Transactions.tempTransactionCategory = "Choose Category";
            }
        }

        datePicker();
        enterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(view);
            }
        });
        categoryButton = findViewById(R.id.categoryClickArrow);
        categoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // tempData is casing the vew messing up after clicking on a Category

                //deleting temp data puts the category, but any other changes do not update once the page comes back, additionally
                // clicking on an existing transaction populates with the same category

                Intent intentTempData = new Intent(getApplicationContext(), CategoryActivity.class);
                boolean clicked = getIntent().getBooleanExtra("editing", false);
                tempData(intentTempData);
                intentTempData.putExtra("editing", clicked);
//                Transactions clickedItem = getIntent().getParcelableExtra("transaction");
//                intentTempData.putExtra("clickedItem", clickedItem);
                startActivity(intentTempData);

            }
        });


        spinnerCreate();

    }

    private void datePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                enterDate.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    public String getMonthFormat(int month) {
        switch (month) {
            case 1:
                return "JAN";
            case 2:
                return "FEB";
            case 3:
                return "MAR";
            case 4:
                return "APR";
            case 5:
                return "MAY";
            case 6:
                return "JUN";
            case 7:
                return "JUL";
            case 8:
                return "AUG";
            case 9:
                return "SEP";
            case 10:
                return "OCT";
            case 11:
                return "NOV";
            case 12:
                return "DEC";
        }
        return "JAN";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();

    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }


    private void backToTransactions() {

        editing = false;

        finish();


    }

    private void tempData(Intent intent) {
        // when clicked on adding a transaction and then choosing category this was callled, all data stored correctly in editedTransaction
        Transactions editedTransaction = new Transactions();
        editedTransaction.tempPaidTo = enterPaidTo.getText().toString().trim();
        editedTransaction.tempTransActionAmount = enterAmount.getText().toString().trim();
        editedTransaction.tempNotes = enterNotes.getText().toString().trim();
        editedTransaction.tempAddress = propertySpinner.getSelectedItem().toString().trim();
        editedTransaction.tempDate = enterDate.getText().toString().trim();
        editedTransaction.tempTransactionCategory = showCategory.getText().toString().trim();

        intent.putExtra("editedTransaction", editedTransaction);

    }
// not yet used
//    public void clearTempData() {
//        Transactions.tempPaidTo = "";
//        Transactions.tempNotes = "";
//        Transactions.tempAddress = "";
//        Transactions.tempTransActionAmount = "";
//        Transactions.tempDate = "";
//        Transactions.tempTransactionCategory = "";
//    }

    private void saveData() {

        String paidTo = enterPaidTo.getText().toString().trim();
        // comas in amount make the app crash on saving page
        String amount = enterAmount.getText().toString().trim();
        String notes = enterNotes.getText().toString().trim();
        String date = enterDate.getText().toString().trim();
        String category = showCategory.getText().toString().trim();
        String property = propertySpinner.getSelectedItem().toString().trim();
        int position = propertySpinner.getSelectedItemPosition();
        System.out.println("1. is this called editing pre verision: "+ editing);
        if (editing == false) {
            System.out.println("1. is this called");
            Transactions transaction = new Transactions();

            transaction.setTransactionAmount(Double.parseDouble(amount));
            transaction.setTransactionNotes(notes);
            transaction.setTransactionPaidTo(paidTo);
            transaction.setTransactionDate(date);
            transaction.setTransactionCategory(category);
            transaction.setProperty(property);

            AppDatabase.getDatabase(getApplicationContext()).getTransactionDao().insertTransaction(transaction);

//            resetFields();
// deleting fields causes an issue
            Toast.makeText(this, "Data Successfully Saved", Toast.LENGTH_SHORT).show();
            onRestart();
        }




        if (editing == true) {
            try {
                selectedTransaction = getIntent().getParcelableExtra("transaction");
            } catch (Exception e) {
                selectedTransaction = new Transactions();
                e.printStackTrace();
            }


            selectedTransaction.setTransactionNotes(notes);
            selectedTransaction.setTransactionPaidTo(paidTo);
            selectedTransaction.setTransactionDate(date);
            selectedTransaction.setTransactionCategory(category);
            selectedTransaction.setProperty(property);
            selectedTransaction.setTransactionAmount(Double.parseDouble(amount));

            AppDatabase.getDatabase(getApplicationContext()).getTransactionDao().updateTransaction(selectedTransaction);
            Toast.makeText(this, "Data Successfully Updated", Toast.LENGTH_SHORT).show();

        }


    }


//    private void resetFields() {
//        if (editing == false) {
//
//
//            enterPaidTo.setText("");
//            enterAmount.setText("");
//            enterNotes.setText("");
//            enterDate.setText(getTodaysDate());
//            showCategory.setText("Choose Category");
//            propertySpinner.setSelection(0);
//
//            Transactions.tempPaidTo = "";
//            Transactions.tempTransActionAmount = "";
//            Transactions.tempNotes = "";
//            Transactions.tempTransactionCategory = "Choose Category";
//        }
//
//    }

    @Override
    protected void onRestart() {
        // does not seem to be called anymore
        // set fields to blank here

        System.out.println("On restaert is called");
        super.onRestart();

    }

    private void spinnerCreate() {
        ArrayList<String> testing = new ArrayList<>();
        testing.add("All Properties");

        AppDatabase db = AppDatabase.getDatabase(this);
        List<Property> temp = db.getPropertyDao().getAllProperty();

        for (Property list : temp) {
            testing.add(list.address);
        }

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, testing);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}
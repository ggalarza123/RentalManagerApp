package com.example.rentalmanager.viewmodel;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentalmanager.db.ManageData;
import com.example.rentalmanager.R;
import com.example.rentalmanager.db.AppDatabase;
import com.example.rentalmanager.db.Property;
import com.example.rentalmanager.db.Transactions;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {

    TextInputEditText enterPaidTo, enterAmount, enterNotes, enterDate;
    Spinner propertySpinner;
    View categoryButton;
    DatePickerDialog datePickerDialog;
    boolean editing;
    Button deleteButton, yesButton, noButton;
    Transactions selectedTransaction, savedTransaction;

    private ImageView backBtn;
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
                if (TextUtils.isEmpty(enterAmount.getText())) {
                    Toast.makeText(getApplicationContext(), "Amount is required.", Toast.LENGTH_SHORT).show();
                } else {
                    saveData();
                }
            }
        });
        propertySpinner = findViewById(R.id.spinner);
        enterPaidTo = findViewById(R.id.paidToInput);
        enterAmount = findViewById(R.id.textAmountInput);
        enterNotes = findViewById(R.id.editNotes);
        enterDate = findViewById(R.id.editTextDate2);
        showCategory = findViewById(R.id.textView3);
        deleteButton = findViewById(R.id.delete_button);

        spinnerCreate();
        editing = getIntent().getBooleanExtra("editing", false);
        selectedTransaction = getIntent().getParcelableExtra("transaction");

        if (editing == true) {

            deleteButton.setVisibility(View.VISIBLE);

            try {
                savedTransaction = getIntent().getParcelableExtra("editedTransaction");

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

                if (savedTransaction.tempDate != null) {
                    enterDate.setText(savedTransaction.tempDate);
                }
                if (savedTransaction.tempAddress != null) {
                    String address = savedTransaction.tempAddress;
                    List<Property> temp = AppDatabase.getDatabase(getApplicationContext()).getPropertyDao().getAllProperty();
                    int index = 0;
                    for (Property list : temp) {
                        if (list.address.equals(address)) {
                            index = temp.indexOf(list);
                        }
                    }
                    propertySpinner.setSelection(index + 1);
                }

            } catch (Exception e) {
                System.out.println("savedTransaction empty");
            }

            if (selectedTransaction != null) {

                enterPaidTo.setText(selectedTransaction.transactionPaidTo);
                enterAmount.setText(String.valueOf(selectedTransaction.transactionAmount));
                enterNotes.setText(selectedTransaction.transactionNotes);
                showCategory.setText(selectedTransaction.transactionCategory);
                enterDate.setText(selectedTransaction.transactionDate);
                // CODE TO ADD SPINNER VIEWED IS BEING DONE HERE
                String address = selectedTransaction.propertyAddress;
                List<Property> temp = AppDatabase.getDatabase(getApplicationContext()).getPropertyDao().getAllProperty();
                int index = 0;
                for (Property list : temp) {
                    if (list.address.equals(address)) {
                        index = temp.indexOf(list);
                    }
                }
                propertySpinner.setSelection(index + 1);

            }
        }


        if (editing == false) {
            enterDate.setText(getTodaysDate());
            deleteButton.setVisibility(View.GONE);

            if (Transactions.tempPaidTo != null) {
                enterPaidTo.setText(Transactions.tempPaidTo);
            }
            if (Transactions.tempTransActionAmount != null) {
                enterAmount.setText(Transactions.tempTransActionAmount);
            }
            if (Transactions.tempNotes != null) {
                enterNotes.setText(Transactions.tempNotes);
            }
            if (Transactions.tempTransactionCategory != null) {
                showCategory.setText(Transactions.tempTransactionCategory);
            }
            if (Transactions.tempDate != null) {
                enterDate.setText(Transactions.tempDate);
            }
            if (Transactions.tempAddress != null) {
                String address = Transactions.tempAddress;
                List<Property> temp = AppDatabase.getDatabase(getApplicationContext()).getPropertyDao().getAllProperty();
                int index = 0;
                for (Property list : temp) {
                    if (list.address.equals(address)) {
                        index = temp.indexOf(list);
                    }
                }
                if (Transactions.tempAddress.equals("")) {
                    index = -1;
                }
                if (Transactions.tempAddress.equals("All Properties")) {
                    index = -1;
                }

                propertySpinner.setSelection(index + 1);
            } else {
                propertySpinner.setSelection(0);
            }
            clearTemps();
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

                Intent intentTempData = new Intent(getApplicationContext(), CategoryActivity.class);
                boolean clicked = getIntent().getBooleanExtra("editing", false);
                savedTransaction = selectedTransaction;
                tempData(intentTempData);
                intentTempData.putExtra("editing", clicked);
                startActivity(intentTempData);

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


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


    private void tempData(Intent intent) {
        savedTransaction.tempPaidTo = enterPaidTo.getText().toString().trim();
        savedTransaction.tempTransActionAmount = enterAmount.getText().toString().trim();
        savedTransaction.tempNotes = enterNotes.getText().toString().trim();
        savedTransaction.tempAddress = propertySpinner.getSelectedItem().toString().trim();
        savedTransaction.tempDate = enterDate.getText().toString().trim();
        savedTransaction.tempTransactionCategory = showCategory.getText().toString().trim();
        intent.putExtra("editedTransaction", savedTransaction);
    }

    private void saveData() {

        String paidTo = enterPaidTo.getText().toString().trim();
        // comas in amount make the app crash on saving page
        String amount = enterAmount.getText().toString().trim();
        String notes = enterNotes.getText().toString().trim();
        String date = enterDate.getText().toString().trim();
        String category = showCategory.getText().toString().trim();
        String property = propertySpinner.getSelectedItem().toString().trim();

        if (editing == false) {
            ManageData.saveTransactionData(false, selectedTransaction, this, paidTo, amount, notes, date, category, property);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        if (editing == true) {
            ManageData.editTransactionData(true, selectedTransaction, savedTransaction,
                    this, paidTo, amount, notes,
                    date, category, property);
            if (savedTransaction != null) {
                clearTemps();
            }

        }

    }

    private void clearTemps() {
        Transactions.tempPaidTo = "";
        Transactions.tempTransactionCategory = "Choose Category";
        Transactions.tempAddress = "";
        Transactions.tempDate = getTodaysDate();
        Transactions.tempNotes = "";
        Transactions.tempTransActionAmount = "";
    }

    private void showDialog() {
        Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog_delete_transaction);

        yesButton = dialog.findViewById(R.id.button_yes);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Transactions savedTransaction = getIntent().getParcelableExtra("transaction");
                AppDatabase.getDatabase(getApplicationContext()).getTransactionDao().deleteTransaction(savedTransaction);
                Toast.makeText(getApplicationContext(), "Data Deleted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();

        noButton = dialog.findViewById(R.id.button_no);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void spinnerCreate() {
        ArrayList<String> spinnerList = ManageData.getPropertyList(this);
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}
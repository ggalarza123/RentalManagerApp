package com.example.rentalmanager.viewmodel;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.rentalmanager.R;
import com.example.rentalmanager.db.AppDatabase;
import com.example.rentalmanager.db.Property;

import java.util.ArrayList;
import java.util.List;

public class ReportsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView backArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        initialStart();
    }

    private void initialStart() {
        spinnerCreate();
        spinnerCreate2();
        spinnerCreate3();
        spinnerCreate4();
        spinnerCreate5();
        backArrow = findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }



    private void spinnerCreate() {
        ArrayList<String> testing = new ArrayList<>();
        testing.add("All Properties");
        // code here to add properties to testing array before they get shown

        AppDatabase db = AppDatabase.getDatabase(this);
        List<Property> temp = db.getPropertyDao().getAllProperty();

        for (Property list : temp) {
            testing.add(list.address);
        }

        Spinner spinner = findViewById(R.id.spinner_all_properties);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, testing);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void spinnerSet(@IdRes int id, ArrayList temp) {
        Spinner spinner = findViewById(id);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, temp);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void spinnerCreate2() {
        ArrayList<String> allReports = new ArrayList<>();
        allReports.add("Net Cash Flow");
        allReports.add("Income Statement");
        allReports.add("Balance Sheet");
        allReports.add("Rent Roll");
        allReports.add("Tenant Ledger");
        allReports.add("Schedule of Real Estate Owned");
        allReports.add("General Ledger");
        allReports.add("Capital Expenses");
        allReports.add("Tax Package");
        allReports.add("Stress Test");
        spinnerSet(R.id.spinner_reports, allReports);

    }

    private void spinnerCreate3() {
        ArrayList<String> timeFrame = new ArrayList<>();
        timeFrame.add("Last 12 months");
        timeFrame.add("Month to Date");
        timeFrame.add("Last Month");
        timeFrame.add("Year to Date");
        timeFrame.add("Last Calender Year");
        timeFrame.add("Custom");

        spinnerSet(R.id.spinner_month_or_property, timeFrame);
    }
    private void spinnerCreate4() {
        ArrayList<String> category = new ArrayList<>();
        category.add("By Category");
        category.add("By Sub-Category");

        spinnerSet(R.id.spinner_by_category, category);
    }

    private void spinnerCreate5() {
        ArrayList<String> export = new ArrayList<>();
        export.add("Excel");
        export.add("PDF");

        spinnerSet(R.id.spinner2_excel_or_pdf, export);
    }
}
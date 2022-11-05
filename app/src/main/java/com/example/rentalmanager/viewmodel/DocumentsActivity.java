package com.example.rentalmanager.viewmodel;


import androidx.appcompat.app.AppCompatActivity;

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

public class DocumentsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    ImageView backArrowDoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);
        initialStart();

    }

    private void initialStart() {


        backArrowDoc = findViewById(R.id.back_arrow_image);
        backArrowDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        spinnerCreate();
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

        Spinner spinner = findViewById(R.id.spinner_document_properties);
        ArrayAdapter<String> adapter = new ArrayAdapter(DocumentsActivity.this, android.R.layout.simple_spinner_item, testing);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        String text = adapterView.getItemAtPosition(i).toString();
//        Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
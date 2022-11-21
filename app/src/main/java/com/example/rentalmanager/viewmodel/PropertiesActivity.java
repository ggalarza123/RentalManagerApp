package com.example.rentalmanager.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalmanager.Adapter.PropertyAdapter;
import com.example.rentalmanager.db.ManageData;
import com.example.rentalmanager.R;
import com.example.rentalmanager.db.AppDatabase;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

public class PropertiesActivity extends AppCompatActivity {

    private Button btnShowDialog, btnAddProperty;
    private EditText editText;
    private ImageView backArrow;
    RecyclerView propertyRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties);
        initialStart();
    }

    private void initialStart() {
        backArrow = findViewById(R.id.imageView9);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnShowDialog = findViewById(R.id.button_add_property);
        propertyRecyclerView = findViewById(R.id.recycler_view_add_property);
        getData();
        btnShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
        spinnerCreate();
        ImageView backArrow;
        backArrow = findViewById(R.id.imageView9);
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

    private void getData() {
        PropertyAdapter adapter = new PropertyAdapter(this, AppDatabase.getDatabase(getApplicationContext()).getPropertyDao().getAllProperty());
        propertyRecyclerView.setAdapter(adapter);
        propertyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.property_add_dialog_view);

        btnAddProperty = dialog.findViewById(R.id.button_save_property);

        btnAddProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                getData();
                dialog.dismiss(); // this to close dialog
            }
        });
        dialog.show();
        editText = dialog.findViewById(R.id.edit_address_txt);

    }

    private void saveData() {
        String address = editText.getText().toString().trim();
        ManageData.savePropertyData(address, this);
        editText.setText("");
    }

    private void spinnerCreate() {
        ArrayList propertiesArray = ManageData.getPropertyList(this);
        Spinner spinner = findViewById(R.id.spinner2_properties);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, propertiesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


}
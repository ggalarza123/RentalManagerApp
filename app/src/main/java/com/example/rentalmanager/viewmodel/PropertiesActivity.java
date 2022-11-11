package com.example.rentalmanager.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalmanager.Adapter.PropertyAdapter;
import com.example.rentalmanager.R;
import com.example.rentalmanager.db.AppDatabase;
import com.example.rentalmanager.db.Property;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

//                dialog.dismiss(); // this to close dialog
            }
        });
        dialog.show();
        editText = dialog.findViewById(R.id.edit_address_txt);

    }

    private void saveData() {

        String address = editText.getText().toString().trim();
        Property property = new Property();
        property.setAddress(address);
        AppDatabase.getDatabase(getApplicationContext()).getPropertyDao().insertProperty(property);
        editText.setText("");
        Toast.makeText(this, "Property Saved", Toast.LENGTH_SHORT).show();
    }

    private void spinnerCreate() {
        ArrayList<String> propertiesArray = new ArrayList<>();
        propertiesArray.add("All Properties");
        // code here to add properties to testing array before they get shown

        AppDatabase db = AppDatabase.getDatabase(this);
        List<Property> temp = db.getPropertyDao().getAllProperty();

        for (Property list: temp) {
            propertiesArray.add(list.address);
        }

        Spinner spinner = findViewById(R.id.spinner2_properties);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, propertiesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


}
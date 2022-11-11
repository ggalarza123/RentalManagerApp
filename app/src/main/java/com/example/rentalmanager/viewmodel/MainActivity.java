package com.example.rentalmanager.viewmodel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.rentalmanager.Adapter.TransactionAdapter;
import com.example.rentalmanager.R;
import com.example.rentalmanager.db.AppDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //RecyclerView I will pass the data saved to a recyclerView, matching it by R.id.
    RecyclerView transactionListView;

    // these three objects will be for the navigation menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    // creating a button that will lead a new activity that adds a new transaction
    private FloatingActionButton addNewTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialStart();


    }


    private void initialStart() {
        setMenu();
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

    //reloads the page so that any new transactions recently added will be visible
    @Override
    protected void onRestart() {
        super.onRestart();
        initialStart();
    }


    private void getData() {
        TransactionAdapter adapter = new TransactionAdapter(this, AppDatabase.getDatabase(getApplicationContext()).getTransactionDao().getAllTransactions());
        transactionListView.setAdapter(adapter);
        transactionListView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void setMenu() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_menu_40);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    // this is the method that is called above in OnCreate, which this method then creates a new intent which is
    // a few lines of code that lead to the next page
    private void openActivity2() {
        Intent intent = new Intent(this, TransactionActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        //selecting the activity we wish to start which is taking us to a new java file I wrote.
        startActivity(intent);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_properties:
                Intent intent = new Intent(this, PropertiesActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_transactions:
                break;
            case R.id.nav_documents:
                Intent intent3 = new Intent(this,DocumentsActivity.class);
                startActivity(intent3);
                break;
            case R.id.nav_reports:
                Intent intent4 = new Intent(this, ReportsActivity.class);
                startActivity(intent4);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
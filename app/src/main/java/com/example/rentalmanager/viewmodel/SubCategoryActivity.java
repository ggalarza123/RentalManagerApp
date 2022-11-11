package com.example.rentalmanager.viewmodel;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.rentalmanager.Adapter.CategoryAdapter;
import com.example.rentalmanager.R;
import com.example.rentalmanager.db.Categories;
import com.example.rentalmanager.db.RecyclerViewInterface;
import com.example.rentalmanager.db.Transactions;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity implements RecyclerViewInterface {

    int[] xmlFileNames = {R.array.admin_and_other_full_list, R.array.legal_and_professional_full_list, R.array.insurance_full_list, R.array.management_fees_full_list, R.array.repairs_and_maint_full_list, R.array.taxes_full_list, R.array.utilities_full_list, R.array.mortgage_and_loans_full_list, R.array.capital_expenses_full_list, R.array.security_deposits_full_list, R.array.transfers_full_list, R.array.income_refund_list};
    ArrayList<Categories> subCategories = new ArrayList<>();
    int position;
    ImageView backArrow;
    String[] subCategoryNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_sub_categories_layout);
        initialStart();
    }

    private void initialStart() {
        RecyclerView recyclerView2 = findViewById(R.id.recycler_view_category_view);
        position = parseInt(getIntent().getStringExtra("POSITION"));

        setUpCategories(position);
        CategoryAdapter adapter = new CategoryAdapter(this, subCategories, this);
        recyclerView2.setAdapter(adapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        backArrow = findViewById(R.id.transaction_back_button);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

    }

    private void setUpCategories(int position) {
        subCategoryNames = getResources().getStringArray(xmlFileNames[position]);

        for (int i = 0; i < subCategoryNames.length; i++) {
            subCategories.add(new Categories(subCategoryNames[i]));
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getApplicationContext(), TransactionActivity.class);
        Transactions.tempTransactionCategory = subCategories.get(position).getCategory();
        intent.putExtra("category", Transactions.tempTransactionCategory);

        Transactions editedTransaction = getIntent().getExtras().getParcelable("editedTransaction");
        intent.putExtra("editedTransaction", editedTransaction);

//        Transactions selectedTransaction = getIntent().getExtras().getParcelable("transaction");
//        intent.putExtra("transaction", selectedTransaction);
//        Transactions clickedItem = getIntent().getParcelableExtra("transaction");
//        intent.putExtra("transaction", clickedItem);
        boolean clicked = getIntent().getBooleanExtra("editing", false);
        intent.putExtra("editing", clicked);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);


    }
}
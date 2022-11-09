package com.example.rentalmanager.viewmodel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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

// added implements transactionDao for second recyclerview
public class CategoryActivity extends AppCompatActivity implements RecyclerViewInterface {


    ArrayList<Categories> categories = new ArrayList<>();
    ImageView backButton;
//    int[] xmlFileNames = {R.array.admin_and_other_full_list, R.array.legal_and_professional_full_list, R.array.insurance_full_list, R.array.management_fees_full_list, R.array.repairs_and_maint_full_list, R.array.taxes_full_list, R.array.utilities_full_list, R.array.mortgage_and_loans_full_list, R.array.capital_expenses_full_list, R.array.security_deposits_full_list, R.array.transfers_full_list, R.array.income_refund_list};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view_categories_layout);


        initialStart();
    }
    //          new code for second recycler view
    private void initialStart() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_category_view);
        setUpCategories();

        CategoryAdapter adapter = new CategoryAdapter(this, categories, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        backButton = findViewById(R.id.transaction_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setUpCategories() {
        String[] mainCategoryNames = getResources().getStringArray(R.array.categories_main_full_list);

        for (int i = 0; i < mainCategoryNames.length; i++) {
            categories.add(new Categories(mainCategoryNames[i], R.drawable.ic_baseline_keyboard_arrow_right_24));
        }
    }

    // the int position, in other words the position of the item clicked is defined in category adapter.
    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(this, SubCategoryActivity.class);

        intent.putExtra("NAME",categories.get(position).getCategory());

        intent.putExtra("POSITION", Integer.toString(position));

        Transactions editedTransaction = getIntent().getExtras().getParcelable("editedTransaction");
        intent.putExtra("editedTransaction", editedTransaction);

        boolean clicked = getIntent().getBooleanExtra("editing", false);
        intent.putExtra("editing", clicked);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}


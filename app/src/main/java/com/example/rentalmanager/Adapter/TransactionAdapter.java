package com.example.rentalmanager.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalmanager.R;
import com.example.rentalmanager.db.Transactions;
import com.example.rentalmanager.viewmodel.TransactionActivity;

import java.util.Collections;
import java.util.List;


public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {
    Context context;
    List<Transactions> listOfTransactions;
    static int itemSelected = RecyclerView.NO_POSITION;

    public TransactionAdapter(Context context, List<Transactions> listOfTransactions) {
        this.context = context;
        Collections.reverse(listOfTransactions);
        this.listOfTransactions = listOfTransactions;
    }


    @NonNull
    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_main_recyclerview, parent, false);
        return new TransactionAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //assiging value to each of our rows as they come back on the screen
        // based on the position of our recycler view
        holder.title.setText(listOfTransactions.get(position).getTransactionPaidTo());
        holder.amount.setText(String.valueOf((listOfTransactions.get(position).getTransactionAmount())));
        holder.date.setText(listOfTransactions.get(position).getTransactionDate());
        holder.category.setText(listOfTransactions.get(position).getTransactionCategory());
        holder.propertyAddress.setText(listOfTransactions.get(position).getProperty());
        // for image holder.imageView.setImageResource(list.get(position).getImage());
        holder.itemView.setSelected(itemSelected == position);

    }

    @Override
    public int getItemCount() {
        //total items in total
        return listOfTransactions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // similar to on create, grabs all the view and assigns them to variables

        TextView title, category, amount, propertyAddress, date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.textView_expense_title);
            date = itemView.findViewById(R.id.textDate);
            amount = itemView.findViewById(R.id.textView_cost_amount);
            category = itemView.findViewById(R.id.textView_expense_cataegory);
            propertyAddress = itemView.findViewById(R.id.textView_property);
            date = itemView.findViewById(R.id.textDate);
        }

        @Override
        public void onClick(View view) {
            if (getAdapterPosition() == RecyclerView.NO_POSITION) {
                return;
            }
            else {
                notifyItemChanged(itemSelected);
                itemSelected = getAdapterPosition();
                notifyItemChanged(itemSelected);
                itemSelected = getAdapterPosition();
                setUpNewActivity();
            }
        }

    }

    public void setUpNewActivity() {
        Intent intent = new Intent(this.context, TransactionActivity.class);
        Transactions clickedItem = listOfTransactions.get(itemSelected);

        intent.putExtra("transaction", clickedItem);
        intent.putExtra("editing", true);
        context.startActivity(intent);
    }

}


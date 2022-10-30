package com.example.rentalmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalmanager.R;
import com.example.rentalmanager.db.Categories;
import com.example.rentalmanager.db.RecyclerViewInterface;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {


    private final RecyclerViewInterface recyclerViewInterface;
        // new code for second textView
        Context context2;
        ArrayList<Categories> list2;


        // new code for second textView
    public CategoryAdapter(Context context, ArrayList<Categories> list2,RecyclerViewInterface recyclerViewInterface){
        this.context2 = context;
        this.list2 = list2;
        this.recyclerViewInterface = recyclerViewInterface;
    }

        @NonNull
        @Override
        public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            // new code for second textView
        LayoutInflater inflater2 = LayoutInflater.from(context2);

        // HERE!!!!!!!!!!!!!!!!!----------------
        View view = inflater2.inflate(R.layout.recycler_view_row_catgories, parent, false);

        return new CategoryAdapter.ViewHolder(view, recyclerViewInterface);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {

            // new code for second textView
            // HERE!!!!!!!!!!!!!!!!!----------------
        holder.categoryTitle.setText(list2.get(position).getCategory());
        holder.arrow.setImageResource(list2.get(position).getImage());

        }

        @Override
        public int getItemCount() {
            // new code for second textView
        return list2.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            // new code for second textView
            TextView categoryTitle;
            ImageView arrow;
            public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
                super(itemView);


                // new code for second textView
                arrow = itemView.findViewById(R.id.imageView_arrow);
                categoryTitle = itemView.findViewById(R.id.textView_category);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(recyclerViewInterface != null) {
                            int pos = getAdapterPosition();

                            if (pos != RecyclerView.NO_POSITION) {
                                recyclerViewInterface.onItemClick(pos);
                            }
                        }
                    }
                });
            }
        }
}

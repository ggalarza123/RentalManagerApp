package com.example.rentalmanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalmanager.R;
import com.example.rentalmanager.db.Property;


import java.util.List;

public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {
    Context context;
    List<Property> list;

    public PropertyAdapter(Context context, List<Property> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PropertyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row_properties, parent, false);
        return new PropertyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PropertyAdapter.ViewHolder holder, int position) {
        holder.propertyAddress.setText(list.get(position).getAddress());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView propertyAddress;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            propertyAddress = itemView.findViewById(R.id.textView_property_address);
        }
    }
}

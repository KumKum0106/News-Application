package com.example.newsapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class categoryRVAdapter extends RecyclerView.Adapter<categoryRVAdapter.ViewHolder> {
    private ArrayList<CategoryRVModal> categoryRVModals;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    public categoryRVAdapter(ArrayList<CategoryRVModal> categoryRVModals, Context context, CategoryClickInterface categoryClickInterface) {
        this.categoryRVModals = categoryRVModals;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @NonNull
    @Override
    public categoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_rv_item, parent, false);
        return new categoryRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull categoryRVAdapter.ViewHolder holder, int position) {
        CategoryRVModal categoryRVModal = categoryRVModals.get(position);
        holder.categoryTV.setText(categoryRVModal.getCategory());
        Picasso.get().load(categoryRVModal.getCategoryImageUrl()).into(holder.categoryIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClickInterface.onCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryRVModals.size();
    }

    public interface CategoryClickInterface{
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView categoryTV;
        private ImageView categoryIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTV = itemView.findViewById(R.id.idTVCategory);
            categoryIV = itemView.findViewById(R.id.idIVCategory);
        }
    }
}

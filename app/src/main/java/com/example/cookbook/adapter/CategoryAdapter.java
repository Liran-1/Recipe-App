package com.example.cookbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookbook.R;
import com.example.cookbook.callbacks.CategoryCallback;
import com.example.cookbook.callbacks.IngredientRemoveCallback;
import com.example.cookbook.models.Category;
import com.example.cookbook.models.Ingredient;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private Context context;
    private ArrayList<Category> categories;
    private CategoryCallback categoryCallback;

    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        this.context    = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_recyclerview, parent, false);
        CategoryViewHolder categoryViewHolder = new CategoryAdapter.CategoryViewHolder(view);

        return categoryViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        Category category = categories.get(position);

        holder.category_TXT_name.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private Category getItem(int position) {
        return categories.get(position);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        public TextView category_TXT_name;
        public ImageView category_IMG_category;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            category_TXT_name = (TextView) itemView.findViewById(R.id.category_TXT_name);
            category_IMG_category = (ImageView) itemView.findViewById(R.id.category_IMG_category);

            itemView.setOnClickListener(v -> categoryCallback
                    .categoryClicked(getItem(getAdapterPosition()),getAdapterPosition()));
        }

    }

    public void setCategoryCallback(CategoryCallback categoryCallback) {
        this.categoryCallback = categoryCallback;
    }
}

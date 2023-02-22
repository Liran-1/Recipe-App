package com.example.cookbook.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookbook.R;
import com.example.cookbook.callbacks.CategoryCallback;
import com.example.cookbook.callbacks.CategoryUpdatedCallback;
import com.example.cookbook.models.Category;
import com.google.android.material.textview.MaterialTextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    private Context context;
    private ArrayList<Category> categories;
    private CategoryCallback categoryCallback;
    private CategoryUpdatedCallback categoryUpdatedCallback;

    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        this.context    = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);

        return categoryViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = getItem(position);

        if(category != null) {
            holder.category_TXT_name.setText(category.getName());
//            Bitmap bitmap = getBitmapFromURL(category.getImage());
//            Glide.with(holder.itemView.getContext()).load(category.getImage())
//                    .into(holder.category_IMG_category);
            Glide
                    .with(this.context)
                    .load(category.getImage())
                    .centerCrop()
                    .placeholder(R.drawable.bread)
                    .into(holder.category_IMG_category);
//            holder.category_IMG_category.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return categories == null ? 0 : categories.size();
    }

    private Category getItem(int position) {
        return categories.get(position);
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            Log.e("src",src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }


public class CategoryViewHolder extends RecyclerView.ViewHolder{

        public MaterialTextView category_TXT_name;
        public ImageView category_IMG_category;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            category_TXT_name = itemView.findViewById(R.id.category_LBL_name);
            category_IMG_category = itemView.findViewById(R.id.category_IMG_category);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryCallback
                            .categoryClicked(getItem(getAbsoluteAdapterPosition()), getAbsoluteAdapterPosition());
                }
            });
        }

    }

    public void setCategoryCallback(CategoryCallback categoryCallback) {
        this.categoryCallback = categoryCallback;
    }

    public void setCategoryUpdatedCallback(CategoryUpdatedCallback categoryUpdatedCallback){
        this.categoryUpdatedCallback = categoryUpdatedCallback;
    }
}

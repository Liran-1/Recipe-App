package com.example.cookbook.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookbook.R;
import com.example.cookbook.callbacks.CategoryCallback;
import com.example.cookbook.models.Category;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FBCategoryAdapter extends FirebaseRecyclerAdapter<Category,
        FBCategoryAdapter.FBCategoryViewholder> {

    private CategoryCallback categoryCallback;

    public FBCategoryAdapter(@NonNull FirebaseRecyclerOptions<Category> options) {
        super(options);
    }

    @NonNull
    @Override
    public FBCategoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_recyclerview, parent, false);

        return new FBCategoryAdapter.FBCategoryViewholder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull FBCategoryViewholder holder, int position,
                                    @NonNull Category category) {

        holder.category_TXT_name.setText(category.getName());
        Bitmap bitmap = getBitmapFromURL(category.getImage());
        holder.category_IMG_category.setImageBitmap(bitmap);

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



    public void setCategoryCallback(CategoryCallback categoryCallback) {
        this.categoryCallback = categoryCallback;
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class FBCategoryViewholder extends RecyclerView.ViewHolder {
        public TextView category_TXT_name;
        public ImageView category_IMG_category;

        public FBCategoryViewholder(@NonNull View itemView)
        {
            super(itemView);

            category_TXT_name = (TextView) itemView.findViewById(R.id.category_TXT_name);
            category_IMG_category = (ImageView) itemView.findViewById(R.id.category_IMG_category);
        }
    }

}

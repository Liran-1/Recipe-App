package com.example.cookbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookbook.R;
import com.example.cookbook.callbacks.RecipeCallback;
import com.example.cookbook.models.Recipe;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>{

    private Context context;
    private ArrayList<Recipe> recipes;
    private RecipeCallback recipeCallback;
    private FavoriteClicked onFavoriteClicked;

    public interface FavoriteClicked {
        void onFavoriteClicked(int position);
    }

    public void setOnFavoriteClicked(FavoriteClicked onFavoriteClicked) {
        this.onFavoriteClicked = onFavoriteClicked;
    }

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        RecipeAdapter.RecipeViewHolder recipeViewHolder = new RecipeAdapter.RecipeViewHolder(view/*, onFavoriteClicked*/);

        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.RecipeViewHolder holder, int position) {
        Recipe recipe = getItem(position);

        holder.recipe_LBL_name.setText(recipe.getName());
        holder.recipe_LBL_description.setText(recipe.getDescription());
//        holder.recipe_LBL_likes.setText(recipe.getLikes());
//        holder.recipe_LBL_username.setText(recipe.getName());

    }

    @Override
    public int getItemCount() {
        return recipes == null ? 0 : recipes.size();
    }

    private Recipe getItem(int position) {
        return recipes.get(position);
    }

    public void setRecipeCallback(RecipeCallback recipeCallback) {
        this.recipeCallback = recipeCallback;
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        public TextView recipe_LBL_name, recipe_LBL_username, /*recipe_LBL_likes,*/ recipe_LBL_description;
        public ShapeableImageView recipe_IMG_result/*, recipe_IMG_favorite*/;

        public RecipeViewHolder(@NonNull View itemView/*, FavoriteClicked onFavoriteClicked*/) {
            super(itemView);

            recipe_LBL_name         = itemView.findViewById(R.id.recipeRV_LBL_name);
            recipe_LBL_username     = itemView.findViewById(R.id.recipeRV_LBL_username);
//            recipe_LBL_likes        = itemView.findViewById(R.id.recipe_LBL_likes);
            recipe_LBL_description  = itemView.findViewById(R.id.recipeRV_LBL_description);
            recipe_IMG_result       = itemView.findViewById(R.id.recipe_IMG_result);
//            recipe_IMG_favorite     = itemView.findViewById(R.id.recipe_IMG_favorite);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    recipeCallback
                            .recipeClicked(getItem(getAbsoluteAdapterPosition()), getAbsoluteAdapterPosition());
                }
            });

//            recipe_IMG_favorite.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    onFavoriteClicked.onFavoriteClicked(getAbsoluteAdapterPosition());
//                }
//            });

        }
    }
}

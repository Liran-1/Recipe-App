package com.example.cookbook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookbook.R;
import com.example.cookbook.RecipeCreationActivity;
import com.example.cookbook.callbacks.IngredientRemoveCallback;
import com.example.cookbook.models.Ingredient;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private Context context;
    private ArrayList<Ingredient> ingredients;
    private IngredientRemoveCallback ingredientRemoveCallback;

    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item, parent, false);
        return new IngredientViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);

        holder.ingredient_TXT_ingredientName.setText(ingredient.getName());
        holder.ingredient_TXT_ingredientAmount.setText(ingredient.getAmount());
        holder.ingredient_TXT_ingredientUnit.setText(ingredient.getUnits());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    private Ingredient getItem(int position) {
        return ingredients.get(position);
    }

    public void setIngredientRemoveCallback(IngredientRemoveCallback ingredientRemoveCallback) {
        this.ingredientRemoveCallback = ingredientRemoveCallback;
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        public TextView ingredient_TXT_ingredientName;
        public TextView ingredient_TXT_ingredientAmount;
        public TextView ingredient_TXT_ingredientUnit;
        public ImageButton ingredient_IBTN_remove;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);

            ingredient_TXT_ingredientName   = (TextView) itemView
                    .findViewById(R.id.ingredient_TXT_ingredientName);
            ingredient_TXT_ingredientAmount = (TextView) itemView
                    .findViewById(R.id.ingredient_TXT_ingredientAmount);
            ingredient_TXT_ingredientUnit   = (TextView) itemView
                    .findViewById(R.id.ingredient_TXT_ingredientUnit);
            ingredient_IBTN_remove          = (ImageButton) itemView
                    .findViewById(R.id.ingredient_IBTN_remove);

            ingredient_IBTN_remove.setOnClickListener(view ->
                    ingredientRemoveCallback.ingredientDeleteClicked(getItem(getAdapterPosition()),
                    getAdapterPosition()));
        }
    }

}

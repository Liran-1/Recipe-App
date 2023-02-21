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
import com.example.cookbook.callbacks.IngredientRemoveCallback;
import com.example.cookbook.models.Ingredient;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private Context context;
    private ArrayList<Ingredient> ingredients;
    private IngredientRemoveClicked onRemoveClicked;

    public interface IngredientRemoveClicked {
        void onRemoveClicked(int position);
    }

    public void setOnRemoveClicked(IngredientRemoveClicked onRemoveClicked) {
        this.onRemoveClicked = onRemoveClicked;
    }

    public IngredientAdapter(Context context, ArrayList<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_item, parent, false);
        IngredientViewHolder ingredientViewHolder = new IngredientViewHolder(view, onRemoveClicked);

        return ingredientViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = getItem(position);

        holder.ingredient_TXT_ingredientName.setText(ingredient.getName());
        holder.ingredient_TXT_ingredientAmount.setText(ingredient.getAmount());
        holder.ingredient_TXT_ingredientUnit.setText(ingredient.getUnits());

//        holder.ingredient_IBTN_remove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ingredients.remove(ingredient);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return ingredients == null ? 0 : ingredients.size();
    }

    private Ingredient getItem(int position) {
        return ingredients.get(position);
    }

    public void addItem(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        public TextView ingredient_TXT_ingredientName;
        public TextView ingredient_TXT_ingredientAmount;
        public TextView ingredient_TXT_ingredientUnit;
        public ImageButton ingredient_IBTN_remove;

        public IngredientViewHolder(@NonNull View itemView, IngredientRemoveClicked ingredientRemovedClicked) {
            super(itemView);

            ingredient_TXT_ingredientName = (TextView) itemView
                    .findViewById(R.id.ingredient_TXT_ingredientName);
            ingredient_TXT_ingredientAmount = (TextView) itemView
                    .findViewById(R.id.ingredient_TXT_ingredientAmount);
            ingredient_TXT_ingredientUnit = (TextView) itemView
                    .findViewById(R.id.ingredient_TXT_ingredientUnit);
            ingredient_IBTN_remove = (ImageButton) itemView
                    .findViewById(R.id.ingredient_IBTN_remove);

            ingredient_IBTN_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ingredientRemovedClicked.onRemoveClicked(getAbsoluteAdapterPosition());
                }
            });
        }
    }

}

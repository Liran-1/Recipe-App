package com.example.cookbook.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookbook.R;
import com.example.cookbook.adapter.IngredientAdapter;
import com.example.cookbook.callbacks.IngredientAddedCallback;
import com.example.cookbook.callbacks.IngredientRemoveCallback;
import com.example.cookbook.models.Ingredient;
import com.example.cookbook.utils.RecipeSP;

import java.util.ArrayList;

public class IngredientFragment extends Fragment {

    private RecyclerView recipeCreation_RV_ingredients;
    private IngredientRemoveCallback ingredientRemoveCallback;
    private IngredientAddedCallback ingredientAddedCallback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_recipe_fragment, container, false);
        findViews(view);
        initViews();

        return view;
    }

    private void findViews(View view) {
        recipeCreation_RV_ingredients = view.findViewById(R.id.recipeCreation_RV_ingredients);
    }

    private void initViews() {
        ArrayList ingredients = RecipeSP.getInstance().getIngredients();
        IngredientAdapter ingredientAdapter = new IngredientAdapter(getContext(), ingredients);
        recipeCreation_RV_ingredients.setHasFixedSize(true);
        recipeCreation_RV_ingredients.setLayoutManager(new LinearLayoutManager(getContext()));
        recipeCreation_RV_ingredients.setAdapter(ingredientAdapter);
//        ingredientAdapter.setIngredientRemoveCallback(ingredientRemoveCallback);
    }

    public void setIngredientRemoveCallback(IngredientRemoveCallback ingredientRemoveCallback) {
        this.ingredientRemoveCallback = ingredientRemoveCallback;
    }
    public void setIngredientAddedCallback(IngredientAddedCallback ingredientAddedCallback) {
        this.ingredientAddedCallback = ingredientAddedCallback;
    }

    public void addItem(Ingredient ingredient) {
        IngredientAdapter ingredientAdapter = (IngredientAdapter) recipeCreation_RV_ingredients.getAdapter();
        ingredientAdapter.addItem(ingredient);
        ingredientAdapter.notifyItemInserted(ingredientAdapter.getItemCount() - 1);
    }


}

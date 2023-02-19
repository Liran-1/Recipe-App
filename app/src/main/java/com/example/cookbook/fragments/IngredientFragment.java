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
import com.example.cookbook.callbacks.IngredientRemoveCallback;
import com.example.cookbook.models.Ingredient;
import com.google.firebase.database.DatabaseReference;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class IngredientFragment extends Fragment {

    private RecyclerView ingredientRecycledView;
    private IngredientRemoveCallback ingredientRemoveCallback;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredient_item_recyclerview, container, false);
        findViews(view);
        initViews();

        return view;
    }

    private void findViews(View view) {
        ingredientRecycledView = view.findViewById(R.id.recipeCreation_RV_ingredients);
    }

    private void initViews() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        IngredientAdapter ingredientAdapter = new IngredientAdapter(getContext(), ingredients);
        ingredientRecycledView.setHasFixedSize(true);
        ingredientRecycledView.setLayoutManager(new LinearLayoutManager(getContext()));
        ingredientRecycledView.setAdapter(ingredientAdapter);
        ingredientAdapter.setIngredientRemoveCallback(ingredientRemoveCallback);
    }

    public void setCallback(IngredientRemoveCallback ingredientRemoveCallback) {
        this.ingredientRemoveCallback = ingredientRemoveCallback;
    }
}

package com.example.cookbook;

import android.widget.Button;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cookbook.models.Ingredient;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class RecipeManager {

    private ArrayList<Ingredient> ingredients;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    public RecipeManager() {
        ingredients = new ArrayList<>();
    }
}

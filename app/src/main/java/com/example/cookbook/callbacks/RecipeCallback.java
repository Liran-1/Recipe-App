package com.example.cookbook.callbacks;

import com.example.cookbook.models.Recipe;

public interface RecipeCallback {
    void recipeClicked(Recipe recipe, int position);
}

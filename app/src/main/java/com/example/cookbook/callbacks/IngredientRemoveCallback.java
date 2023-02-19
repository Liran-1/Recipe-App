package com.example.cookbook.callbacks;

import com.example.cookbook.models.Ingredient;

public interface IngredientRemoveCallback {

    void ingredientDeleteClicked(Ingredient ingredient, int position);

}

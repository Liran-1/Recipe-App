package com.example.cookbook.callbacks;

import com.example.cookbook.models.Recipe;

public interface FavoriteCallback {

    void favoriteClicked(Recipe recipe, int position);

}

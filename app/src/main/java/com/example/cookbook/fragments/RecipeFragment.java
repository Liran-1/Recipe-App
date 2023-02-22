package com.example.cookbook.fragments;

import static com.example.cookbook.activities.MainActivity.navigationView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.cookbook.R;
import com.example.cookbook.callbacks.OnBackPressedCallback;
import com.example.cookbook.models.Recipe;
import com.example.cookbook.utils.RecipeSP;
import com.google.android.material.textview.MaterialTextView;

public class RecipeFragment extends Fragment implements OnBackPressedCallback {


    private MaterialTextView recipe_LBL_name, recipe_LBL_description, recipe_LBL_ingredients,
            recipe_LBL_instructions;
    private Recipe recipe;
    private RecipeRVFragment recipeRVFragment;                  // for return
    public static OnBackPressedCallback onBackPressedCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);

        recipe = RecipeSP.getInstance().getRecipe();

        findViews(view);
        initViews();


        return view;
    }

    private void initViews() {
        recipe_LBL_name.setText(recipe.getName());
        recipe_LBL_description.setText(recipe.getDescription());
        recipe_LBL_ingredients.setText(recipe.getIngredients().toString());
        recipe_LBL_instructions.setText(recipe.getInstructions());
    }

    private void findViews(View view) {
        recipe_LBL_name = view.findViewById(R.id.recipe_LBL_name);
        recipe_LBL_description = view.findViewById(R.id.recipe_LBL_description);
        recipe_LBL_ingredients = view.findViewById(R.id.recipe_LBL_ingredients);
        recipe_LBL_instructions = view.findViewById(R.id.recipe_LBL_instructions);
    }


    public void setRecipeRVFragment(RecipeRVFragment recipeRVFragment) {
        this.recipeRVFragment = recipeRVFragment;
    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_FRG_fragment, recipeRVFragment).commit();
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onResume() {
        super.onResume();
        onBackPressedCallback = this;
    }

    @Override
    public void onPause() {
        super.onPause();
        onBackPressedCallback = null;
    }
}

package com.example.cookbook.fragments;

import static com.example.cookbook.activities.MainActivity.navigationView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.cookbook.R;
import com.example.cookbook.callbacks.OnBackPressedCallback;

public class RecipeFragment extends Fragment implements OnBackPressedCallback {

    private RecipeRVFragment recipeRVFragment;                  // for return
    public static OnBackPressedCallback onBackPressedCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }


    public void setRecipeRVFragment(RecipeRVFragment recipeRVFragment) {
        this.recipeRVFragment = recipeRVFragment;
    }

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_FRG_fragment, new CategoryRVFragment()).commit();
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

package com.example.cookbook.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookbook.R;
import com.example.cookbook.adapter.RecipeAdapter;
import com.example.cookbook.callbacks.RecipeCallback;
import com.example.cookbook.models.Recipe;
import com.example.cookbook.utils.RecipeSP;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecipeFragment extends Fragment {


    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = mDatabase.getReference();
//    private ShapeableImageView recipe_IMG_favorite;
    private RecyclerView home_RV_recipes;
    private String categoryChosen = RecipeSP.getInstance().getString("Category", "Lunch");
    private ArrayList<Recipe> recipes;
    private RecipeAdapter recipeAdapter;
    private RecipeCallback recipeCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.category_recyclerview, container, false);

        getRecipes();

        findViews(view);
        initViews();

        return view;
    }

    private void findViews(View view) {
//        recipe_IMG_favorite = view.findViewById(R.id.recipe_IMG_favorite);
        home_RV_recipes = view.findViewById(R.id.home_RV_recipes);
    }

    private void initViews() {
        recipeAdapter = new RecipeAdapter(getContext(), recipes);
        home_RV_recipes.setHasFixedSize(true);
        home_RV_recipes.setLayoutManager(new LinearLayoutManager(getContext()));
        home_RV_recipes.setAdapter(recipeAdapter);

//        recipeAdapter.setOnFavoriteClicked(new RecipeAdapter.FavoriteClicked() {
//            @Override
//            public void onFavoriteClicked(int position) {
//                user.changeFavorite(home_RV_recipes.getAdapter().getItemId(position));
//                home_RV_recipes.getAdapter().notifyItemChanged(position);
//            }
//        });
        recipeAdapter.setRecipeCallback(recipeCallback);
    }

//    private void changeFavoriteState() {
//        if()
//    }

    private void getRecipes() {
        dbRef.child("Category").child("Category").child(categoryChosen)
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            recipes = (ArrayList) task.getResult().getValue();
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        }
                    }
                });

    }




}

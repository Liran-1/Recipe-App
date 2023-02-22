package com.example.cookbook.managers;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cookbook.R;
import com.example.cookbook.adapter.RecipeAdapter;
import com.example.cookbook.models.Category;
import com.example.cookbook.models.Ingredient;
import com.example.cookbook.models.Recipe;
import com.example.cookbook.utils.RecipeSP;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.util.ArrayList;
import java.util.Map;

public class DataManager {

    private ArrayList<Category> categories;

    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = mDatabase.getReference();
    private int categoryChosenNum;
    private String categoryChosenNumParsed;

//    public ArrayList<Recipe> getRecipesFromDB(View view) {
//        categoryChosenNum = RecipeSP.getInstance().getInt(RecipeSP.CATEGORY_CHOSEN_NUM, 0);
//        categoryChosenNumParsed = categoryChosenNum < 10 ? "0" + categoryChosenNum : String.valueOf(categoryChosenNum);
//
//        ArrayList<Recipe> recipes = new ArrayList<>();
//        dbRef.child("Category").child(categoryChosenNumParsed).child("Recipes").get().addOnCompleteListener(task -> {
//            if (!task.isSuccessful()) {
//                Log.e("firebase", "Error getting data", task.getException());
//            } else {
//                Log.d("firebase", String.valueOf(task.getResult().getValue()));
//                Log.d("firebase", String.valueOf(task.getResult().getChildren()));
//
////                DataSnapshot dataSnapshot = task.getResult().child("Recipes");
//                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
//                    Log.d("resultsIs", String.valueOf(dataSnapshot.getValue()));
//                    Log.d("resultsIs", String.valueOf(dataSnapshot.getKey()));
//                    String name = String.valueOf(dataSnapshot.child("name").getValue());
//                    String description = String.valueOf(dataSnapshot.child("description").getValue());
//                    String image = String.valueOf(dataSnapshot.child("image").getValue());
//                    String instructions = String.valueOf(dataSnapshot.child("instructions").getValue());
//                    String categoryId = categoryChosenNumParsed;
//
//                    GenericTypeIndicator<Map<String, Ingredient>> genericTypeIndicator =
//                            new GenericTypeIndicator<Map<String, Ingredient>>() {
//                            };
//                    Map<String, Ingredient> ingredientsMap = dataSnapshot.child("ingredients")
//                            .getValue(genericTypeIndicator);
//
//                    ArrayList<Ingredient> ingredients = new ArrayList<>();
//                    if (ingredientsMap != null) {
////                        ingredients = ingredientsMap.values();
//                        ingredients = new ArrayList<>(ingredientsMap.values());
//                    }
////                    int likes = (int) dataSnapshot.child("Image").getValue();
//                    int likes = 0;
//
//
//                    Recipe recipe = new Recipe(name, description, image, instructions, categoryId, likes, ingredients);
//                    recipes.add(recipe);
//
//                }
//            }
//            ProgressBar home_PB_progressbar = view.findViewById(R.id.home_PB_progressbar);
//            RecyclerView home_RV_recipes = view.findViewById(R.id.home_RV_recipes);
//
//            RecipeAdapter recipeAdapter = new RecipeAdapter()
//            home_PB_progressbar.setVisibility(View.GONE);
//            home_RV_recipes.setAdapter(recipeAdapter);
//            recipeAdapter.setRecipeCallback(recipeCallback);
//        });
//        return recipes;
//    }

}

package com.example.cookbook.fragments;

import static com.example.cookbook.activities.MainActivity.navigationView;

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
import com.example.cookbook.callbacks.OnBackPressedCallback;
import com.example.cookbook.callbacks.RecipeCallback;
import com.example.cookbook.models.Ingredient;
import com.example.cookbook.models.Recipe;
import com.example.cookbook.utils.RecipeSP;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class RecipeRVFragment extends Fragment implements OnBackPressedCallback {


    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = mDatabase.getReference();
    //    private ShapeableImageView recipe_IMG_favorite;
    private CategoryRVFragment categoryRVFragment;                  // for return
    private RecyclerView home_RV_recipes;
    private String categoryChosen = RecipeSP.getInstance().getString("Category", "Lunch");
    private ArrayList<Recipe> recipes;
    private RecipeAdapter recipeAdapter;
    public static OnBackPressedCallback onBackPressedCallback;
    private RecipeCallback recipeCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recyclerview_recipe, container, false);

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

    private ArrayList<Recipe> getRecipes() {
        dbRef.child("Category").child(categoryChosen).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    recipes = (ArrayList) task.getResult().getValue();
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));

                    for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {

                        String name = String.valueOf(dataSnapshot.child("Name").getValue());
                        String description = String.valueOf(dataSnapshot.child("Description").getValue());
                        String image = String.valueOf(dataSnapshot.child("Image").getValue());
                        String instructions = String.valueOf(dataSnapshot.child("Instructions").getValue());
                        String categoryId = String.valueOf(dataSnapshot.child("CategoryId").getValue());
                        GenericTypeIndicator<Map<String, Ingredient>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Ingredient>>() {
                        };
                        Map<String, Ingredient> ingredientsMap = dataSnapshot.child("Ingredients")
                                .getValue(genericTypeIndicator);

                        ArrayList<Ingredient> ingredients = new ArrayList<>();
                        if (ingredientsMap != null)
//                        ingredients = ingredientsMap.values();
                            ingredients = new ArrayList<>(ingredientsMap.values());
//                    int likes = (int) dataSnapshot.child("Image").getValue();
                        int likes = 0;
                        //                        String menuId = String.valueOf(dataSnapshot.getKey());
                        Log.d("name", name);
                        Log.d("image", image);
                        Recipe recipe = new Recipe(name, description, image, instructions, categoryId, likes, ingredients);
                        recipes.add(recipe);

//                    Log.d("counted", String.valueOf(categories.size()));

                    }
                }
            };
        });
                return recipes;
    }

        public void setCategoryRVFragment (CategoryRVFragment categoryRVFragment){
            this.categoryRVFragment = categoryRVFragment;
        }

        @Override
        public void onBackPressed () {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_FRG_fragment, categoryRVFragment).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        @Override
        public void onResume () {
            super.onResume();
            onBackPressedCallback = this;
        }

        @Override
        public void onPause () {
            super.onPause();
            onBackPressedCallback = null;
        }


        public void setCallback (RecipeCallback recipeCallback){
            this.recipeCallback = recipeCallback;
        }
    }

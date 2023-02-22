package com.example.cookbook.fragments;

import static android.app.Activity.RESULT_OK;

import static com.example.cookbook.activities.MainActivity.navigationView;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookbook.R;
import com.example.cookbook.adapter.IngredientAdapter;
import com.example.cookbook.callbacks.OnBackPressedCallback;
import com.example.cookbook.models.Ingredient;
import com.example.cookbook.models.Recipe;
import com.example.cookbook.utils.RecipeSP;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateRecipeFragment extends Fragment implements OnBackPressedCallback {
    private TextInputEditText recipeCreation_ETXT_name, recipeCreation_ETXT_description, ingredient_ETXT_ingredientName,
            ingredient_ETXT_ingredientAmount, recipeCreation_ETXT_instructions;
    private Spinner ingredient_SPNR_ingredientUnit, recipeCreation_SPNR_categories;
    private MaterialButton ingredient_BTN_addIngredient, recipeCreation_BTN_addRecipe;
    private ImageView recipeCreation_IMG_picture;
    private RecyclerView recipeCreation_RV_ingredients;
    private IngredientAdapter ingredientAdapter;
    private ArrayList<Ingredient> ingredients;
    private Map<String, Ingredient> ingredientMap = new HashMap<>();
    private ArrayList<String> categoriesName;
    private String name, description, instructions, category, image;
    private Uri cam_uri;

    public static OnBackPressedCallback onBackPressedCallback;

    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = mDatabase.getReference();
    private CategoryRVFragment categoryRVFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_recipe, container, false);

        categoriesName = getCategoriesName();
        if (ingredients == null)
            ingredients = new ArrayList<>();

        findViews(view);
        initViews();

        return view;
    }

    private ArrayList<String> getCategoriesName() {
        ArrayList<String> categories = new ArrayList<>();
        String[] category = getResources().getStringArray(R.array.Categories);
        categories.add(category[0]);
        dbRef.child("Category").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                //                    categories = (ArrayList) task.getResult().getValue();
                Log.d("firebase", String.valueOf(task.getResult().getKey()));
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    String name = dataSnapshot.getKey().replace('_', ' ');
                    Log.d("name", name);
                    categories.add(name);
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categoriesName);
            recipeCreation_SPNR_categories.setAdapter(adapter);

        });


        return categories;

    }

    private void findViews(View view) {
        recipeCreation_ETXT_name = view.findViewById(R.id.recipeCreation_ETXT_name);
        recipeCreation_ETXT_description = view.findViewById(R.id.recipeCreation_ETXT_description);
        recipeCreation_IMG_picture = view.findViewById(R.id.recipeCreation_IMG_picture);

        ingredient_ETXT_ingredientName = view.findViewById(R.id.ingredient_ETXT_ingredientName);
        ingredient_ETXT_ingredientAmount = view.findViewById(R.id.ingredient_ETXT_ingredientAmount);
        ingredient_SPNR_ingredientUnit = view.findViewById(R.id.ingredient_SPNR_ingredientUnit);
        ingredient_BTN_addIngredient = view.findViewById(R.id.ingredient_BTN_addIngredient);

        recipeCreation_RV_ingredients = view.findViewById(R.id.recipeCreation_RV_ingredients);

        recipeCreation_ETXT_instructions = view.findViewById(R.id.recipeCreation_ETXT_instructions);
        recipeCreation_SPNR_categories = view.findViewById(R.id.recipeCreation_SPNR_categories);
        recipeCreation_BTN_addRecipe = view.findViewById(R.id.recipeCreation_BTN_addRecipe);
    }

    private void initViews() {
        ingredient_BTN_addIngredient.setOnClickListener(view -> ingredientAddClicked());
//        setCallback(new IngredientRemoveCallback() {
//            @Override
//            public void userHighScoreClicked(Ingredient ingredient, int pos) {
//                ingredientRemoveCallback.removeIngredient(ingredient);
//            }
//        });

//        ingredients = RecipeSP.getInstance().getIngredients(RecipeSP.INGREDIENTS_FILLED);

        recipeCreation_IMG_picture.setOnClickListener(view -> openCamera());

        recipeCreation_BTN_addRecipe.setOnClickListener(view -> addRecipe());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item_my, categoriesName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (String s : categoriesName) {
            adapter.add(s);
        }
        recipeCreation_SPNR_categories.setAdapter(adapter);
        recipeCreation_SPNR_categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//                String chosenCategory = adapterView.getItemAtPosition(position).toString();
                recipeCreation_SPNR_categories.setSelection(position);
                Log.d("SELECTED", recipeCreation_SPNR_categories.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        ingredientAdapter = new IngredientAdapter(getContext(), ingredients);
        recipeCreation_RV_ingredients.setHasFixedSize(true);
        recipeCreation_RV_ingredients.setLayoutManager(new LinearLayoutManager(getContext()));
        recipeCreation_RV_ingredients.setAdapter(ingredientAdapter);
//        ingredientAdapter.setIngredientRemoveCallback(ingredientRemoveCallback);
        ingredientAdapter.setOnRemoveClicked(new IngredientAdapter.IngredientRemoveClicked() {
            @Override
            public void onRemoveClicked(int position) {
                ingredients.remove(position);
                ingredientAdapter.notifyItemRemoved(position);
            }
        });


//        ingredientAdapter.setIngredientRemoveCallback(ingredientRemoveCallback);
//        ingredientAdapter.setIngredientAddedCallback(ingredientAddedCallback);
    }

    private boolean isInputFull() {
        String[] category = getResources().getStringArray(R.array.Categories);
        return !TextUtils.isEmpty(recipeCreation_ETXT_name.getText()) &&
                !TextUtils.isEmpty(recipeCreation_ETXT_description.getText()) &&
                !TextUtils.isEmpty(recipeCreation_ETXT_instructions.getText()) &&
                (ingredients != null) &&
                !recipeCreation_SPNR_categories.getSelectedItem().toString().equals(category[0]);
    }

    private void saveInput() {
        if (ingredients != null)
            RecipeSP.getInstance().putIngredients(RecipeSP.CREATE_INGREDIENTS, ingredients);
        if (!TextUtils.isEmpty(recipeCreation_ETXT_name.getText()))
            RecipeSP.getInstance().putString(RecipeSP.CREATE_NAME, recipeCreation_ETXT_name.getText().toString());
        if (!TextUtils.isEmpty(recipeCreation_ETXT_description.getText()))
            RecipeSP.getInstance().putString(RecipeSP.CREATE_DESCRIPTION, recipeCreation_ETXT_description.getText().toString());
        if (!TextUtils.isEmpty(recipeCreation_ETXT_instructions.getText()))
            RecipeSP.getInstance().putString(RecipeSP.CREATE_INSTRUCTIONS, recipeCreation_ETXT_instructions.getText().toString());
    }

    private void clearInput() {
//        RecipeSP.getInstance().putIngredients(RecipeSP.CREATE_INGREDIENTS, new ArrayList<>());
//        RecipeSP.getInstance().putString(RecipeSP.CREATE_NAME, "");
//        RecipeSP.getInstance().putString(RecipeSP.CREATE_DESCRIPTION, "");
//        RecipeSP.getInstance().putString(RecipeSP.CREATE_INSTRUCTIONS, "");
//
//        ingredientAdapter = new IngredientAdapter(getContext(), ingredients);
//        recipeCreation_RV_ingredients.setAdapter(ingredientAdapter);

    }

    /////CAMERA/////


    ActivityResultLauncher<Intent> startCamera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    recipeCreation_IMG_picture.setImageURI(cam_uri);
                }
            }
    );

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From Camera");
        cam_uri = getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cam_uri);

        //startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE); // OLD WAY
        startCamera.launch(cameraIntent); // VERY NEW WAY


//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            registerForActivityResult()
//            startActivityForResult(intent, 101);
//        } else {
//            String msg = "Did not find an app to support this action";
//            toaster = Toast
//                    .makeText(RecipeCreationActivity.this, msg, Toast.LENGTH_SHORT);
//            toaster.show();
//        }
    }

    //////////////////

    public void ingredientAddClicked() {
        String[] Units = getResources().getStringArray(R.array.Amounts);

        if (TextUtils.isEmpty(ingredient_ETXT_ingredientName.getText()) ||
                TextUtils.isEmpty(ingredient_ETXT_ingredientAmount.getText()) ||
                ingredient_SPNR_ingredientUnit.getSelectedItem().toString().equals(Units[0])) {
            return;
        } else {
            String name = ingredient_ETXT_ingredientName.getText().toString();
            double amount = Double.parseDouble(ingredient_ETXT_ingredientAmount.getText().toString());
            String unit = ingredient_SPNR_ingredientUnit.getSelectedItem().toString();

            Ingredient ingredient = new Ingredient(name, amount, unit);
            ingredient_ETXT_ingredientName.getText().clear();
            ingredient_ETXT_ingredientAmount.getText().clear();
            ingredient_SPNR_ingredientUnit.setSelection(0);

            ingredients.add(ingredient);
            ingredientMap.put("0" + ingredients.size(), ingredient);

//            RecipeSP.getInstance().setIngredients(ingredients);
            ingredientAdapter = new IngredientAdapter(getContext(), ingredients);
            ingredientAdapter.notifyItemInserted(ingredients.size() - 1);
            recipeCreation_RV_ingredients.setAdapter(ingredientAdapter);
            ingredientAdapter.setOnRemoveClicked(new IngredientAdapter.IngredientRemoveClicked() {
                @Override
                public void onRemoveClicked(int position) {
                    ingredients.remove(position);
                    ingredientAdapter.notifyItemRemoved(position);
                }
            });
        }

    }

    private void addRecipe() {
        if (!isInputFull())
            return;

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        name = recipeCreation_ETXT_name.getText().toString();
        description = recipeCreation_ETXT_description.getText().toString();
        instructions = recipeCreation_ETXT_instructions.getText().toString();
        category = recipeCreation_SPNR_categories.getSelectedItem().toString();
        image = recipeCreation_IMG_picture.toString();
        int likes = 0;

//        recipeCreation_ETXT_name.getText().clear();
//        recipeCreation_ETXT_description.getText().clear();
//        recipeCreation_ETXT_instructions.getText().clear();
//        recipeCreation_SPNR_categories.setSelection(0);
//        recipeCreation_IMG_picture.setImageResource(R.drawable.open_camera);
//        ingredients = new ArrayList<>();

//        clearInput();

        category = category.replace(' ', '_');

        Recipe recipe = new Recipe(name, description, image, instructions, likes, ingredientMap);

        writeNewRecipeToDB(category, recipe);


//        mDatabase.child("Category").child()
    }

    private void writeNewRecipeToDB(String category, Recipe recipe) {
        ArrayList<Integer> numOfRecipes = new ArrayList<>();
        dbRef.child("Category").child(category).get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
                if (!task.getResult().hasChild("Recipes")) {
                    numOfRecipes.add(0);
                } else {
                    long count = task.getResult().child("Recipes").getChildrenCount();
                    numOfRecipes.add((int) count);
                }
                int countRecipes = numOfRecipes.get(0) + 1;
                String numOfRecipesStr = countRecipes < 10 ?
                        "0" + countRecipes :
                        String.valueOf(countRecipes);
                dbRef.child("users").child(firebaseUser.getUid())
                        .child("user-recipes").child(name).setValue(recipe);

                dbRef.child("Category").child(category).child("Recipes")
                        .child(numOfRecipesStr).setValue(recipe);
            }
        });

    }

    public void setCategoryRVFragment(CategoryRVFragment categoryRVFragment) {
        this.categoryRVFragment = categoryRVFragment;
    }


    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_FRG_fragment, categoryRVFragment).commit();
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onResume() {
        super.onResume();
        onBackPressedCallback = this;
        RecipeSP.getInstance().getString(RecipeSP.CREATE_NAME, "");
        RecipeSP.getInstance().getString(RecipeSP.CREATE_DESCRIPTION, "");
        RecipeSP.getInstance().getString(RecipeSP.CREATE_INSTRUCTIONS, "");
        RecipeSP.getInstance().getIngredients(RecipeSP.CREATE_INGREDIENTS);
    }

    @Override
    public void onPause() {
        super.onPause();
        onBackPressedCallback = null;
        saveInput();
    }

}

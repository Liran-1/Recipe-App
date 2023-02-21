package com.example.cookbook.fragments;

import static android.app.Activity.RESULT_OK;

import static com.example.cookbook.activities.MainActivity.navigationView;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.cookbook.activities.MainActivity;
import com.example.cookbook.adapter.IngredientAdapter;
import com.example.cookbook.callbacks.IngredientAddedCallback;
import com.example.cookbook.callbacks.IngredientRemoveCallback;
import com.example.cookbook.callbacks.OnBackPressedCallback;
import com.example.cookbook.models.Ingredient;
import com.example.cookbook.models.Recipe;
import com.example.cookbook.utils.RecipeSP;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateRecipeFragment extends Fragment implements OnBackPressedCallback {
    private TextInputEditText recipeCreation_ETXT_name, recipeCreation_ETXT_description, ingredient_ETXT_ingredientName,
            ingredient_ETXT_ingredientAmount, recipeCreation_ETXT_instructions;
    private Spinner ingredient_SPNR_ingredientUnit, recipeCreation_SPNR_categories;
    private MaterialButton ingredient_BTN_addIngredient, recipeCreation_BTN_addRecipe;
    private ImageView recipeCreation_IMG_picture;
    private RecyclerView recipeCreation_RV_ingredients;
    private IngredientAdapter ingredientAdapter;
    private ArrayList<Ingredient> ingredients;
    private Uri cam_uri;

    public static OnBackPressedCallback onBackPressedCallback;

    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_recipe_fragment, container, false);
        findViews(view);
        initViews();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        return view;
    }

    private void initViews() {
        ingredient_BTN_addIngredient.setOnClickListener(view -> ingredientAddClicked());
//        setCallback(new IngredientRemoveCallback() {
//            @Override
//            public void userHighScoreClicked(Ingredient ingredient, int pos) {
//                ingredientRemoveCallback.removeIngredient(ingredient);
//            }
//        });

        ingredients = RecipeSP.getInstance().getIngredients();

        recipeCreation_IMG_picture  .setOnClickListener(view -> openCamera());

        recipeCreation_BTN_addRecipe.setOnClickListener(view -> addRecipe());

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

    private void addRecipe() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        String name         = recipeCreation_ETXT_name.getText().toString();
        String description  = recipeCreation_ETXT_description.getText().toString();
        String instructions = recipeCreation_ETXT_instructions.getText().toString();
//        String category = recipeCreation_SPNR_categories.getSelectedItem().toString();
        String category     ="test";
        String image        = recipeCreation_IMG_picture.toString();
        int likes           = 0;

        recipeCreation_ETXT_name.getText().clear();
        recipeCreation_ETXT_description.getText().clear();
        recipeCreation_ETXT_instructions.getText().clear();
        recipeCreation_SPNR_categories.setSelection(0);
        recipeCreation_IMG_picture.setImageResource(R.drawable.open_camera);
        ingredients = new ArrayList<>();

        Recipe recipe = new Recipe(name, description, image, instructions, category, likes,  ingredients);

        mDatabase.child("users").child(firebaseUser.getUid())
                .child("user-recipes").child(name).setValue(recipe);
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
            String name     = ingredient_ETXT_ingredientName.getText().toString();
            String amount   = ingredient_ETXT_ingredientAmount.getText().toString();
            String unit     = ingredient_SPNR_ingredientUnit.getSelectedItem().toString();

            Ingredient ingredient = new Ingredient(name, amount, unit);
            ingredient_ETXT_ingredientName.getText().clear();
            ingredient_ETXT_ingredientAmount.getText().clear();
            ingredient_SPNR_ingredientUnit.setSelection(0);

            ingredients.add(ingredient);

//            RecipeSP.getInstance().setIngredients(ingredients);
            ingredientAdapter = new IngredientAdapter(getContext(), ingredients);
            ingredientAdapter.notifyItemInserted(ingredients.size()-1);
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

    @Override
    public void onBackPressed() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_FRG_fragment, new HomeFragment()).commit();
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

package com.example.cookbook.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookbook.R;
import com.example.cookbook.adapter.CategoryAdapter;
import com.example.cookbook.adapter.FBCategoryAdapter;
import com.example.cookbook.callbacks.CategoryCallback;
import com.example.cookbook.models.Category;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CategoryRVFragment extends Fragment {


    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = mDatabase.getReference();

    private RecyclerView home_RV_categories;
    private ArrayList<Category> categories;
    private CategoryAdapter categoryAdapter;
    private Handler handler = new Handler();

    private FBCategoryAdapter fbCategoryAdapter;
    private CategoryCallback categoryCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        categoryAdapter = new CategoryAdapter(getContext(), categories);

//        Category category = new Category("test", "https://pixabay.com/photos/fruit-apple-tangerine-healthy-deco-1987195/");

        categories = getCategories();
//        categories.add(category);

        findViews(view);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initViews();
            }
        }, 1000);

        Log.d("categories", String.valueOf(categories));

        initViews();

        return view;
    }

    private void findViews(View view) {
        home_RV_categories = view.findViewById(R.id.home_RV_categories);
    }

    private void initViews() {
        categoryAdapter = new CategoryAdapter(getContext(), categories);
        home_RV_categories.setHasFixedSize(true);
        home_RV_categories.setLayoutManager(new LinearLayoutManager(getContext()));

//        Query baseQuery = mDatabase.getReference().child("Category").child("Category");
//        PagingConfig config = new PagingConfig(/* page size */ 20, /* prefetchDistance */ 10,
//                /* enablePlaceHolders */ false);
//
//        /////Firebase RecyclerView//////
//        DatabasePagingOptions<Category> options
//                = new DatabasePagingOptions.Builder<Category>()
//                .setQuery(baseQuery, config , Category.class)
//                .build();

//        fbCategoryAdapter = new FBCategoryAdapter(options);
//        home_RV_categories.setAdapter(fbCategoryAdapter);
        home_RV_categories.setAdapter(categoryAdapter);
        categoryAdapter.setCategoryCallback(categoryCallback);
    }

    private ArrayList<Category> getCategories() {
        categories = new ArrayList<>();
        dbRef.child("Category").get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());
                    }
                    else {
    //                    categories = (ArrayList) task.getResult().getValue();
                        Log.d("firebase", String.valueOf(task.getResult().getKey()));
                        for(DataSnapshot dataSnapshot: task.getResult().getChildren()){
                            String name = String.valueOf(dataSnapshot.child("Name").getValue());
                            String image = String.valueOf(dataSnapshot.child("Image").getValue());
                            String menuId = String.valueOf(dataSnapshot.getKey());
                            Log.d("name", name);
                            Log.d("image", image);
                            Category category = new Category(name, image, menuId);
                            categories.add(category);
                            categoryAdapter.notifyItemInserted(categories.size());
                            Log.d("counted", String.valueOf(categories.size()));
                        }
                    }
                });

        return categories;
    }


    // Function to tell the app to start getting
    // data from database on starting of the activity
    @Override
    public void onStart() {
        super.onStart();
//        if(fbCategoryAdapter != null){
//            fbCategoryAdapter.startListening();
//        }
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override
    public void onStop() {
        super.onStop();
//        fbCategoryAdapter.stopListening();
    }

    public void setCallback(CategoryCallback categoryCallback) {
        this.categoryCallback = categoryCallback;
    }
}
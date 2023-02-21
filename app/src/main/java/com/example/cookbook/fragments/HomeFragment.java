package com.example.cookbook.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookbook.R;
import com.example.cookbook.adapter.CategoryAdapter;
import com.example.cookbook.adapter.FBCategoryAdapter;
import com.example.cookbook.callbacks.CategoryCallback;
import com.example.cookbook.models.Category;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = mDatabase.getReference();

    private RecyclerView home_RV_categories;
    private ArrayList<Category> categories;
    private CategoryAdapter categoryAdapter;
    private FBCategoryAdapter fbCategoryAdapter;
    private CategoryCallback categoryCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.category_recyclerview, container, false);
//        categoryAdapter = new CategoryAdapter(getContext(), categories);

        categories = getCategories();

        if(categories.size() == 0){
//            categories.add(new Category("Testing", ""));
        }
        Log.d("categories", String.valueOf(categories));
        findViews(view);
        initViews();

        return view;
    }

    private void findViews(View view) {
        home_RV_categories = view.findViewById(R.id.home_RV_categories);
    }

    private void initViews() {

        home_RV_categories.setHasFixedSize(true);
        home_RV_categories.setLayoutManager(new LinearLayoutManager(getContext()));

        /////Firebase RecyclerView//////
//        FirebaseRecyclerOptions<Category> options
//                = new FirebaseRecyclerOptions.Builder<Category>()
//                .setQuery(dbRef, Category.class)
//                .build();

//        fbCategoryAdapter = new FBCategoryAdapter(options);
//        home_RV_categories.setAdapter(fbCategoryAdapter);
        home_RV_categories.setAdapter(categoryAdapter);
        categoryAdapter.setCategoryCallback(categoryCallback);
    }

    private ArrayList<Category> getCategories() {
        if (categories == null)
            categories = new ArrayList<>();
        dbRef.child("Category").child("Category")
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
//                    categories = (ArrayList) task.getResult().getValue();
                    Log.d("firebase", String.valueOf(task.getResult().getKey()));
                    for(DataSnapshot dataSnapshot: task.getResult().getChildren()){
                        String name = String.valueOf(dataSnapshot.child("Name").getValue());
                        String image = String.valueOf(dataSnapshot.child("Image").getValue());
//                        String menuId = String.valueOf(dataSnapshot.getKey());
                        Log.d("name", name);
                        Log.d("image", image);
                        Category category = new Category(name, image);
                        categories.add(category);
                        categoryAdapter.notifyItemInserted(categories.size());
                        Log.d("counted", String.valueOf(categories.size()));
                    }
                }
            }
        });
        categoryAdapter = new CategoryAdapter(getContext(), categories);

        return categories;
    }

    // Function to tell the app to start getting
    // data from database on starting of the activity
    @Override
    public void onStart()
    {
        super.onStart();
//        if(fbCategoryAdapter != null){
//            fbCategoryAdapter.startListening();
//        }
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    @Override
    public void onStop()
    {
        super.onStop();
//        fbCategoryAdapter.stopListening();
    }

}
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

        getCategoriesFromDB();

        findViews(view);

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                initViews();
//            }
//        }, 2000);

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

//        Query baseQuery = mDatabase.getReference().child("Category").;
//        PagingConfig config = new PagingConfig(/* page size */ 20, /* prefetchDistance */ 10,
//                /* enablePlaceHolders */ false);
//
//        /////Firebase RecyclerView//////
//        Query query = FirebaseDatabase.getInstance()
//                .getReference()
//                .child("Category");
//        FirebaseRecyclerOptions<Category> options
//                = new FirebaseRecyclerOptions.Builder<Category>()
//                .setQuery(query, Category.class)
//                .build();

//        fbCategoryAdapter = new FBCategoryAdapter(options);
//        home_RV_categories.setAdapter(fbCategoryAdapter);

    }


    private void loadData() {
//        FirebaseRecyclerAdapter<Category,CategoryAdapter.CategoryViewHolder> firebaseRecyclerAdapter =
//                new FirebaseRecyclerAdapter<Category, CategoryAdapter.CategoryViewHolder>() {
//                    @Override
//                    protected void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position, @NonNull Category model) {
//
//                    }
//
//                    @NonNull
//                    @Override
//                    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                        return null;
//                    }
//                };
    }


    private void getCategoriesFromDB() {
        categories = new ArrayList<>();
        dbRef.child("Category").get().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.e("firebase", "Error getting data", task.getException());
            } else {
//                        categories = (ArrayList) task.getResult().getValue();
                Log.d("firebase", String.valueOf(task.getResult().getKey()));
                for (DataSnapshot dataSnapshot : task.getResult().getChildren()) {
                    String name = String.valueOf(dataSnapshot.child("Name").getValue());
                    String image = String.valueOf(dataSnapshot.child("Image").getValue());
                    String menuId = String.valueOf(dataSnapshot.getKey());
                    Category category = new Category(name, image, menuId);
                    categories.add(category);
//                    categoryAdapter.notifyItemInserted(categories.size());
                }

                home_RV_categories.setAdapter(categoryAdapter);
                categoryAdapter.setCategoryCallback(categoryCallback);
            }
        });
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
package com.example.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cookbook.activities.MainActivity;
import com.example.cookbook.models.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private TextInputEditText etUserName, etPhone, etEmail, etPass;
    private MaterialButton bRegister;
    private ProgressBar progressBar;
    private TextView textView;
    private Toast toaster;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

//        findViews();
//        initViews();

    }

//    private void findViews() {
//        etUserName = findViewById(R.id.register_ETXT_user);
//        etPhone = findViewById(R.id.register_ETXT_phone);
//        etEmail = findViewById(R.id.register_ETXT_email);
//        etPass = findViewById(R.id.register_ETXT_pass);
//
//        bRegister = findViewById(R.id.register_BTN_register);
//
//        progressBar = findViewById(R.id.register_PRGBR_progressBar);
//
//        textView = findViewById(R.id.register_TXTV_goToLogin);
//    }

//    private void initViews() {
//        progressBar.setVisibility(View.VISIBLE);
//
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), Login.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        bRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String userName, phone, email, password;
//                userName = etUserName.getText().toString();
//                phone = etPhone.getText().toString();
//                email = etEmail.getText().toString();
//                password = etPass.getText().toString();
//
//                //check if an input is empty
//                if (checkIfEmpty(userName, etUserName.getHint().toString()) ||
//                        checkIfEmpty(phone, etPhone.getHint().toString()) ||
//                        checkIfEmpty(email, etEmail.getHint().toString()) ||
//                        checkIfEmpty(password, etPass.getHint().toString())) {
//                    progressBar.setVisibility(View.GONE);
//                    return;
//                }
//
//                User user = new User(userName, email, phone, password);
//
//
//                mAuth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                progressBar.setVisibility(View.GONE);
//                                if (task.isSuccessful()) {
//                                    toaster = Toast.makeText(Register.this,
//                                            "Account Created!", Toast.LENGTH_SHORT);
//                                    toaster.show();
//
//                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d("Created Successfully", "createUserWithEmail:success");
//                                    FirebaseUser user = mAuth.getCurrentUser();
//
//                                    userLoggedIn();
//
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Log.w("Creation Failed", "createUserWithEmail:failure", task.getException());
//                                    toaster = Toast.makeText(Register.this,
//                                            "Authentication failed.", Toast.LENGTH_SHORT);
//                                    toaster.show();
//                                }
//                            }
//                        });
//            }
//        });
//    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            userLoggedIn();
        }
    }

    private void userLoggedIn() {
        Intent intent = new Intent(Register.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private boolean checkIfEmpty(String input, String msg) {
        if (TextUtils.isEmpty(input)) {
            toaster = Toast.makeText(Register.this, "Enter " + msg, Toast.LENGTH_SHORT);
            toaster.show();
            return true;
        }
        return false;
    }

//    public void writeNewUser(String userId, String name, String email, String phoneNumber,String password) {
//        User user = new User(name, email, phoneNumber, password);
//
//        mDatabase.child("User").child(user.getUserId()).setValue(user);
//    }

}

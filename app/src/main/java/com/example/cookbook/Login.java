package com.example.cookbook;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.cookbook.activities.MainActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class Login extends AppCompatActivity {

    //    private TextInputEditText etEmail, etPass;
//    private MaterialButton bLogin;
//    private ProgressBar progressBar;
//    private TextView textView;
    private Toast toaster;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        findViews();
//        initViews();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            login();
        } else {
            userLoggedIn();

        }

    }

    private void login() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
//                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        signInLauncher.launch(signInIntent);
    }

//    private void findViews() {
//        etEmail = findViewById(R.id.register_ETXT_email);
//        etPass = findViewById(R.id.register_ETXT_pass);
//
//        bLogin = findViewById(R.id.login_BTN_login);
//
//        progressBar = findViewById(R.id.login_PRGBR_progressBar);
//
//        textView = findViewById(R.id.login_TXTV_goToRegister);
//    }

//    private void initViews() {
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), Register.class);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//        bLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                progressBar.setVisibility(View.VISIBLE);
//
//                String email, password;
//                email = etEmail.getText().toString();
//                password = etPass.getText().toString();
//
//                if (checkIfEmpty(email, etEmail.getHint().toString()) ||
//                        checkIfEmpty(password, etPass.getHint().toString())) {
//                    return;
//                }
//
//                mAuth.signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
//                                    Log.d("Logged in Successfully", "signInWithEmail:success");
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    updateUI(user);
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Log.w("Log in Failed", "signInWithEmail:failure", task.getException());
//                                    Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
//                                            Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
//                                }
//                            }
//                        });
//
////                mAuth.signInWithEmailAndPassword(email, password)
////                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
////                            @Override
////                            public void onComplete(@NonNull Task<AuthResult> task) {
////                                progressBar.setVisibility(View.GONE);
////                                if (task.isSuccessful()) {
////
////                                    FirebaseUser user = mAuth.getCurrentUser();
////
////                                    toaster = Toast
////                                            .makeText(Login.this,
////                                                    "Hello " + user.getDisplayName() + "!",
////                                                    Toast.LENGTH_SHORT);
////                                    toaster.show();
////
////                                    userLoggedIn();
////
////                                    // Sign in success, update UI with the signed-in user's information
////                                    Log.d("Logged in Successfully", "signInWithEmail:success");
////                                } else {
////                                    // If sign in fails, display a message to the user.
////                                    Log.w("Log in Failed", "signInWithEmail:failure",
////                                            task.getException());
////
////                                    toaster = Toast.makeText(Login.this,
////                                            "Authentication failed.", Toast.LENGTH_SHORT);
////                                    toaster.show();
////                                }
////                            }
////                        });
//            }
//        });
//    }

    // See: https://developer.android.com/training/basics/intents/result
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        userLoggedIn();
    }

    public void signOut() {
        Context context = this;
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        String message = "Logged out";
                        toaster = Toast
                                .makeText(context, message, Toast.LENGTH_SHORT);
                        toaster.show();
                    }
                });
        // [END auth_fui_signout]
    }

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
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean checkIfEmpty(String input, String msg) {
        if (TextUtils.isEmpty(input)) {
            toaster = Toast.makeText(Login.this, "Enter " + msg, Toast.LENGTH_SHORT);
            toaster.show();
            return true;
        }
        return false;
    }

}
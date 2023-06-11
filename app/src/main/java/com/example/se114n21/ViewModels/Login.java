package com.example.se114n21.ViewModels;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.se114n21.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    ImageButton butBack;
    EditText txtEmail, txtPassword;
    ImageButton eyeButton;
    Button butLogin;
    FirebaseAuth auth;
    TextView textViewForgotPassword, textViewSignUp;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        initUI();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);


        // Ki?m tra user ?? ??ng nh?p hay ch?a
        // ??nh d?u ch? c?a h?ng v?i nh?n vi?n b?ng vi?c verified email
//        FirebaseUser user = auth.getCurrentUser();
//        if (user != null) {
//            startActivity(new Intent(Login.this, MainActivity.class));
//        }
        butBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        butLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(txtEmail.getText());
                password = String.valueOf(txtPassword.getText());

                if (! email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (! password.isEmpty()) {
                        auth.signInWithEmailAndPassword(email, password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(Login.this, "??ng nh?p th?nh c?ng", Toast.LENGTH_SHORT).show();
                                        FirebaseUser firebaseUser = auth.getCurrentUser();
                                        boolean emailVerified = false;
                                        if (firebaseUser != null ) {
                                            emailVerified = firebaseUser.isEmailVerified();
                                        }

//                                       Có 2 cái bottom nav view Loan chỉnh nha
//                                        BottomNavigation
//                                        BottomNavigationNhanVien
                                        if (emailVerified) {
                                            startActivity(new Intent(Login.this, admin_QLNhanVien.class));
                                        }  else {
                                            startActivity(new Intent(Login.this, BottomNavigation.class));
                                        }

//                                        startActivity(new Intent(Login.this, Account.class));
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(Login.this, "Vui l?ng nh?p v?o m?t kh?u", Toast.LENGTH_SHORT).show();
                        txtPassword.setError("Password canot be empty");
                        txtPassword.requestFocus();
                    }
                } else if (email.isEmpty()) {
                    Toast.makeText(Login.this, "Vui l?ng nh?p v?o ??a ch? email", Toast.LENGTH_SHORT).show();
                    txtEmail.setError("Email cannot be empty");
                    txtEmail.requestFocus();
                } else {
                    Toast.makeText(Login.this, "Vui l?ng nh?p v?o ??a ch? email h?p l?", Toast.LENGTH_SHORT).show();
                    txtEmail.setError("Please enter valid email");
                    txtEmail.requestFocus();
                }
            }
        });
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ForgotPassword.class));
                finish();
            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
                finish();
            }
        });

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    eyeButton.setVisibility(View.VISIBLE);
                } else {
                    eyeButton.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        eyeButton.setOnClickListener(view -> {
            if (txtPassword.getInputType() == 129) {
                txtPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                eyeButton.setImageDrawable(getDrawable(R.drawable.eye_blind));
            }
            else
            {
                eyeButton.setImageDrawable(getDrawable(R.drawable.eye));
            }
        });

    }

    private void initUI() {
        butBack = findViewById(R.id.butBack);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        eyeButton = findViewById(R.id.eyeButton);
        butLogin = findViewById(R.id.butLogin);
        textViewForgotPassword = findViewById((R.id.textViewForgotPassword));
        textViewSignUp = findViewById(R.id.textViewSignUp);
    }

}


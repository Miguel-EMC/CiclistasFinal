package com.example.ciclistasfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword, editTextName, editTextLastName,editTextAddress;
    Button buttonReg;
    FirebaseAuth mAuth;


    ProgressBar progressBar;
    TextView textView;
    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextName = findViewById(R.id.name);
        editTextLastName = findViewById(R.id.lastName);
        editTextAddress = findViewById(R.id.address);
        buttonReg = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.loginNow);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String password, name, surname, address;
                String email = editTextEmail.getText().toString().trim();
                password = String.valueOf(editTextPassword.getText());
                name = String.valueOf(editTextName.getText().toString());
                surname = String.valueOf(editTextLastName.getText().toString());
                address = String.valueOf(editTextAddress.getText().toString());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this, "Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name)){
                    Toast.makeText(Register.this, "Enter name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(surname)){
                    Toast.makeText(Register.this, "Enter surname", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(address)){
                    Toast.makeText(Register.this, "Enter address", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()){
                                    FirebaseUser firebaseUser = mAuth.getCurrentUser();
                                    User user = new User(name, surname, email, password, address, "ciclist");
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    db.collection("users")
                                            .document(firebaseUser.getUid())
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(Register.this, "Account created.", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Register.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                } else {
                                    Toast.makeText(Register.this, "Authentication fails", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }
}
package com.example.socially.loginsignup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.socially.MainActivity;
import com.example.socially.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword, textSignUP;
    private Button cirLoginButton;
    private ProgressBar loginProgressBar;
    private TextView TextSignUP, TextForgotPass;
    FirebaseAuth authx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPassword);
        cirLoginButton = findViewById(R.id.cirLoginButton);
        TextSignUP = findViewById(R.id.TextSignUP);
        TextForgotPass = findViewById(R.id.TextForgotPass);
        loginProgressBar=findViewById(R.id.loginProgressBar);
        authx = FirebaseAuth.getInstance();
        
        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginProgressBar.setVisibility(View.VISIBLE);
                String str_choice = editTextEmail.getText().toString();
                String str_passwork = editTextPassword.getText().toString();
                if(str_choice.length()==0){
                    editTextEmail.setError("Mail must not be empty");
                    loginProgressBar.setVisibility(View.GONE);
                }
                if(str_passwork.length()==0){
                    editTextPassword.setError("Password is empty");
                    loginProgressBar.setVisibility(View.GONE);
                }
                else{
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    Query checkusername = reference.orderByChild("Mail ID").equalTo(str_choice);
                    checkusername.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot alpha : snapshot.getChildren()){
                                String email_input = alpha.getKey();
                                if(snapshot.exists()){
                                    String psswdfromDB = snapshot.child(email_input).child("Password").getValue(String.class);
                                    if(psswdfromDB.equals(str_passwork)){
                                        String namefromDB = snapshot.child(email_input).child("Name").getValue(String.class);
                                        String usernamefromDB = snapshot.child(email_input).child("Username").getValue(String.class);
                                        String emailfromDB = snapshot.child(email_input).child("Mail ID").getValue(String.class);
                                        authx.signInWithEmailAndPassword(str_choice,str_passwork).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful()){
                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                                    intent.putExtra("Name",namefromDB);
                                                    intent.putExtra("Username",usernamefromDB);
                                                    intent.putExtra("Email ID",emailfromDB);
                                                    intent.putExtra("Password",psswdfromDB);

                                                    startActivity(intent);
                                                }
                                                else{
                                                    Toast.makeText(LoginActivity.this, "We have encountered an error", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        loginProgressBar.setVisibility(View.GONE);


                                    }
                                    else{
                                        editTextPassword.setError("Incorrect Password");
                                        loginProgressBar.setVisibility(View.GONE);
                                        editTextPassword.requestFocus();
                                    }
                                }
                                else{
                                    editTextEmail.setError("Incorrect Email ID");
                                    loginProgressBar.setVisibility(View.GONE);
                                    editTextEmail.requestFocus();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                }
                

            }
        });
        
        TextSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        
        TextForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPassActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}

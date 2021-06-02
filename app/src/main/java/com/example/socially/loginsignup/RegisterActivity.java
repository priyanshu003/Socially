package com.example.socially.loginsignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socially.MainActivity;
import com.example.socially.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextName, editTextEmail, editTextPassword;
    private Button cirLoginButton;
    private ProgressBar RegisterProgressBar;
    private TextView TextSignIP;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        cirLoginButton = findViewById(R.id.cirLoginButton);
        RegisterProgressBar= findViewById(R.id.RegisterProgressBar);
        TextSignIP = findViewById(R.id.TextSignIP);
        auth=FirebaseAuth.getInstance();
        RegisterProgressBar.setVisibility(View.GONE);

        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterProgressBar.setVisibility(View.VISIBLE);
                String strname = editTextName.getText().toString();
                String stremail = editTextEmail.getText().toString();
                String strpassword = editTextPassword.getText().toString();


                if(strname.length()==0){
                    editTextName.setError("Please enter ur name");
                    RegisterProgressBar.setVisibility(View.GONE);
                    editTextName.requestFocus();
                }
                if(stremail.length()==0){
                    editTextEmail.setError("Please enter an email");
                    RegisterProgressBar.setVisibility(View.GONE);
                    editTextEmail.requestFocus();
                }
                if(strpassword.length()<8){
                    editTextPassword.setError("Password must be greater than 8 digits");
                    RegisterProgressBar.setVisibility(View.GONE);
                    editTextPassword.requestFocus();
                }



                if(strpassword.matches(".*\\d.*") && strpassword.length()>8){
                    auth.createUserWithEmailAndPassword(stremail, strpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                FirebaseUser fuser = auth.getCurrentUser();
                                String fuid = fuser.getUid();
                                HashMap<String, Object> hashMap = new HashMap<String, Object>();
                                hashMap.put("Name",strname);
                                hashMap.put("Mail ID",stremail);
                                hashMap.put("Password",strpassword);
                                FirebaseDatabase.getInstance().getReference("Users").child(fuid).setValue(hashMap);
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(RegisterActivity.this, "Please enter a new email", Toast.LENGTH_SHORT).show();
                                RegisterProgressBar.setVisibility(View.GONE);
                                editTextEmail.requestFocus();
                            }
                        }
                    });
                    RegisterProgressBar.setVisibility(View.GONE);
                }

            }
        });
        TextSignIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
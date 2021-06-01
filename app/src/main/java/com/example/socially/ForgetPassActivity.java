package com.example.socially;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class ForgetPassActivity extends AppCompatActivity {
    
    TextInputEditText emailEt;
    Button resetPasswordEt;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);
        
        emailEt=findViewById(R.id.editTextEmailRecover);
        resetPasswordEt=findViewById(R.id.cirRecoverPassButton);
        progressBar=findViewById(R.id.progressBar);
        auth=FirebaseAuth.getInstance();
        
        resetPasswordEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailId=emailEt.getText().toString();
                if(TextUtils.isEmpty(emailId)){
                    emailEt.setError("Enter registered Email Id");
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    auth.sendPasswordResetEmail(emailId).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(ForgetPassActivity.this,"Please check Your Email for password verification",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(ForgetPassActivity.this,"Failed to Send Email",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}


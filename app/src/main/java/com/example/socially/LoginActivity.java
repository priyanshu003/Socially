package com.example.socially;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword, textSignUP;
    private Button cirLoginButton;
    FirebaseAuth authx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPassword);
        cirLoginButton = findViewById(R.id.cirLoginButton);
        textSignUP = findViewById(R.id.textSignUP);
        authx = FirebaseAuth.getInstance();
        
        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_choice = editTextEmail.getText().toString();
                String str_passwork = editTextPassword.getText().toString();
                if(str_choice.length()==0){
                    editTextEmail.setError("Mail must not be empty");
                }
                if(str_passwork.length()==0){
                    editTextPassword.setError("Password is empty");
                }
                if(str_choice.contains("@")){
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


                                    }
                                    else{
                                        editTextPassword.setError("Incorrect Password");
                                        editTextPassword.requestFocus();
                                    }
                                }
                                else{
                                    editTextEmail.setError("Incorrect Email ID");
                                    editTextEmail.requestFocus();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                else{
                    DatabaseReference theta = FirebaseDatabase.getInstance().getReference("Users");
                    Query checkuser = theta.orderByChild("Username").equalTo(str_choice);
                    checkuser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot beta : snapshot.getChildren()){
                                String user_input = beta.getKey();
                                if(snapshot.exists()){
                                    String psswdfroDB = snapshot.child(user_input).child("Password").getValue(String.class);
                                    if(psswdfroDB.equals(str_passwork)){
                                        String namefroDB = snapshot.child(user_input).child("Name").getValue(String.class);
                                        String usernamefroDB = snapshot.child(user_input).child("Username").getValue(String.class);
                                        String emailfroDB = snapshot.child(user_input).child("Mail ID").getValue(String.class);
                                        authx.signInWithEmailAndPassword(emailfroDB,str_passwork).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if(task.isSuccessful()){
                                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                                                    intent.putExtra("Name",namefroDB);
                                                    intent.putExtra("Username",usernamefroDB);
                                                    intent.putExtra("Email ID",emailfroDB);
                                                    intent.putExtra("Password",psswdfroDB);

                                                    startActivity(intent);
                                                }
                                                else{
                                                    Toast.makeText(LoginActivity.this, "We have encountered an error. Please try again", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });


                                    }
                                    else{
                                        editTextPassword.setError("Incorrect Password");
                                        editTextPassword.requestFocus();
                                    }
                                }
                                else{
                                    editTextEmail.setError("Incorrect Username");
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
        
        textSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}

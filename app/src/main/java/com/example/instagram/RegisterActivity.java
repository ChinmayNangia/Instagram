package com.example.instagram;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class  RegisterActivity extends AppCompatActivity {
private EditText username,password,fullname,email;
private DatabaseReference mrootref;
private Button register;
private TextView loginuser;
private FirebaseAuth auth;
ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        auth=FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        username=findViewById(R.id.username);
        fullname=findViewById(R.id.name);
        register=findViewById(R.id.register);
        loginuser=findViewById(R.id.loginuser);
        mrootref= FirebaseDatabase.getInstance().getReference();
pd=new ProgressDialog(this);


        loginuser.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    }
                }
        );

register.setOnClickListener(
        new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUsername=username.getText().toString();
                String txtEmail=email.getText().toString();
                String txtPassword=password.getText().toString();
                String txtName=fullname.getText().toString();
                if(TextUtils.isEmpty(txtUsername)||TextUtils.isEmpty(txtName)||TextUtils.isEmpty(txtEmail)||TextUtils.isEmpty(txtPassword))
                    Toast.makeText(RegisterActivity.this,"empty crediants",Toast.LENGTH_LONG).show();
                else if(txtPassword.length()<6)
                    Toast.makeText(RegisterActivity.this,"password too short",Toast.LENGTH_LONG).show();
                else
                    registerUser(txtUsername,txtName,txtEmail,txtPassword);



            }
        }
);

    }

    private void registerUser(final String username, final String name, final String email, String password) {
        pd.setMessage("please Wait");
        pd.show();
        auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String,Object>  map=new HashMap<>();
                map.put("name",name);
                map.put("email",email);
                map.put("username",username);
                map.put("id",auth.getCurrentUser().getUid());
                map.put("bio"," ");
                map.put("imageurl","default");
           mrootref.child("users").child(auth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                   if(task.isSuccessful())
                       pd.dismiss();
                       Toast.makeText(RegisterActivity.this,"go to settings and update profile",Toast.LENGTH_LONG).show();
                       Intent intent=new Intent(RegisterActivity.this, MainActivity.class);
                       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_CLEAR_TOP);
                       startActivity(intent);
                       finish();

               }
           });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegisterActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

            }
        });
    }
}

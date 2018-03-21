package com.example.shabarinath.bloodbank;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signup extends AppCompatActivity
{
    EditText name,surname,email,pass,cnfpass,city,add,phnum;
    Button signup;
    RadioGroup gender;
    Spinner bloodgroup;
    private FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    public  String mail,password,cnfmpassword,phonenum,cityname,address;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        name=(EditText)findViewById(R.id.name);
        surname=(EditText)findViewById(R.id.surname);
        email=(EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.password);
        cnfpass=(EditText)findViewById(R.id.cnfpassword);
        city=(EditText)findViewById(R.id.city);
        add=(EditText)findViewById(R.id.address);
        phnum=(EditText)findViewById(R.id.phnum);
        signup = (Button)findViewById(R.id.submit);
        gender=(RadioGroup) findViewById(R.id.gender);
        bloodgroup=(Spinner) findViewById(R.id.bloodgroup);


        firebaseAuth= FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(signup.this,"Please Wait ","While Registering",true);

                register();


            }
        });



    }


    public void register(){
        mail = email.getText().toString().trim();
        password = pass.getText().toString().trim();
        cnfmpassword=cnfpass.getText().toString().trim();
        phonenum=phnum.getText().toString().trim();

         if (!isValidEmail(mail)) {
            progressDialog.dismiss();
            email.setError("please enter your gmail account");
        }

        else if (TextUtils.isEmpty(mail)) {
            progressDialog.dismiss();
            email.setError( "Email is required!" );
        }
        else if(TextUtils.isEmpty(password)){
            progressDialog.dismiss();
            pass.setError( "Password!" );

        }
        else if (TextUtils.isEmpty(cnfmpassword)){
            progressDialog.dismiss();
            cnfpass.setError( "Password!" );
        }
        else if(!(password.equals(cnfmpassword))){
            progressDialog.dismiss();
            Toast.makeText(this, "password mismatch", Toast.LENGTH_SHORT).show();
        }
         else if(TextUtils.isEmpty(phonenum)){
             progressDialog.dismiss();
             pass.setError( "Password!" );

         }

        else {
            firebaseAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(signup.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Intent intent = new Intent(signup.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        FirebaseAuthException e = (FirebaseAuthException) task.getException();
                        Toast.makeText(signup.this, "error" + " " + e.getMessage(), Toast.LENGTH_LONG).show();

                    }


                }
            });
        }
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[a-z0-9](\\.?[a-z0-9]){5,}@g(oogle)?mail\\.com$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}

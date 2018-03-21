
package com.example.shabarinath.bloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
{
    EditText memail,mpassword;
    Button login,signup;

    private FirebaseAuth mauth;
    private FirebaseAuth.AuthStateListener mautlistener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        memail=(EditText) findViewById(R.id.email);
        mpassword=(EditText) findViewById(R.id.password);
        login=(Button) findViewById(R.id.login);
        signup=(Button) findViewById(R.id.signup);
        mauth=FirebaseAuth.getInstance();

        mautlistener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                if(firebaseAuth.getCurrentUser()!=null)
                {
//
                }

            }
        };

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                signin();


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this,signup.class);
                startActivity(intent);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mauth.addAuthStateListener(mautlistener);
    }

    public void signin()
    {
        String email=memail.getText().toString();
        String password=mpassword.getText().toString();

        if(TextUtils.isEmpty(email)||TextUtils.isEmpty(password))
        {
            Toast.makeText(MainActivity.this,"Fields are empty",Toast.LENGTH_SHORT).show();

        }
        else
        {
            mauth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this ,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful())
                    {
                        Toast.makeText(MainActivity.this,"Sign in problem",Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Intent intent = new Intent(MainActivity.this,successfull.class);
                        startActivity(intent);

                    }
                }
            });
        }
    }

}

package com.sri.voiceofcustomer.signup;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.*;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sri.voiceofcustomer.R;
import com.sri.voiceofcustomer.database.models.User;
import com.sri.voiceofcustomer.login.LoginActivity;

import java.math.BigInteger;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword, firstName, lastName, contact,userCompany,userName;
    private Button btnSignIn, btnSignUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebaseinstance
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.last_name);
        contact = (EditText) findViewById(R.id.contact);
        userCompany= (EditText) findViewById(R.id.company);
        userName = (EditText) findViewById(R.id.username);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                final String fname = firstName.getText().toString().trim();
                final String lname = lastName.getText().toString().trim();
                final String cntct = contact.getText().toString().trim();
                final String company = userCompany.getText().toString().trim();
                final String username = userName.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(getApplicationContext(),"Enter Email Address!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(username))
                {
                    Toast.makeText(getApplicationContext(),"Enter Username!",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(),"Enter password !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(fname))
                {
                    Toast.makeText(getApplicationContext(),"Enter first name !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(lname))
                {
                    Toast.makeText(getApplicationContext(),"Enter last name !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(cntct) || cntct.length()!=10)
                {
                    Toast.makeText(getApplicationContext(),"Enter valid contact !",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()<6)
                {
                    Toast.makeText(getApplicationContext(),"Password too short,Enter Minimum 6 characters.!",Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                        auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (!(task.isSuccessful())) {
                                            try
                                            {
                                                throw task.getException();
                                            }
                                            catch(FirebaseAuthUserCollisionException e)
                                            {
                                                Toast.makeText(SignupActivity.this, "Email already registered", Toast.LENGTH_SHORT).show();
                                            }
                                            catch(FirebaseNetworkException e)
                                            {
                                                Toast.makeText(SignupActivity.this, "Chect your network and retry", Toast.LENGTH_SHORT).show();
                                            }
                                            catch(FirebaseAuthWeakPasswordException e)
                                            {
                                                Toast.makeText(SignupActivity.this, "Password is too weak.", Toast.LENGTH_SHORT).show();
                                            }
                                            catch(Exception e)
                                            {
                                                Log.e("Error occured",e.getMessage().toString());
                                            }
                                            progressBar.setVisibility(View.GONE);

                                        } else if (task.isSuccessful()) {

                                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
                                            // Creating new user node, which returns the unique key value
                                            // new user node would be /users/$userid/
                                            //String userId = mDatabase.push().getKey();
                                            // creating user object
                                            User user = new User(email, "customer", fname, lname, cntct);
                                            // pushing user to 'users' node using the userId
                                            mDatabase.child(company).push().setValue(user);

                                            mDatabase = FirebaseDatabase.getInstance().getReference("userdetail");
                                            int keyIndex = email.indexOf('@');
                                            String keyId = email.substring(0,keyIndex);
                                            mDatabase.child(keyId).setValue(user);

                                            Toast.makeText(SignupActivity.this, "Registration success.", Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.GONE);
                                            finish();
                                            Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                                            intent.putExtra("isCustomer",true);
                                            startActivity(intent);

                                        }
                                    }
                                });



            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}

package com.card;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.card.Model.userModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity {
    ProgressDialog dialog1;
    EditText Username,id_number,password,C_password,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        Username=(EditText)findViewById(R.id.user_name);
        id_number=(EditText)findViewById(R.id.id_number);
        password=(EditText)findViewById(R.id.password);
        C_password=(EditText)findViewById(R.id.confirm_password);
        email=(EditText)findViewById(R.id.e_mail);
        dialog1 = new ProgressDialog(this);
        dialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog1.setMessage("Account is being created Please wait");
        dialog1.setIndeterminate(true);
        dialog1.setCanceledOnTouchOutside(false);
        ImageView back_btn=(ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button SignUp=(Button)findViewById(R.id.sign_up);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( validation()) {
                    dialog1.show();
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = task.getResult().getUser();

                                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(Username.getText().toString())
                                        .build();
                                user.updateProfile(userProfileChangeRequest);


                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference Users = database.getReference("Users");

                                userModel userModel = new userModel();
                                userModel.setUID(user.getUid());
                                userModel.setID_Card(String.valueOf(gen()));
                                userModel.setPrice(0.0);
                                userModel.setID_number(id_number.getText().toString());

                                Users.push().setValue(userModel);
                                sendVerificationEmail();
                            } else {
                                // if email already registerd
                                if (task.getException().getMessage().equals("The email address is already in use by another account.")) {
                                    dialog1.cancel();
                                    Toast.makeText(RegisterActivity.this, "The email address is already in use by another account", Toast.LENGTH_SHORT).show();
                                }
                                dialog1.cancel();
                                Toast.makeText(RegisterActivity.this, "The registration failed to make sure that the data is filled correctly", Toast.LENGTH_SHORT).show();

                            }


                        }
                    });
                }

            }
        });
    }
    public int gen() {
        Random r = new Random( System.currentTimeMillis() );
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }
    public void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
       // user(id_number.getText().toString());
        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Signup successful. Verification email sent", Toast.LENGTH_SHORT).show();
                                dialog1.cancel();
                                finish();
                                //   Toast.makeText(RegisterActivity.this, "Register Successfully ", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegisterActivity.this, ConfirmEmailActivity.class);
                                startActivity(i);
                            }
                        }
                    });
        }

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public boolean validation(){


        if (Username.getText().toString().equals("")){
            Username.setError("needed *");
            Toast.makeText(getBaseContext(), "Please enter your name", Toast.LENGTH_LONG).show();
            return false;
        }
        if (id_number.getText().toString().equals("")){
            id_number.setError("needed *");
            Toast.makeText(getBaseContext(), "Make sure you enter the ID number", Toast.LENGTH_LONG).show();
            return false;
        }


        if (password.getText().toString().equals("")){
            password.setError("Make sure you enter your password");
            Toast.makeText(getBaseContext(), "Make sure you enter your password", Toast.LENGTH_LONG).show();
            return false;
        }
        if (C_password.getText().toString().equals("")){
            C_password.setError("Make sure you enter your password confirmation");
            Toast.makeText(getBaseContext(), "Make sure you enter your password confirmation", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!C_password.getText().toString().equals(password.getText().toString())){
            C_password.setError("Password does not match");
            password.setError("Password does not match");
            Toast.makeText(getBaseContext(), "Password does not match", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!validateEmail(email.getText().toString())){
            email.setError("Please enter your e-mail address correctly");
            Toast.makeText(getBaseContext(), "Please enter your e-mail address correctly", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
    public final boolean validateEmail(String target) {
        String EMAIL_PATTERN = "^[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?$";
        if (target !=null && target.length() > 1) {
            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(target);
            return matcher.matches();
        } else if (target.length() == 0) {
            return false;
        } else {
            return false;
        }
    }

}

package com.card;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.card.Model.PurchaseModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FirstActivity extends AppCompatActivity {
    ProgressDialog dialog1;
    EditText name,password;
    String name1="admin";
    String password1="admin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_first);
        SharedPreferences prefsLogin = getSharedPreferences("login", MODE_PRIVATE);
        boolean isLoggedIn = prefsLogin.getBoolean("isLoggedIn", false);
        if(isLoggedIn){
            Intent i = new Intent(FirstActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        dialog1 = new ProgressDialog(this);
        dialog1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog1.setMessage("Log in Please wait");
        dialog1.setIndeterminate(true);
        dialog1.setCanceledOnTouchOutside(false);

        Button sign_in=(Button)findViewById(R.id.sign_in);
        sign_in.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FirstActivity.this);
                        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );

                        View views = inflater.inflate(R.layout.dialoge_login, null);
                        alertDialog.setView(views);

                        name=(EditText)views.findViewById(R.id.name);
                         password=(EditText)views.findViewById(R.id.password);

                        Button send=(Button)views.findViewById(R.id.send);
                        send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(validation()){
                                    if(name.getText().toString().equals(name1)||password.getText().toString().equals(password1)){
                                        Toast.makeText(FirstActivity.this, "Admin is signed ", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(FirstActivity.this, payerActivity.class);
                                        startActivity(i);

                                    }else {
                                        dialog1.show();
                                        FirebaseAuth.getInstance().signInWithEmailAndPassword(name.getText().toString(), password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                                    SharedPreferences.Editor editor = getSharedPreferences("login", MODE_PRIVATE).edit();
                                                    editor.putString("ID", user.getUid());
                                                    editor.putBoolean("isLoggedIn", true);
                                                    editor.apply();

                                                    boolean emailVerified = user.isEmailVerified();
                                                    if (emailVerified) {
                                                        dialog1.cancel();


                                                        //Toast.makeText(FirstActivity.this, "User is signed ", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(FirstActivity.this, RegistrationSuccessActivity.class);
                                                        startActivity(i);
                                                    } else {
                                                        dialog1.cancel();
                                                        Toast.makeText(FirstActivity.this, "Email is not verified", Toast.LENGTH_SHORT).show();

                                                    }
                                                } else {
                                                    dialog1.cancel();
                                                    Toast.makeText(FirstActivity.this, "The registration failed to make sure that the data is filled correctly", Toast.LENGTH_SHORT).show();


                                                }


                                            }
                                        });

                                    }
                            }
                            }
                        });

                        alertDialog.show();

                    }
                }
        );
        TextView new_account=(TextView)findViewById(R.id.sign_up);
        new_account.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // dialog1.show();
                        //  Login(username.getText().toString(), password.getText().toString());
                        Intent i = new Intent(FirstActivity.this, RegisterActivity.class);
                        startActivity(i);
                    }
                }
        );

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    public boolean validation(){
        if (name.getText().toString().equals("")){
            name.setError("Please enter your e-mail address correctly");
            Toast.makeText(getBaseContext(), "\"Please enter your e-mail address correctly\"", Toast.LENGTH_LONG).show();
            return false;
        }
        if (password.getText().toString().equals("")){
            password.setError("Make sure you enter your password");
            Toast.makeText(getBaseContext(), "Make sure you enter your password", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}

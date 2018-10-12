package com.card;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_first);

        Button sign_in=(Button)findViewById(R.id.sign_in);
        sign_in.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(FirstActivity.this);
                        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );

                        View views = inflater.inflate(R.layout.dialoge_login, null);
                        alertDialog.setView(views);

                        final EditText name=(EditText)views.findViewById(R.id.user_name);
                        final EditText password=(EditText)views.findViewById(R.id.password);

                        Button send=(Button)views.findViewById(R.id.send);
                        send.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(FirstActivity.this, MainActivity.class);
                                startActivity(i);
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

}

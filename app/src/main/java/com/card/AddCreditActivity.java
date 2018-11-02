package com.card;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.card.Model.userModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddCreditActivity extends AppCompatActivity {
EditText new_number;
    Double new_price=null;
    Double old_p = null;
    String uid="",Id_n="",id_card="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_credit);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        ImageView Back_btn=(ImageView)findViewById(R.id.Back_btn);
        Back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final TextView balnce=(TextView)findViewById(R.id.balnce);
        new_number=(EditText)findViewById(R.id.code);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference Users = database.getReference("Users");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, userModel> results = dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, userModel>>() {});

                List<userModel> posts = new ArrayList<>(results.values());

                for (userModel post : posts) {
                    if(user.getUid().equals(post.getUID())){
                        balnce.setText(post.getPrice().toString()+" SAR");
                        old_p=post.getPrice();
                        uid=post.getUID();
                        Id_n=post.getID_number();
                        id_card=post.getID_Card();
                    }
                    //  Log.e("Post Title", post.getPrice().toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new_number.getText().toString().equals("")){
                    new_number.setError("Please enter your Card Number");
                    Toast.makeText(getBaseContext(), "\"Please enter your  Card Number\"", Toast.LENGTH_LONG).show();
                }else {
                    DatabaseReference Card_Numbers = database.getReference("Card_Numbers");
                    Card_Numbers.getRef().child(new_number.getText().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {

                                new_price = Double.parseDouble(dataSnapshot.getValue().toString());
                                if(new_price==0){
                                    Toast.makeText(AddCreditActivity.this, "This number does not contain credit", Toast.LENGTH_LONG).show();

                                }
                                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                final Query query = database.getReference().child("Users").orderByChild("uid").equalTo(uid);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                                            Snapshot.getRef().setValue(new userModel(uid, (Double) old_p + new_price, Id_n, id_card));
                                        }
                                        Toast.makeText(AddCreditActivity.this, "Added Success", Toast.LENGTH_LONG).show();
                                        new_number.setText("");
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        databaseError.getMessage();
                                    }
                                });
                            } else
                                Toast.makeText(AddCreditActivity.this, "Card number is not Valid", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            databaseError.getMessage();
                        }
                    });
                }
              //  finish();

            }
        });

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

package com.card;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.card.Model.userModel;
import com.card.fragments.homeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
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

public class EditAccountActivity extends AppCompatActivity {

    EditText Username,password,new_password,email;

    Double old_p = null;
    String uid="",Id_n="",id_card="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        ImageView back_btn=(ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Username=(EditText)findViewById(R.id.user_name);
      //  id_number=(EditText)findViewById(R.id.id_number);
        password=(EditText)findViewById(R.id.old_password);
        new_password=(EditText)findViewById(R.id.new_password);
        email=(EditText)findViewById(R.id.e_mail);

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
                        Username.setText(user.getDisplayName());
                        email.setText(user.getEmail());
                        email.setFocusable(false);
                        old_p=post.getPrice();
                        uid=post.getUID();
                        Id_n=post.getID_number();
                     //   id_number.setText(Id_n);
                        id_card=post.getID_Card();

                    }
                    //  Log.e("Post Title", post.getPrice().toString());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        Button edit=(Button)findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Username.getText().toString().equals("")) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                            .setDisplayName(Username.getText().toString())
                            .build();
                    homeFragment.newInstance(Username.getText().toString());
                    user.updateProfile(userProfileChangeRequest);
                    Toast.makeText(EditAccountActivity.this, "Username Edit Success", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(EditAccountActivity.this, "Username Is Empty", Toast.LENGTH_LONG).show();
                    Username.setError("Needed *");
                }

             /*   if (!id_number.getText().toString().equals("")) {
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final Query query = database.getReference().child("Users").orderByChild("uid").equalTo(uid);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                                Snapshot.getRef().setValue(new userModel(uid, (Double) old_p, Id_n, id_card));
                            }
                            Toast.makeText(EditAccountActivity.this, "Edit Success", Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            databaseError.getMessage();
                        }
                    });
                } else {
                    Toast.makeText(EditAccountActivity.this, "Id number Is Empty", Toast.LENGTH_LONG).show();
                    id_number.setError("Needed *");
                }*/

            }




                //

        });

        Button change=(Button)findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!password.getText().toString().equals("")){
                    if (!new_password.getText().toString().equals("")){
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                AuthCredential credential = EmailAuthProvider
                        .getCredential(user.getEmail(),password.getText().toString() );
                user.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    user.updatePassword(new_password.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(EditAccountActivity.this,"Password updated",Toast.LENGTH_LONG).show();
                                                finish();
                                            } else Toast.makeText(EditAccountActivity.this,"password not updated, New password Not True",Toast.LENGTH_LONG).show();
                                        }
                                    });
                                } else Toast.makeText(EditAccountActivity.this,"Old password Not True, Error auth failed",Toast.LENGTH_LONG).show();
                            }
                        });
                    }else new_password.setError("needed *");
                }else password.setError("needed *");

            }
        });

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}

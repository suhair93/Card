package com.card;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.card.Model.PurchaseModel;
import com.card.Model.userModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class payerActivity extends AppCompatActivity {
    private static final int BARCODE_READER_REQUEST_CODE = 102;
    String uid,Id_n,id_card;

    Double old_p;
    EditText name,price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payer);
        name=(EditText)findViewById(R.id.name);
        price=(EditText)findViewById(R.id.price);

        ImageView barcode_btn=(ImageView)findViewById(R.id.barcode_btn);
        barcode_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    Intent i = new Intent(payerActivity.this,QrCodeActivity.class);
                    startActivityForResult( i,BARCODE_READER_REQUEST_CODE);

                } else {
                    requestPermission();
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode != Activity.RESULT_OK) {
            if(data==null)
                return;
            String result = data.getStringExtra("com.blikoon.qrcodescanner.error_decoding_image");
            if( result!=null)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(payerActivity.this).create();
                alertDialog.setTitle("Scan Error");
                alertDialog.setMessage("QR Code could not be scanned");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            return;

        }
        if(requestCode == BARCODE_READER_REQUEST_CODE) {
            if (data == null)
                return;
            final String result = data.getStringExtra("com.blikoon.qrcodescanner.got_qr_scan_relult");
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd,MMMM,yyyy hh,mm,a", Locale.US);
            final String strDate = sdf.format(c.getTime());


            DatabaseReference Users = database.getReference("Users");

            Users.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    HashMap<String, userModel> results = dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, userModel>>() {
                    });

                    List<userModel> posts = new ArrayList<>(results.values());

                    boolean status=true;
                    for (userModel post : posts) {
                        if (result.equals(post.getUID())) {
                            status=false;
                            old_p = post.getPrice();
                            uid = post.getUID();
                            Id_n = post.getID_number();
                            id_card=post.getID_Card();
                            if (old_p < Double.parseDouble(price.getText().toString())) {
                                Toast.makeText(payerActivity.this, "The operation failed to have enough credit", Toast.LENGTH_LONG).show();
                            } else {
                                final Query query = database.getReference().child("Users").orderByChild("uid").equalTo(result);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot Snapshot : dataSnapshot.getChildren()) {
                                            PurchaseModel post1=new PurchaseModel(result+"",price.getText().toString()+" SAR",strDate);
                                            DatabaseReference posts = database.getReference("purchase");
                                            posts.push().setValue(post1);
                                            Snapshot.getRef().setValue(new userModel(result, (Double) old_p - Double.parseDouble(price.getText().toString()), Id_n, id_card));
                                        }
                                        Toast.makeText(payerActivity.this, "Operation completed successfully", Toast.LENGTH_LONG).show();
                                        }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        databaseError.getMessage();
                                    }
                                });

                            }
                        }
                    }
                    if(status){
                        Toast.makeText(payerActivity.this, "The operation failed", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }
    private static final int PERMISSION_REQUEST_CODE = 200;

    private boolean checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        return true;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }
}

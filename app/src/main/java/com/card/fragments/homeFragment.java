package com.card.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.card.AddCreditActivity;
import com.card.MainActivity;
import com.card.Model.userModel;
import com.card.PurchaseActivity;
import com.card.R;
import com.card.RemainingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class homeFragment extends Fragment {
    View view;
String Price,id_card;
public static TextView name;
    public static void newInstance(String someInt) {
        homeFragment myFragment = new homeFragment();

        if(!someInt.equals(""))
            name.setText(someInt);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
         view= inflater.inflate(R.layout.fragment_home, container, false);
         MainActivity.title.setText(getResources().getString(R.string.home));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference posts = database.getReference("Users");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        name=(TextView)view.findViewById(R.id.name) ;

        posts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, userModel> results = dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, userModel>>() {});
                List<userModel> posts = new ArrayList<>(results.values());
                for (userModel post : posts) {
                    if(user.getUid().equals(post.getUID())){
                        Price= post.getPrice().toString()+" SAR";
                    } }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });
        DatabaseReference Users = database.getReference("Users");
        final TextView id_number=(TextView)view.findViewById(R.id.id_number) ;
        Users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, userModel> results = dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, userModel>>() {});
                List<userModel> posts = new ArrayList<>(results.values());

                for (userModel post : posts) {
                    if(user.getUid().equals(post.getUID())){
                        name.setText(user.getDisplayName());
                        id_card=post.getID_Card();
                        id_number.setText(id_card);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }});


        LinearLayout Remaining=(LinearLayout)view.findViewById(R.id.Remaining);
        Remaining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );

                View views = inflater.inflate(R.layout.activity_remaining, null);
                alertDialog.setView(views);
                final TextView balnce=(TextView)views.findViewById(R.id.balnce);
                balnce.setText(Price);

                Button ok=(Button)views.findViewById(R.id.ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });

        LinearLayout qr_qode=(LinearLayout)view.findViewById(R.id.qr_qode);
        qr_qode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService( Context.LAYOUT_INFLATER_SERVICE );

                View views = inflater.inflate(R.layout.dialoge_code, null);
                alertDialog.setView(views);

                ImageView imageView=(ImageView)views.findViewById(R.id.imageView);

                String text=user.getUid() ;
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    imageView.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                alertDialog.show();
            }
        });

        LinearLayout credit=(LinearLayout)view.findViewById(R.id.credit);
        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AddCreditActivity.class);
                startActivity(i);
            }
        });

        LinearLayout purchase=(LinearLayout)view.findViewById(R.id.purchase);
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PurchaseActivity.class);
                startActivity(i);
            }
        });


        return view;
    }

}

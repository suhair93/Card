package com.card;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.card.Adapter.PurchaseAdapter;
import com.card.Model.PurchaseModel;
import com.card.Model.userModel;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PurchaseActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        ImageView Back_btn=(ImageView)findViewById(R.id.Back_btn);
        Back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final TextView noMember=(TextView)findViewById(R.id.no_member) ;


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference posts = database.getReference("purchase");
        SharedPreferences prefsRemmber = getSharedPreferences("login", MODE_PRIVATE);
        ID = prefsRemmber.getString("ID", "");

    /*    PurchaseModel post1=new PurchaseModel(ID,"3500.0 SAR","10:50 17/05/2018");
        PurchaseModel post2=new PurchaseModel(ID,"2550.0 SAR","11:55 07/08/2018");
        PurchaseModel post3=new PurchaseModel(ID,"250.0 SAR","11:05 02/09/2018");
        PurchaseModel post4=new PurchaseModel(ID,"550.0 SAR","8:00 10/10/2018");
        posts.push().setValue(post1);
        posts.push().setValue(post2);
        posts.push().setValue(post3);
        posts.push().setValue(post4);*/

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(mLayoutManager);
        // GetNotification(Token);



        posts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap<String, PurchaseModel> results = dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, PurchaseModel>>() {});
                List<PurchaseModel> lList = new ArrayList<PurchaseModel>(results.values());

                if(lList.size()==0)
                    noMember.setVisibility(View.VISIBLE);
                else {
                    List<PurchaseModel> lList2 = new ArrayList<PurchaseModel>();

                    for (PurchaseModel post : lList) {
                        if(ID.equals(post.getID())){
                            lList2.add(new PurchaseModel(post.getID(),post.getPrice(),post.getDate()));

                        }
                    }
                    PurchaseAdapter nAdapter = new PurchaseAdapter(PurchaseActivity.this, lList2);
                    recyclerView.setAdapter(nAdapter);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

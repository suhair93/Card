package com.card;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PurchaseActivity extends AppCompatActivity {
    RecyclerView recyclerView;
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
        TextView noMember=(TextView)findViewById(R.id.no_member) ;


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(mLayoutManager);
        // GetNotification(Token);

        List<PurchaseModel> lList = new ArrayList<PurchaseModel>();
        lList.add(new PurchaseModel("300.0 SAR","10:50 17/05/2018"));
        lList.add(new PurchaseModel("250.0 SAR","11:55 07/08/2018"));
        lList.add(new PurchaseModel("20.0 SAR","11:05 02/09/2018"));

        if(lList.size()==0)
            noMember.setVisibility(View.VISIBLE);
        else {
            PurchaseAdapter nAdapter = new PurchaseAdapter(this, lList);
            recyclerView.setAdapter(nAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

        }




    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}

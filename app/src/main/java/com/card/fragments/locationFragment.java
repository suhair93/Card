package com.card.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.card.Adapter.LocationsAdapter;
import com.card.MainActivity;
import com.card.Model.LocationModel;
import com.card.R;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class locationFragment extends Fragment {
    View view;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
         view= inflater.inflate(R.layout.fragment_location, container, false);

        TextView noMember=(TextView)view.findViewById(R.id.no_member) ;


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(mLayoutManager);
        // GetNotification(Token);

        List<LocationModel> lList = new ArrayList<LocationModel>();
        lList.add(new LocationModel("Tamimi Markets","Khaled Bin Alwaleed St. Riyadh, Saudi Arabia , Riyadh"));
        lList.add(new LocationModel("SACO Hardware","Head Office, Takhassusi Main Road, Riyadh"));


        if(lList.size()==0)
            noMember.setVisibility(View.VISIBLE);
        else {
            LocationsAdapter nAdapter = new LocationsAdapter(getContext(), lList);
            recyclerView.setAdapter(nAdapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

        }



        return view;
    }

}

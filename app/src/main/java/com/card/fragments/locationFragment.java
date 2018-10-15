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
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class locationFragment extends Fragment implements OnMapReadyCallback {
    View view;
    //RecyclerView recyclerView;
    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
         view= inflater.inflate(R.layout.activity_maps, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


  /*      TextView noMember=(TextView)view.findViewById(R.id.no_member) ;
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

*/

        return view;
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng origin = new LatLng(24.68773, 46.72185);
        CameraUpdate panToOrigin = CameraUpdateFactory.newLatLng(origin);
        mMap.moveCamera(panToOrigin);
       // BitmapDescriptor ic1 = BitmapDescriptorFactory.fromResource(R.drawable.maintenance);

        MarkerOptions marker = new MarkerOptions()
                .position(origin)
                .title("Riyadh, Saudi Arabia ")
                .snippet("")
                //.icon(ic4)
                ;
        googleMap.addMarker(marker);
        // set zoom level with animation
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 400, null);

        // Add a marker in Sydney and move the camera
    //    LatLng sydney = new LatLng(-34, 151);
      //  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}

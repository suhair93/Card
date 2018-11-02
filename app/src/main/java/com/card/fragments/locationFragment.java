package com.card.fragments;

import android.content.SharedPreferences;
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
import com.card.Model.LocationModelMap;
import com.card.PurchaseActivity;
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

import static android.content.Context.MODE_PRIVATE;


public class locationFragment extends Fragment implements OnMapReadyCallback {
    View view;
    //RecyclerView recyclerView;
    private GoogleMap mMap;
    String ID;
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



        return view;
    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference posts = database.getReference("Location");

        posts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.getValue(LocationModelMap.class);
                HashMap<String, LocationModelMap> results =
                        dataSnapshot.getValue(new GenericTypeIndicator<HashMap<String, LocationModelMap>>() {});
                List<LocationModelMap> lList = new ArrayList<LocationModelMap>(results.values());

                if(lList.size()==0){}
                else {

                    for(int i=0;i<lList.size();i++){
                        if(i==0){
                            LatLng origin = new LatLng(  Double.parseDouble(lList.get(i).getLat()), Double.parseDouble(lList.get(i).getLng()));
                            CameraUpdate panToOrigin = CameraUpdateFactory.newLatLng(origin);
                            mMap.moveCamera(panToOrigin);

                            MarkerOptions marker = new MarkerOptions()
                                    .position(origin)
                                    .title(lList.get(i).getName())
                                    .snippet("")
                                    //.icon(ic4)
                                    ;
                            googleMap.addMarker(marker);
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(8), 400, null);

                        }else {
                            LatLng origin = new LatLng(Double.parseDouble(lList.get(i).getLat()), Double.parseDouble(lList.get(i).getLng()));
                            MarkerOptions marker = new MarkerOptions()
                                    .position(origin)
                                    .title(lList.get(i).getName())
                                    .snippet("")
                                    //.icon(ic4)
                                    ;
                            googleMap.addMarker(marker);
                        }


                    }


                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



       // mMap.animateCamera(CameraUpdateFactory.zoomTo(20), 400, null);

    }

}

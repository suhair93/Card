package com.card.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.card.AboutActivity;
import com.card.AddCreditActivity;
import com.card.EditAccountActivity;
import com.card.FirstActivity;
import com.card.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

import static android.support.v4.app.ActivityCompat.finishAffinity;


public class settingFragment extends Fragment {
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
         view= inflater.inflate(R.layout.fragment_setting, container, false);


        LinearLayout edit_account=(LinearLayout)view.findViewById(R.id.edit_account);
        edit_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), EditAccountActivity.class);
                startActivity(i);
            }
        });
        LinearLayout help=(LinearLayout)view.findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), AboutActivity.class);
                startActivity(i);
            }
        });
        LinearLayout logout=(LinearLayout)view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
                builder2.setMessage("Are you sure you want to sign out?");
                builder2 .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                if(Build.VERSION.SDK_INT>=20 && Build.VERSION.SDK_INT<21){
                                    getActivity().finishAffinity();
                                } else if(Build.VERSION.SDK_INT>=21){
                                    getActivity().finishAndRemoveTask();
                                }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertdialog2 = builder2.create();
                alertdialog2.show();

            }
        });


        return view;
    }

}

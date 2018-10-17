package com.card;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle("About");

        Element versionElement = new Element();
        versionElement.setTitle("Version 1.0");

        Element phoneElement = new Element();
        phoneElement.setIconDrawable(R.drawable.phone_call);
        phoneElement.setTitle("Call us");
        phoneElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_num="+966555106325";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phone_num));
                startActivity(intent); }});

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.wallet_3)
                .addItem(versionElement)
                .addItem(phoneElement)
                .setDescription("E Wallet App")
                .addGroup("Connect with us")
                .addEmail("elmehdi.sakout@gmail.com")
                .addWebsite("http://medyo.github.io/")
                .setCustomFont("fonts/regular.ttf")
                .addFacebook("the.medy")
                .addTwitter("medyo80")
                .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
                .addPlayStore("com.ideashower.readitlater.pro")
              //  .addGitHub("medyo")
                .addInstagram("medyo80")
                .create();
        setContentView(aboutPage);

    }
}

package com.card;

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
        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.wallet_3)
                .addItem(versionElement)
              //  .addItem(adsElement)
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

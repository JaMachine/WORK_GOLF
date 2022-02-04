package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class MainActivity extends AppCompatActivity {
    private String singleton;
    private WebView loading;
    private RelativeLayout loadingScreen;
    private boolean isSet, trueveble = false;
    private ImageView tap;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadingScreen = findViewById(R.id.loading_screen);
        tap = findViewById(R.id.tap);
        loading = findViewById(R.id.loading);
        loading.setWebViewClient(new WebViewClient());
        loading.getSettings().setJavaScriptEnabled(true);
        loading.getSettings().getLoadsImagesAutomatically();
        refresh(0);
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        FirebaseRemoteConfig finalMFirebaseRemoteConfig = mFirebaseRemoteConfig;
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            singleton = finalMFirebaseRemoteConfig.getString("singleton");
                        } else {
                        }
                    }
                });

    }

    private void refresh(int count) {
        count++;
        int finalCount = count;
        if (!isSet) {
            if (singleton != null && singleton.length() > 9) {
                isSet = true;
                loading.loadUrl(singleton);
            }
        }
        if (isSet) {
            if (loading.getUrl().length() > 50) {
                loadingScreen.setVisibility(View.GONE);
                trueveble = true;
            }
        }
        if (finalCount < 60) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    refresh(finalCount);
                }
            }, 100);
        } else {
            if (!trueveble) {
                tap.setVisibility(View.VISIBLE);
                Animation animation = new AlphaAnimation(0, 1);
                animation.setDuration(600);
                animation.setInterpolator(new LinearInterpolator());
                animation.setRepeatCount(Animation.INFINITE);
                animation.setRepeatMode(Animation.REVERSE);
                tap.startAnimation(animation);
                loadingScreen.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                        startActivity(new Intent(getApplicationContext(), Golf.class));
                    }
                });
            }
        }
    }
}
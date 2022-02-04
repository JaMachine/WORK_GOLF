package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    private String singleton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        finish();
//        startActivity(new Intent(getApplicationContext(), Golf.class));
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
        Log.e(TAG, "url = " + singleton + "\n count = " + count);
        int finalCount = count++;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh(finalCount);
            }
        }, 100);
    }
}
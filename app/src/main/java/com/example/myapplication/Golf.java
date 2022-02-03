package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityGolfBinding;

public class Golf extends AppCompatActivity implements Theme {
    private boolean add = true, steady = true;
    private int powerLevel = 0, playerState = 1;
    private ImageView power, player;
    private RelativeLayout screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_golf);
        hideNavigationBar();
        player = findViewById(R.id.player);
        power = findViewById(R.id.power);
        screen = findViewById(R.id.screen);
        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                steady = false;
                hitTheBall();
            }
        });
        powerTimer();
    }

    @Override
    public void hideNavigationBar() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onBackPressed() {
    }

    private void powerTimer() {
        switch (powerLevel) {
            case 0:
                power.setImageResource(R.drawable.power0);
                add = true;
                break;
            case 1:
                power.setImageResource(R.drawable.power1);
                break;
            case 2:
                power.setImageResource(R.drawable.power2);
                break;
            case 3:
                power.setImageResource(R.drawable.power3);
                break;
            case 4:
                power.setImageResource(R.drawable.power4);
                break;
            case 5:
                power.setImageResource(R.drawable.power5);
                break;
            case 6:
                power.setImageResource(R.drawable.power6);
                break;
            case 7:
                power.setImageResource(R.drawable.power7);
                break;
            case 8:
                power.setImageResource(R.drawable.power8);
                break;
            case 9:
                power.setImageResource(R.drawable.power9);
                add = false;
                break;
        }
        if (add) powerLevel++;
        else powerLevel--;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (steady)
                    powerTimer();
            }
        }, 50);
    }

    private void hitTheBall() {
        playerState++;
        if (playerState == 2) {
            player.setImageResource(R.drawable.player2);
        }
        if (playerState == 3) {
            player.setImageResource(R.drawable.player3);
        }
        if (playerState == 4) {
            player.setImageResource(R.drawable.player4);
        }

        if (playerState < 6) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                        hitTheBall();
                }
            }, 7);
        }
    }
}
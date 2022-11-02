package com.example.recyclehome;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_splash);
        ImageView image = findViewById(R.id.imageView);
        TextView text1 = findViewById(R.id.textView);
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("translationY", -800f, 0f);
        ObjectAnimator animation0 = ObjectAnimator.ofPropertyValuesHolder(image, pvhX);
        animation0.setDuration(2000);
        animation0.start();
        ObjectAnimator animation1 = ObjectAnimator.ofPropertyValuesHolder(text1, pvhX);
        animation1.setDuration(2000);
        animation1.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), ScreenSlide.class);
                startActivity(intent);
            }
        }, 3000);
    }
}
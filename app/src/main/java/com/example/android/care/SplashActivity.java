package com.example.android.care;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
/**
 * Created by deepak on 1/19/17.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

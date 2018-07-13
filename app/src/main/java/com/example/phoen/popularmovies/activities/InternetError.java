package com.example.phoen.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.phoen.popularmovies.network.InternetCheck;
import com.example.phoen.popularmovies.R;

public class InternetError extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_error);
    }

    public void retry(View view) {
        new InternetCheck(this, "com.example.phoen.popularmovies.MainActivity", false).execute();
    }

}

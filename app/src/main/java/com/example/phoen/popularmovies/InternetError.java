package com.example.phoen.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

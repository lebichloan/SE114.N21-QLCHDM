package com.example.se114n21.ViewModels;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.se114n21.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitleToolbar();
    }

    private void setTitleToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Main Activity");
        }
    }
}
package com.rdeluca118.step3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    private String p1Name;
    private String p2Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Bundle extras = getIntent().getExtras();
        String p1Name = extras.getString("p1name");
        String p2Name = extras.getString("p2name");


    }
}

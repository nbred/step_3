package com.rdeluca118.step3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doNewGame(View v){
        startActivity(new Intent(this, newgame_activity.class));
    }

    public void doDatabase(View v){
        startActivity(new Intent(this, gameListActivity.class));
    }

}

package com.rdeluca118.step3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;

public class
MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void doNewGame(View v) {
        startActivity(new Intent(this, newgame_activity.class));
    }

    public void doDatabase(View v) {
        startActivity(new Intent(this, gameListActivity.class));
    }

    public void doQuit(View v) {
        /*int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        System.exit(0);*/
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}

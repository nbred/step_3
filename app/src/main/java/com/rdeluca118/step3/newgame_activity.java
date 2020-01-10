package com.rdeluca118.step3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import java.lang.String;

public class newgame_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgame_activity);

    }

    public void startGame(View v){

        EditText p1 = findViewById(R.id.player1Name);
        String p1name = p1.getText().toString();
        EditText p2 = findViewById(R.id.player2Name);
        String p2name = p2.getText().toString();
        Intent i = new Intent(this, GameActivity.class);

    }
}

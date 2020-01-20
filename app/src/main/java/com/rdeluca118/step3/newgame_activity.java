package com.rdeluca118.step3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.content.Intent;
import android.database.Cursor;

import java.lang.String;

public class newgame_activity extends AppCompatActivity {
    DBManager db;
    SimpleCursorAdapter sca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgame_activity);
        db = new DBManager(this);
        db.open();

        final Cursor mycursor = db.fetch_players();

        String[] from = new String[]{"name"};
        int[] to = new int[]{android.R.id.text1};
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.spinner_view, mycursor, from, to, 0);

        Spinner spin1 = this.findViewById(R.id.spinner1);
        spin1.setAdapter(sca);

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EditText p1 = findViewById(R.id.player1Name);
                p1.setText(mycursor.getString(1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                EditText p1 = findViewById(R.id.player1Name);
                p1.setText("");
            }
        });

        Spinner spin2 = this.findViewById(R.id.spinner2);
        spin2.setAdapter(sca);

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EditText p2 = findViewById(R.id.player2Name);
                p2.setText(mycursor.getString(1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                EditText p2 = findViewById(R.id.player2Name);
                p2.setText("");
            }
        });
        /*
        mycursor.close();
        */
    }

    public void startGame(View v) {

        EditText p1 = findViewById(R.id.player1Name);
        String p1name = p1.getText().toString();
        EditText p2 = findViewById(R.id.player2Name);
        String p2name = p2.getText().toString();

        this.db.insert_player(p1name);
        this.db.insert_player(p2name);

        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("p1name", p1name);
        i.putExtra("p2name", p2name);
        startActivity(i);
    }
}

package com.rdeluca118.step3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
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
    private DBManager db;
    private String curType;
    private int typeSel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgame_activity);

        db = new DBManager(this);
        db.open();

//        Resources res = getResources();
//        types = res.getStringArray(R.array.game_type);

        final Cursor mycursor = db.fetch_players();

        String[] from = new String[]{"name"};
        int[] to = new int[]{android.R.id.text1};
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, R.layout.spinner_view, mycursor, from, to, 0);

        Spinner spin1 = this.findViewById(R.id.spinner1);
        spin1.setAdapter(sca);

        spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private Boolean changed = false;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!changed) {
                    changed = true;
                } else {
                    EditText p1 = findViewById(R.id.player1Name);
                    p1.setText(mycursor.getString(1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner spin2 = this.findViewById(R.id.spinner2);
        spin2.setAdapter(sca);

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private Boolean changed = false;

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!changed) {
                    changed = true;
                } else {
                    EditText p2 = findViewById(R.id.player2Name);
                    p2.setText(mycursor.getString(1));
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final Spinner gType = this.findViewById(R.id.game_legs);
        gType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                curType = parent.getItemAtPosition(position).toString();
                typeSel = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void startGame(View v) {
        int max;

        EditText p1 = findViewById(R.id.player1Name);
        String p1name = p1.getText().toString();
        EditText p2 = findViewById(R.id.player2Name);
        String p2name = p2.getText().toString();

        this.db.insert_player(p1name);
        this.db.insert_player(p2name);

        switch (typeSel) {
            case 1:
                max = 3;
                break;
            case 2:
                max = 5;
                break;
            case 3:
                max = 7;
                break;
            case 4:
                max = 9;
                break;
            case 5:
                max = 11;
                break;
            case 6:
                max = 13;
                break;
            case 7:
                max = 15;
                break;
            default:
                max = 1;
        }

        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("p1name", p1name);
        i.putExtra("p2name", p2name);
        i.putExtra("typesel", max);

        startActivity(i);
    }
}

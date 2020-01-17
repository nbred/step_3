package com.rdeluca118.step3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.database.Cursor;
import java.lang.String;

public class newgame_activity extends AppCompatActivity {
    DBManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgame_activity);
        db = new DBManager(this);
        db.open();

        EditText  playerList = findViewById(R.id.player_list);
        Cursor c = db.fetch_players();
        if(c != null){
            int i = c.getCount();
            do {
                String s = c.getString(c.getColumnIndex("name"));
                playerList.append(s + '\n');
            } while( c.moveToNext());
            c.close();
        }
        //db.close();
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

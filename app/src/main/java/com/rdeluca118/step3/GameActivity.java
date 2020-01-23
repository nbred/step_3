package com.rdeluca118.step3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.view.ViewGroup;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.RadioButton;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;
import android.media.ToneGenerator;
import android.media.AudioManager;

public class GameActivity extends AppCompatActivity {

    private Player playerOne;
    private Player playerTwo;
    private Game theGame;
    private int dartOrd, tTotal;
    private int[] darts = {0, 0, 0, 0};
    private int col1Value, col2Value;
    TextView totView, p1, p2, curdartview;
    TextView col1Score, col2Score, curCol, startCol;
    RadioGroup theRadioGroup;
    Context context;
    private boolean haveNumber;
//    private DatabaseHelper databaseHelper;
    DBManager dbm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        context = this;
        dbm = new DBManager(this);
        dbm.open();

        Intent intent = getIntent();
        playerOne = new Player(intent.getStringExtra("p1name"));
        playerOne.setId(dbm.getPlayerId(playerOne.getName()));

        playerTwo = new Player(intent.getStringExtra("p2name"));
        playerTwo.setId(dbm.getPlayerId(playerTwo.getName()));

        p1 = findViewById(R.id.p1);
        p1.setText(playerOne.getName());
        p2 = findViewById(R.id.p2);
        p2.setText(playerTwo.getName());

        theGame = new Game(playerOne, playerTwo, 5);
        dbm.insert_game(theGame);

        ViewGroup layout = findViewById(R.id.left_pane);
        disableEnableControls(false, layout);

        theRadioGroup = findViewById(R.id.rg_dt);

        col1Score = findViewById(R.id.p1score);
        col2Score = findViewById(R.id.p2score);
        curCol = col1Score;

        haveNumber = false;
    }

    public void numberClicked(View v) {

        //scratch
        int scratch = v.getId();
        Button button = findViewById(R.id.button00);
        int xxx = button.getId();
        if (xxx == scratch) {
            darts[dartOrd] = 0;
            haveNumber = true;
            Button child = findViewById(R.id.button_count);
            child.setEnabled(true);
            return;
        }

        Button b = (Button) v;
        String buttonText = b.getText().toString();
        try {
            int buttonValue = Integer.parseInt(buttonText);
            darts[dartOrd] = buttonValue;
            haveNumber = true;
            Button child = findViewById(R.id.button_count);
            child.setEnabled(true);

        } catch (NumberFormatException nfe) {
            Log.i("NUMBER", "Couldn't parse.");
            nfe.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doNewLeg(View v) {

        ViewGroup layout;

        TextView etp1 = findViewById(R.id.p1);
        etp1.setText(playerOne.getName());

        TextView etp2 = findViewById(R.id.p2);
        etp2.setText(playerTwo.getName());

        //etp1.setActivated(false);
        etp1.setEnabled(false);
        //etp2.setActivated(false);
        etp2.setEnabled(false);

        layout = findViewById(R.id.left_pane);
        disableEnableControls(true, layout);
        layout = findViewById(R.id.option_pane);
        disableEnableControls(true, layout);

        tTotal = 0;
        dartOrd = 1;
        col1Value = 501;
        col2Value = 501;
        if (startCol == col1Score) {
            curCol = col2Score;
        } else {
            curCol = col1Score;
        }
        startCol = curCol;
    }

    public void taskFailed() {
        Toast.makeText(getApplicationContext(),
                "Can not have TRIPLE 25",
                Toast.LENGTH_SHORT).show();

        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 500);
    }

    public void countShot(View v) {
        TextView view;

        if (haveNumber) {
            //isDouble = true;
            RadioButton doubleOpt = findViewById(R.id.radio_double);
            if (doubleOpt.isChecked()) {
                int x = darts[dartOrd] * 2;
                darts[dartOrd] = x;
                theRadioGroup.clearCheck();
            }

            //isTriple = true;
            RadioButton tripleOpt = findViewById(R.id.radio_triple);
            if (tripleOpt.isChecked()) {
                if (darts[dartOrd] == 25) {
                    // no TRIPLE 25
                    taskFailed();
                    theRadioGroup.clearCheck();
                } else {
                    int x = darts[dartOrd] * 3;
                    darts[dartOrd] = x;
                    theRadioGroup.clearCheck();
                }
            }

            totView = findViewById(R.id.turn);

            switch (dartOrd) {
                case 1:
                    view = findViewById(R.id.dart1);
                    view.setText(String.valueOf(darts[dartOrd]));
                    curdartview = view;
                    tTotal = darts[1];
                    totView.setText(String.valueOf(tTotal));
                    break;
                case 2:
                    view = findViewById(R.id.dart2);
                    view.setText(String.valueOf(darts[dartOrd]));
                    curdartview = view;
                    tTotal = darts[1] + darts[2];
                    totView.setText(String.valueOf(tTotal));
                    break;
                case 3:
                    view = findViewById(R.id.dart3);
                    view.setText(String.valueOf(darts[dartOrd]));
                    curdartview = view;
                    tTotal = darts[1] + darts[2] + darts[3];
                    totView.setText(String.valueOf(tTotal));

                    view =  findViewById(R.id.button_count);
                    view.setEnabled(false);
                    view =  findViewById(R.id.button_post);
                    view.setEnabled(true);
            }
            curdartview.setBackgroundColor(0xFF00FF00);
            dartOrd++;
            haveNumber = false;
            //Button child = findViewById(R.id.button_count);
            //child.setEnabled(false);
        }
    }
    public void doReset(View v) {
        TextView view;

        darts[1] = 0;
        darts[2] = 0;
        darts[3] = 0;
        dartOrd = 1;
        view = findViewById(R.id.dart1);
        view.setBackgroundColor(0x00000000);
        view.setText("0");
        view = findViewById(R.id.dart2);
        view.setBackgroundColor(0x00000000);
        view.setText("0");
        view = findViewById(R.id.dart3);
        view.setBackgroundColor(0x00000000);
        view.setText("0");
        totView.setText("0");
        theRadioGroup.clearCheck();
    }

    public void doScratch(View v) {
        TextView view;
        switch (dartOrd) {
            case 1:
                view = findViewById(R.id.dart1);
                view.setBackgroundColor(0xFF00FF00);
                break;
            case 2:
                view = findViewById(R.id.dart2);
                view.setBackgroundColor(0xFF00FF00);
                break;
            case 3:
                view = findViewById(R.id.dart3);
                view.setBackgroundColor(0xFF00FF00);
        }
        darts[dartOrd] = 0;
        dartOrd++;
    }

    public void doBust(View v) {
        tTotal = 0;
        switchPlayer(v);
    }

    public void switchPlayer(View v) {
        TextView view;

        darts[1] = 0;
        darts[2] = 0;
        darts[3] = 0;
        dartOrd = 1;
        view = findViewById(R.id.dart1);
        view.setBackgroundColor(0x00000000);
        view.setText("0");
        view = findViewById(R.id.dart2);
        view.setBackgroundColor(0x00000000);
        view.setText("0");
        view = findViewById(R.id.dart3);
        view.setBackgroundColor(0x00000000);
        view.setText("0");
        totView.setText("0");

        if (curCol == col1Score) {
            col1Value -= tTotal;
            curCol.append("\n" + tTotal + " : " + col1Value);
            if(col1Value < 1){
                //leg over ================================================================
            }
            curCol = col2Score;
        } else {
            col2Value -= tTotal;
            curCol.append("\n" + tTotal + " : " + col2Value);
            if(col2Value < 1){
                //leg over ================================================================
            }
            curCol = col1Score;
        }
        tTotal = 0;

        view =  findViewById(R.id.button_count);
        view.setEnabled(true);
        view =  findViewById(R.id.button_post);
        view.setEnabled(false);
    }

    private void disableEnableControls(boolean enable, ViewGroup vg) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup) {
                disableEnableControls(enable, (ViewGroup) child);
            }
        }
    }

    public void shutDown(View v) {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}


package com.rdeluca118.step3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
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
    private Leg currentLeg;
    private int currentPlayerId;
    private Turn currentTurn;
    private int dartOrd, tTotal;
    private int[] darts = {0, 0, 0, 0};
    private int col1Value, col2Value;
    private TextView totView, p1, p2, curdartview, legsView;
    private TextView col1Score, col2Score, curCol;
    private RadioGroup theRadioGroup;
    private boolean haveNumber;
    private DBManager dbm;
    private String[] finishes;
    private int maxLegs;
    private int legsPlayed;
    private int turnCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //context = this;
        dbm = new DBManager(this);
        dbm.open();

        Resources res = getResources();
        finishes = res.getStringArray(R.array.finishes);

        Intent intent = getIntent();
        playerOne = new Player(intent.getStringExtra("p1name"));
        playerOne.setId(dbm.getPlayerId(playerOne.getName()));

        playerTwo = new Player(intent.getStringExtra("p2name"));
        playerTwo.setId(dbm.getPlayerId(playerTwo.getName()));

        maxLegs = intent.getIntExtra("typesel", 1);

        p1 = findViewById(R.id.p1);
        p1.setText(playerOne.getName());
        p2 = findViewById(R.id.p2);
        p2.setText(playerTwo.getName());
        p1.setEnabled(false);
        p2.setEnabled(false);

        theGame = new Game(playerOne, playerTwo, maxLegs);
        dbm.insert_game(theGame);

        legsView = findViewById(R.id.leg_text);
        legsView.setText("Best of " + String.valueOf(maxLegs));

        ViewGroup layout = findViewById(R.id.left_pane);
        disableEnableControls(false, layout);

        theRadioGroup = findViewById(R.id.rg_dt);

        col1Score = findViewById(R.id.p1score);
        col2Score = findViewById(R.id.p2score);
        curCol = col2Score;
        //curCol = col1Score;

        haveNumber = false;
        currentPlayerId = -1;
        legsPlayed = 0;
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

        Button b1 = findViewById(R.id.button_Finish);
        b1.setEnabled(false);
    }

    public void doNewLeg(View v) {
        ViewGroup layout;
        View view;
        TextView pName;

        layout = findViewById(R.id.left_pane);
        disableEnableControls(true, layout);
        //layout = findViewById(R.id.option_pane);
        //disableEnableControls(true, layout);

        currentLeg = new Leg(theGame.getId());
        dbm.insert_leg(currentLeg);

        tTotal = 0;
        dartOrd = 1;
        col1Value = 501;
        col2Value = 501;
        turnCount = 0;

//        if (startCol == col1Score) {
//            curCol = col2Score;
//            currentPlayerId = playerTwo.getId();
//        } else {
//            curCol = col1Score;
//            if (currentPlayerId > 0) {
//                currentPlayerId = playerOne.getId();
//            }
//        }
        view = findViewById(R.id.button_count);
        view.setEnabled(true);
        view = findViewById(R.id.button_post);
        view.setEnabled(false);

        legsPlayed++;
        if (legsPlayed % 2 > 0) {     // odd leg
            currentPlayerId = playerOne.getId();
            curCol = col1Score;
            pName = findViewById(R.id.p1);
        } else {                      // even leg
            currentPlayerId = playerTwo.getId();
            curCol = col2Score;
            pName = findViewById(R.id.p1);
        }
        pName.setBackgroundResource(R.color.colorAccent);
        currentLeg.setHammer(currentPlayerId);

        Resources res = getResources();
        String text = res.getString(R.string.leg_message, legsPlayed, maxLegs);
        legsView.setText(text);

        currentTurn = new Turn(currentPlayerId, currentLeg.getLegId());
        //switchPlayer(v);
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
                    currentTurn.setDart(1, darts[1]);
                    totView.setText(String.valueOf(tTotal));
                    break;
                case 2:
                    view = findViewById(R.id.dart2);
                    view.setText(String.valueOf(darts[dartOrd]));
                    curdartview = view;
                    tTotal = darts[1] + darts[2];
                    currentTurn.setDart(2, darts[2]);
                    totView.setText(String.valueOf(tTotal));
                    break;
                case 3:
                    view = findViewById(R.id.dart3);
                    view.setText(String.valueOf(darts[dartOrd]));
                    curdartview = view;
                    tTotal = darts[1] + darts[2] + darts[3];
                    currentTurn.setDart(3, darts[3]);
                    totView.setText(String.valueOf(tTotal));

                    view = findViewById(R.id.button_count);
                    view.setEnabled(false);
                    view = findViewById(R.id.button_post);
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

        totView = findViewById(R.id.turn);
        totView.setText("0");

        col1Score.setText("501");
        col2Score.setText("501");

        findViewById(R.id.rg_dt);
        theRadioGroup.clearCheck();
    }

    public void doBust(View v) {
        tTotal = 0;
        switchPlayer(v);
    }

    public void postFinish(View v) {
        TextView view;

        int maxLegs = theGame.getNumLegs();
        //legsPlayed++;
        if (maxLegs >= legsPlayed) {
            doReset(v);
        } else {
            //GAME OVER
        }

        int r = dbm.update_leg(currentLeg.getLegId(), currentPlayerId);

        view = findViewById(R.id.p1);
        view.setBackgroundResource(0);
        view = findViewById(R.id.p2);
        view.setBackgroundResource(0);
    }

    public void doPost(View v) {
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
        totView = findViewById(R.id.turn);
        totView.setText("0");

        if (curCol == col1Score) {
            col1Value -= tTotal;
            curCol.append("\n" + tTotal + " : " + col1Value);
            if (col1Value == 0) {
                //leg over ================================================================
                enableFinish();
            }
        } else {
            col2Value -= tTotal;
            curCol.append("\n" + tTotal + " : " + col2Value);
            if (col2Value == 0) {
                //leg over ================================================================
                enableFinish();
            }
        }
        tTotal = 0;

        if (col1Value > 0 && col2Value > 0) {
            view = findViewById(R.id.button_count);
            view.setEnabled(true);
            view = findViewById(R.id.button_post);
            view.setEnabled(false);
        }

        switchPlayer(v);
    }

    private void enableFinish() {
        View view;

        view = findViewById(R.id.button_count);
        view.setEnabled(false);
        view = findViewById(R.id.button_post);
        view.setEnabled(false);
        Button b = findViewById(R.id.button_Finish);
        b.setEnabled(true);
    }

    public void switchPlayer(View v) {
        String checkOut;
        TextView pNmae;
        TextView old;

        turnCount++;
        // ###############################################################
        if (curCol == col2Score) {
            curCol = col1Score;
            currentPlayerId = playerOne.getId();
            checkOut = canCheckOut(col1Value);
            pNmae = findViewById(R.id.p1);
            old = findViewById(R.id.p2);
        } else {
            curCol = col2Score;
            currentPlayerId = playerTwo.getId();
            checkOut = canCheckOut(col2Value);
            pNmae = findViewById(R.id.p2);
            old = findViewById(R.id.p1);
        }

        old.setBackgroundResource(0);
        pNmae.setBackgroundResource(R.color.colorAccent);

        if (currentTurn != null) {
            dbm.insert_turn(currentTurn);
        }
        currentTurn = new Turn(currentPlayerId, currentLeg.getLegId());

        TextView checkout = findViewById(R.id.tofinish);
        checkout.setText(checkOut);
    }

    private String canCheckOut(int score) {
        String result;
        int base = 170;
        result = "";

        if (score <= base) {
            int c = finishes.length;
            int i = (base - c) + 1;
            if (score > i) {
                result = finishes[score - i];
            }
        }
        return result;
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

    private void doCleanUp() {
//        private Game theGame;
//        private Leg currentLeg;
//        private Turn currentTurn;
        dbm.update_leg(currentLeg.getLegId(), -1);

    }

    public void shutDown(View v) {
        doCleanUp();
        Intent homeIntent = new Intent(this, MainActivity.class);
        startActivity(homeIntent);
    }
}

//        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
//        homeIntent.addCategory(Intent.CATEGORY_HOME);
//        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

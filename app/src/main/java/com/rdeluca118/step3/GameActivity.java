package com.rdeluca118.step3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.RadioGroup;
import android.view.ViewGroup;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.RadioButton;
import android.content.Intent;
import android.content.Context;

public class GameActivity extends AppCompatActivity {

    private String p1Name;
    private String p2Name;
    private int dartOrd, tTotal;
    private int[] darts = {0, 0, 0, 0};
    private int col1Value, col2Value;
    TextView totView, p1, p2;
    TextView col1Score, col2Score = findViewById(R.id.p2score), curCol, startCol;
    RadioGroup theRadioGroup;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        context = this;

        Intent intent = getIntent();
        p1Name = intent.getStringExtra("p1name");
        p2Name = intent.getStringExtra("p2name");

        ViewGroup layout = findViewById(R.id.left_pane);
        disableEnableControls(false, layout);

        //Typeface myFont = Typeface.createFromAsset(getAssets(), "fonts/Stainy.ttf");
        Typeface chalk = Typeface.createFromAsset(getAssets(), "fonts/LC Chalk.ttf");

        p1 = findViewById(R.id.p1);
        p1.setTypeface(chalk);
        p2 = findViewById(R.id.p2);
        p2.setTypeface(chalk);

        col1Score = findViewById(R.id.p1score);
        col1Score.setTypeface(chalk);
        col2Score = findViewById(R.id.p2score);
        col2Score.setTypeface(chalk);

        theRadioGroup = findViewById(R.id.rg_dt);
        col1Score = findViewById(R.id.p1score);
        col2Score = findViewById(R.id.p2score);
        curCol = col1Score;
    }

    public void numberClicked(View v) {
        Button b = (Button) v;
        String buttonText = b.getText().toString();
        try {
            int buttonValue = Integer.parseInt(buttonText);
            darts[dartOrd] = buttonValue;

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
        etp1.setText(p1Name);

        TextView etp2 = findViewById(R.id.p2);
        etp2.setText(p2Name);

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

    public void countShot(View v) {
        //scratch
        int scratch = v.getId();
        Button button = findViewById(R.id.button00);
        int xxx = button.getId();
        if (xxx == scratch) {
            darts[dartOrd] = 0;
        }

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
                //      ERROR ====================================================
                theRadioGroup.clearCheck();
            } else {
                int x = darts[dartOrd] * 3;
                darts[dartOrd] = x;
                theRadioGroup.clearCheck();
            }
        }

        TextView view;
        totView = findViewById(R.id.turn);

        switch (dartOrd) {
            case 1:
                view = findViewById(R.id.dart1);
                view.setText(String.valueOf(darts[dartOrd]));

                tTotal = darts[1];
                totView.setText(String.valueOf(tTotal));
                break;
            case 2:
                view = findViewById(R.id.dart2);
                view.setText(String.valueOf(darts[dartOrd]));

                tTotal = darts[1] + darts[2];
                totView.setText(String.valueOf(tTotal));
                break;
            case 3:
                view = findViewById(R.id.dart3);
                view.setText(String.valueOf(darts[dartOrd]));

                tTotal = darts[1] + darts[2] + darts[3];
                totView.setText(String.valueOf(tTotal));
                break;
        }
        dartOrd++;
    }

    public void doReset(View v) {
        darts[dartOrd] = 0;
        theRadioGroup.clearCheck();
    }

    public void doScratch(View v) {
        countShot(v);
        //darts[dartOrd] = 0;
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
        view.setText("0");
        view = findViewById(R.id.dart2);
        view.setText("0");
        view = findViewById(R.id.dart3);
        view.setText("0");
        totView.setText("0");

        if (curCol == col1Score) {
            col1Value -= tTotal;
            curCol.append("\n" + tTotal + " : " + col1Value);
            curCol = col2Score;
        } else {
            col2Value -= tTotal;
            curCol.append("\n" + tTotal + " : " + col2Value);
            curCol = col1Score;
        }
        tTotal = 0;
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


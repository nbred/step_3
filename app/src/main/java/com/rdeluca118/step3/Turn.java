package com.rdeluca118.step3;
// Turn Table columns
//public static final String TURN_ID = "_id";
//public static final String player_id = "player_id";
//public static final String leg_id = "leg_id";
//public static final String dart_one = "dart_1";
//public static final String dart_two = "dart_2";
//public static final String dart_three = "dart_3";

public class Turn {
    private int turnID;
    private int playerID;
    private int legID;
    private int dartOne;
    private int dartTwo;
    private int dartThree;

    public Turn(int pID, int lid) {
        this.playerID = pID;
        this.legID = lid;
    }

    public void setDart(int theDart, int valu) {
        switch (theDart) {
            case 1:
                dartOne = valu;
                break;
            case 2:
                dartTwo = valu;
                break;
            case 3:
                dartThree = valu;
        }
    }
}

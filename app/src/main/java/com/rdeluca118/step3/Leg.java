package com.rdeluca118.step3;

public class Leg {
    private int legID;
    private int gameID;

    public Leg(int gameid) {
        this.gameID = gameid;
    }

    public int getGameId() {
        return this.gameID;
    }

    public void setId(int aid) {
        this.legID = aid;
    }

    public int getLegId() {
        return this.legID;
    }
}

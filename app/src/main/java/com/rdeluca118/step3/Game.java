package com.rdeluca118.step3;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Game {

    private int game_id;
    private String GameDate;
    private Player Player1, Player2;
    private int legs;

    public Game(Player p1, Player p2, int maxlegs){
        Date Game_date;
        Player1 = p1;
        Player2 = p2;

        legs = maxlegs;

        Game_date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        GameDate = sdf.format(Game_date);
    }

    public String getDate(){
        return GameDate;
    }

    public int[] getPlayersIds(){
        int[] p = {Player1.getId(), Player2.getId()};
        return p;
    }
    public int getNumLegs(){
        return legs;
    }
    public void setId(int num){
        game_id = num;
    }
}

package com.rdeluca118.step3;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Game {

    private Date Game_date;
    private Player Player1, Player2;
    private Integer legs;

    public void Game(String p1, String p2, Integer maxlegs){

        Player1 = new Player(p1);
        Player2 = new Player(p2);

        legs = maxlegs;

        Game_date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String currentDateandTime = sdf.format(Game_date);
    }
}
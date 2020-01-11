package com.rdeluca118.step3;

public class Player {

    private String name;
    private Integer game_id;

    public Player(String pName) {
        name = pName;
        game_id = 1;
    }

    // method to set the name
    public void setName(String name) {
        this.name = name;
    }

    // method to retrieve the name
    public String getName() {
        return name;
    }
}



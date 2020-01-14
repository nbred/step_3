package com.rdeluca118.step3;

public class Player {

    private String name;
    private int id;
    private DBManager db;

    public Player(String pName) {

        name = pName;

        db.insert_player(name);
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



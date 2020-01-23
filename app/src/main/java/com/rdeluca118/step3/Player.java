package com.rdeluca118.step3;

public class Player {

    private String name;
    private int id;

    public Player(String pName) {
        setName(pName);
    }

    // method to set the name
    public void setName(String name) {
        this.name = name;
    }

    // method to retrieve the name
    public String getName() {
        return name;
    }

    public void setId(int aid) {
        this.id = aid;
    }
    public int getId(){
        return this.id;
    }
}



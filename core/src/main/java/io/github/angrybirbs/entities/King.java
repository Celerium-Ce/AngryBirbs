package io.github.angrybirbs.entities;

public class King extends Pig {
    private int power;

    public King(int x, int y) {
        super("entities/KingPig.png",x,y);
        this.power = 10;
    }

    public int getPower() {
        return power;
    }
}

package io.github.angrybirbs.entities;

public class Normal extends Pig {
    private int power;

    public Normal(int x, int y) {
        super("entities/NormalPig.png",x,y);
        this.power = 10;
    }

    public int getPower() {
        return power;
    }
}

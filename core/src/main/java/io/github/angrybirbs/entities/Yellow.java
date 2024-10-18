package io.github.angrybirbs.entities;

public class Yellow extends Bird {
    private int power;

    public Yellow(int x, int y) {
        super("entities/YellowBird.png",x,y);
        this.power = 10;
    }

    public int getPower() {
        return power;
    }
}

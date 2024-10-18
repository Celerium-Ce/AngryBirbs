package io.github.angrybirbs.entities;

public class Blue extends Bird {
    private int power;

    public Blue(int x, int y) {
        super("entities/BlueBird.png",x,y);
        this.power = 10;
    }

    public int getPower() {
        return power;
    }
}

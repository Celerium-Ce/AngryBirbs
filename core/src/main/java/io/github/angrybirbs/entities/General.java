package io.github.angrybirbs.entities;

public class General extends Pig {
    private int power;

    public General(int x, int y) {
        super("entities/GeneralPig.png",x,y);
        this.power = 10;
    }

    public int getPower() {
        return power;
    }
}

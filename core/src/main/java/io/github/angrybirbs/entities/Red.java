package io.github.angrybirbs.entities;

public class Red extends Bird {
    private int power;

    public Red(int x, int y) {
        super("entities/RedBird.png",x,y);
        this.power = 10;
    }

    public int getPower() {
        return power;
    }
}

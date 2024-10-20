package io.github.angrybirbs.entities;

import com.badlogic.gdx.physics.box2d.World;

public class Red extends Bird {
    private int power;

    public Red(World world, int x, int y) {
        super(world,"entities/RedBird.png",x,y);
        this.power = 10;
    }

    public int getPower() {
        return power;
    }
}

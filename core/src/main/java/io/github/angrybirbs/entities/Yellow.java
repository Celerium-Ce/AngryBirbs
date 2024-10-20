package io.github.angrybirbs.entities;

import com.badlogic.gdx.physics.box2d.World;

public class Yellow extends Bird {
    private int power;

    public Yellow(World world, int x, int y) {
        super(world,"entities/YellowBird.png",x,y);
        this.power = 10;
    }

    public int getPower() {
        return power;
    }
}

package io.github.angrybirbs.entities;

import com.badlogic.gdx.physics.box2d.World;

public class Blue extends Bird {
    private int power;

    public Blue(World world, int x, int y) {
        super(world,"entities/BlueBird.png",x,y);
        this.power = 10;
    }

    public int getPower() {
        return power;
    }
}

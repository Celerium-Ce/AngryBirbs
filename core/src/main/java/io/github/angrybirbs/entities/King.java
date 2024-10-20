package io.github.angrybirbs.entities;

import com.badlogic.gdx.physics.box2d.World;

public class King extends Pig {
    private int power;

    public King(World world, int x, int y) {
        super(world,"entities/KingPig.png",x,y);
        this.power = 10;
    }

    public int getPower() {
        return power;
    }
}

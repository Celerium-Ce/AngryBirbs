package io.github.angrybirbs.entities;

import com.badlogic.gdx.physics.box2d.World;

public class Normal extends Pig {
    private int power;

    public Normal(World world, int x, int y) {
        super(world,"entities/NormalPig.png",x,y);
        this.power = 10;
    }

    public int getPower() {
        return power;
    }
}

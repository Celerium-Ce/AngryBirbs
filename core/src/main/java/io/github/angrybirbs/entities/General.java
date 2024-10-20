package io.github.angrybirbs.entities;

import com.badlogic.gdx.physics.box2d.World;

public class General extends Pig {
    private int power;

    public General(World world,int x, int y) {
        super(world,"entities/GeneralPig.png",x,y);
        this.power = 10;
    }

    public int getPower() {
        return power;
    }
}

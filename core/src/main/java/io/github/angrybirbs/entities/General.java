package io.github.angrybirbs.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.World;

public class General extends Pig {
    private int power;

    public General(World world, TiledMapTile tile, float x, float y) {
        super(world,tile,x,y);
        this.power = 10;
    }

    public int getPower() {
        return power;
    }
}

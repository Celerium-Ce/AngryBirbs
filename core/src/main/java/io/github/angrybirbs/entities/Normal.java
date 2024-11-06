package io.github.angrybirbs.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.World;

public class Normal extends Pig {
    private int power;

    public Normal(World world, TiledMapTile tile, float x, float y) {
        super(world,tile,x,y);
        this.power = 10;
    }

    public int getPower() {
        return power;
    }
}

package io.github.angrybirbs.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.World;

public class Steel extends Material {
    public Steel(TiledMapTile tile, float x, float y, World world) {
        super(tile, x, y,world);
    }
}

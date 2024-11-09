package io.github.angrybirbs.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.World;

public class Wood extends Material {
    private float health = 10f;
    public Wood(TiledMapTile tile, float x, float y, World world) {
        super(tile, x, y,world, 4f, 0.3f, 0.05f); // dencity, friction, restitution
    }

    @Override
    public void takeDamage(float damage){
        health-=damage;
        if (health <= 0){
           setDead();
        }
        System.out.println("Wood took " + damage + " damage and has " + health + " health left");
    }
}


package io.github.angrybirbs.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.World;

public class Steel extends Material {
    private float health = 15f;

    public Steel(TiledMapTile tile, float x, float y, World world) {
        super(tile, x, y,world, 30.0f, 0.5f, 0.02f); // dencity, friction, restitution
    }

    @Override
    public void takeDamage(float damage){
        health-=damage;
        if (health <= 0){
            setDead();
        }
        System.out.println("Steel took " + damage + " damage and has " + health + " health left");
    }

    @Override
    public float getHealth(){
        return health;
    }
}


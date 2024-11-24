package io.github.angrybirbs.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.World;

public class Ice extends Material {
    private float health = 3f;

    public Ice(TiledMapTile tile, float x, float y, World world) {
        super(tile, x, y,world, 2.5f, 0.1f, 0.02f); // dencity, friction, restitution
    }

    @Override
    public void takeDamage(float damage){
        health-=damage;
        if (health <= 0){
            setDead();
        }
        System.out.println("Ice took " + damage + " damage and has " + health + " health left");
    }

    @Override
    public float getHealth(){
        return health;
    }
    @Override
    public void setHealth(float health){
        this.health = health;
    }
}


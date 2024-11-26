package io.github.angrybirbs.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.World;

public class Ice extends Material {
    private float health;

    public Ice(TiledMapTile tile, float x, float y, World world) {
        super(tile, x, y,world, 2.5f, 0.1f, 0.02f); // density, friction, restitution
        this.health = 3f;
    }

    @Override
    public void takeDamage(float damage){
        setHealth(getHealth() - damage);
        if (getHealth() <= 0){
            setDead();
        }
        System.out.println("Ice took " + damage + " damage and has " + getHealth() + " health left");
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


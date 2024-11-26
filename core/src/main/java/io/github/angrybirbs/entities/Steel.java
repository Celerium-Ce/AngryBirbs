package io.github.angrybirbs.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.World;

public class Steel extends Material {
    private float health;

    public Steel(TiledMapTile tile, float x, float y, World world) {
        super(tile, x, y,world, 30.0f, 0.5f, 0.02f); // density, friction, restitution
        this.health = 15f;
    }

    @Override
    public void takeDamage(float damage){
        setHealth(getHealth() - damage);
        if (getHealth() <= 0){
            setDead();
        }
        System.out.println("Steel took " + damage + " damage and has " + getHealth() + " health left");
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


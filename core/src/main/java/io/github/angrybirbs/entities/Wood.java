package io.github.angrybirbs.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.World;

public class Wood extends Material {
    private float health;

    public Wood(TiledMapTile tile, float x, float y, World world) {
        super(tile, x, y,world, 4f, 0.3f, 0.05f); // density, friction, restitution
        this.health = 7f;
    }

    @Override
    public void takeDamage(float damage){
        setHealth(getHealth() - damage);
        if (getHealth() <= 0){
            setDead();
        }
        System.out.println("Wood took " + damage + " damage and has " + getHealth() + " health left");
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


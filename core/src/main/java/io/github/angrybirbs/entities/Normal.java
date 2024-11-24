package io.github.angrybirbs.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.World;

public class Normal extends Pig {
    private int power;
    private float health = 2.5f;

    public Normal(World world, TiledMapTile tile, float x, float y) {
        super(world,tile,x,y);
        this.power = 10;
    }

    public int getPower() {
        return power;
    }
    @Override
    public void takeDamage(float damage){
        health-=damage;
        if (health <= 0){
            setDead();
        }
        System.out.println("Normal took " + damage + " damage and has " + health + " health left");
    }

    @Override
    public float getHealth(){
        return health;
    }
}

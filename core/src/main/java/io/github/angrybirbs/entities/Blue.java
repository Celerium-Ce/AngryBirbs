package io.github.angrybirbs.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Blue extends Bird {
    private int power;
    private float health;


    public Blue(World world, TiledMapTile tile, float x, float y) {
        super(world,tile,x,y);
        this.power = 10;
        this.health = 1000;
    }

    @Override
    public int getPower() {
        return power;
    }
    @Override
    public void takeDamage(float damage) {
        health -= damage;
        if (health <= 0) {
            setDead();
        }
        System.out.println("Blue took " + damage + " damage and has " + health + " health left");

    }

    @Override
    public void usePower() {
        this.power = 30;
        this.getBody().setLinearVelocity(this.getBody().getLinearVelocity().scl(0.5f));
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

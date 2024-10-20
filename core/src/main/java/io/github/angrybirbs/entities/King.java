package io.github.angrybirbs.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.World;

public class King extends Pig {
<<<<<<< HEAD
    private int power;

    public King(World world, TiledMapTile tile, int x, int y) {
        super(world,tile,x,y);
        this.power = 10;
    }

    public int getPower() {
        return power;
=======
    private float health;

    public King(World world, TiledMapTile tile, float x, float y) {
        super(world,tile,x,y);
        this.health = 15;
    }

    @Override
    public void takeDamage(float damage){
        setHealth(getHealth() - damage);
        if (getHealth() <= 0){
            setDead();
        }
        System.out.println("King took " + damage + " damage and has " + getHealth() + " health left");
    }

    @Override
    public float getHealth(){
        return health;
    }
    @Override
    public void setHealth(float health){
        this.health = health;
>>>>>>> parent of b32d36f (refactored code)
    }
}

package io.github.angrybirbs.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.World;

public class General extends Pig {
    private float health;

    public General(World world, TiledMapTile tile, float x, float y) {
        super(world,tile,x,y);
        this.health = 10;

    }

    @Override
    public void takeDamage(float damage){
        setHealth(getHealth() - damage);
        if (getHealth() <= 0){
            setDead();
        }
        System.out.println("General took " + damage + " damage and has " + getHealth() + " health left");
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

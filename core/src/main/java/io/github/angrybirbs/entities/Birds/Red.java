package io.github.angrybirbs.entities.Birds;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.World;

public class Red extends Bird implements power {
    private float health;

    public Red(World world, TiledMapTile tile, float x, float y) {
        super(world,tile,x,y);
        this.health = 1000;
    }


    @Override
    public void takeDamage(float damage) {
        setHealth(getHealth() - damage);
        if (getHealth() <= 0){
            setDead();
        }
        System.out.println("Red took " + damage + " damage and has " + getHealth() + " health left");
    }

    @Override
    public void usePower() {
        return;
    } // Red has no special power

    @Override
    public float getHealth(){
        return health;
    }
    @Override
    public void setHealth(float health){
        this.health = health;
    }
}

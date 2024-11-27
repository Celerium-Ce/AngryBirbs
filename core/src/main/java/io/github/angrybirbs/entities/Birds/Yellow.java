package io.github.angrybirbs.entities.Birds;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.physics.box2d.World;

public class Yellow extends Bird implements power {
    private float health;
    private boolean powerUsed;


    public Yellow(World world, TiledMapTile tile, float x, float y) {
        super(world,tile,x,y);
        this.health = 1000;
        powerUsed = false;
    }

    @Override
    public void takeDamage(float damage) {
        setHealth(getHealth() - damage);
        if (getHealth() <= 0){
            setDead();
        }
        System.out.println("Yellow took " + damage + " damage and has " + getHealth() + " health left");

    }

    @Override
    public void usePower() {
        if (powerUsed || (this.getBody().getLinearVelocity().isZero())) { // don't use power for a 2nd time or if siting on slingshot (only case when velocity is 0 (else bird would have died))
            return;
        }
        powerUsed = true;
        this.getBody().setLinearVelocity(this.getBody().getLinearVelocity().scl(2));
    } // Yellow's power doubles it's speed

    @Override
    public float getHealth(){
        return health;
    }
    @Override
    public void setHealth(float health){
        this.health = health;
    }
}

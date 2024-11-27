package io.github.angrybirbs.entities.Birds;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import io.github.angrybirbs.levels.Level;

public class Blue extends Bird {
    private float health;
    private boolean powerUsed;

    public Blue(World world, TiledMapTile tile, float x, float y) {
        super(world,tile,x,y);
        this.health = 1000;
        this.powerUsed = false;

    }

    @Override
    public void takeDamage(float damage) {
        setHealth(getHealth() - damage);
        if (getHealth() <= 0){
            setDead();
        }
        System.out.println("Blue took " + damage + " damage and has " + getHealth() + " health left");

    }

    @Override
    public void usePower() {  // Blue's power is to split in three but the speed gets reduced to half
        if (powerUsed || (this.getBody().getLinearVelocity().isZero())) { // don't use power for a 2nd time or if siting on slingshot (only case when velocity is 0 (else bird would have died))
            return;
        }
        powerUsed = true;

        Vector2 position = this.getPosition();

        // get the position of the two new birds
        Vector2 abovePosition = new Vector2(position.x -32f, position.y + 32.0f);
        Vector2 belowPosition = new Vector2(position.x -32f, position.y - 96.0f);

        // create the birds
        Blue aboveBlue = new Blue(this.getWorld(), this.getTile(), abovePosition.x, abovePosition.y);
        Blue belowBlue = new Blue(this.getWorld(), this.getTile(), belowPosition.x, belowPosition.y);

        // toggle phys to let the move
        aboveBlue.togglephysics();
        belowBlue.togglephysics();

        // try to make it rotate a bit
        //aboveBlue.getBody().setTransform(aboveBlue.getBody().getPosition(), 0.1f);
        //belowBlue.getBody().setTransform(belowBlue.getBody().getPosition(), -0.1f);


        Vector2 velocity = this.getBody().getLinearVelocity().scl(1f); // get and reduce velocity

        //aboveBlue.getBody().setLinearVelocity(velocity.x, velocity.y + 20f);
        //belowBlue.getBody().setLinearVelocity(velocity.x, velocity.y - 20f);


        // give them velocity
        this.getBody().setLinearVelocity(velocity);
        aboveBlue.getBody().setLinearVelocity(velocity);
        belowBlue.getBody().setLinearVelocity(velocity);

        // add them to the level
        Level.addBird(aboveBlue);
        Level.addBird(belowBlue);

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

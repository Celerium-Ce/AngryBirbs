package io.github.angrybirbs.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static io.github.angrybirbs.levels.Level.PPM; // 100/3

public abstract class Bird implements power{
    private Body body;
    private World world;

    private Vector2 position;
    private float width, height;
    private final TiledMapTile tile;
    private boolean isDead;


    public Bird(World world, TiledMapTile tile, float x, float y) {
        this.world=world;
        this.tile = tile;
        this.position = new Vector2(x, y);

        this.width = tile.getTextureRegion().getRegionWidth();
        this.height = tile.getTextureRegion().getRegionHeight();

        // create a body for the bird
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(getPosition().x / PPM + getWidth()/(2*PPM), getPosition().y / PPM + getHeight()/(2*PPM));

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(getWidth() / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;

        Fixture fixture=getBody().createFixture(fixtureDef);
        fixture.setUserData(this);
        getBody().setUserData(this);
        shape.dispose();

        this.isDead = false;
    }


    public void render(SpriteBatch batch) {
        setPosition(getBody().getPosition().x * PPM, getBody().getPosition().y * PPM);

        if (!isDead()) {
            TextureRegion region = getTile().getTextureRegion();
            if (region != null) {
                batch.draw(region, getPosition().x-region.getRegionWidth()/2f, getPosition().y-region.getRegionHeight()/2f);
            }
        }
    }

    public void dispose() {
        if (getWorld() != null && getBody() != null) {
            for (Fixture fixture : getBody().getFixtureList()) {
                getBody().destroyFixture(fixture);
            }
            getWorld().destroyBody(getBody());
            setBody(null);
            Gdx.app.log("Bird", "Body disposed successfully");
        } else {
            Gdx.app.log("Bird", "Dispose called on a null body or world");
        }
    }
    public void togglephysics(){
        if (getBody().getType() == BodyDef.BodyType.StaticBody){
            getBody().setType(BodyDef.BodyType.DynamicBody);
        }
        else{
            getBody().setType(BodyDef.BodyType.StaticBody);
        }
    }

    public boolean isDead() {
        return isDead;
    }
    public void setDead(){
        isDead = true;
    }

    public Vector2 getPosition() {
        return position;
    }
    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public Body getBody() {
        return body;
    }
    public void setBody(Body body) {
        this.body = body;
    }

    public Vector2 getVelocity() {
        return getBody().getLinearVelocity();
    }
    public void setVelocity(Vector2 velocity) {
        getBody().setLinearVelocity(velocity);
    }

    public void reduceSpeed(float factor) {
        Vector2 velocity = getVelocity();
        getBody().setLinearVelocity(velocity.scl(factor));
    }


    public World getWorld() {
        return world;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public TiledMapTile getTile() {
        return tile;
    }

    public void takeDamage(float damage) {  }
    public abstract float getHealth();
    public abstract void setHealth(float health);

}

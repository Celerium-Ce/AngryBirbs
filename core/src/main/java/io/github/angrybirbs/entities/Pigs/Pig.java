package io.github.angrybirbs.entities.Pigs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static io.github.angrybirbs.levels.Level.PPM;
// necessary imports

public abstract class Pig {
    // Pig class
    // This class is responsible for rendering the pig and updating its position
    private Body body;
    private World world;

    private Vector2 position;
    private final float width, height;
    private final TiledMapTile tile;
    private boolean isDead;


    public Pig(World world,TiledMapTile tile, float x, float y) {
        // Constructor for the pig just sets the world, tile, position on the screen and creates the body and fixture
        this.world=world;
        this.tile = tile;
        this.position = new Vector2(x, y);

        this.width = tile.getTextureRegion().getRegionWidth();
        this.height = tile.getTextureRegion().getRegionHeight();

        // creat body for pig
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getPosition().x / PPM + getWidth()/(2*PPM), getPosition().y / PPM + getHeight()/(2*PPM));

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 10f;
        fixtureDef.restitution = 0.1f;

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
            Gdx.app.log("Pig", "Body disposed successfully");
        } else {
            Gdx.app.log("Pig", "Dispose called on a null body or world");
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

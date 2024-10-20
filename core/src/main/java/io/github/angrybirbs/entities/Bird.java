package io.github.angrybirbs.entities;

import com.badlogic.gdx.Gdx;
<<<<<<< HEAD
import com.badlogic.gdx.graphics.Texture;
=======
>>>>>>> parent of b32d36f (refactored code)
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

<<<<<<< HEAD
import static io.github.angrybirbs.levels.Level.PPM;

public class Bird {
    protected Texture texture;
    private String texturePath;
    private Body body;
    private World world;

    protected Vector2 position;
    protected float width, height;
    public TiledMapTile tile;
=======
import static io.github.angrybirbs.levels.Level.PPM; // 100/3
// necessary imports

public abstract class Bird implements power{
    // Bird class
    //  This class is responsible for rendering the bird and updating its position

    private Body body;
    private World world;

    private Vector2 position;
    private float width, height;
    private final TiledMapTile tile;
>>>>>>> parent of b32d36f (refactored code)
    private boolean isDead;


    public Bird(World world, TiledMapTile tile, float x, float y) {
<<<<<<< HEAD
        this.world=world;

        this.tile = tile;

=======
        // Constructor for the bird just sets the world, tile, position on the screen and creates the body and fixture

        this.world=world;
        this.tile = tile;
>>>>>>> parent of b32d36f (refactored code)
        this.position = new Vector2(x, y);

        this.width = tile.getTextureRegion().getRegionWidth();
        this.height = tile.getTextureRegion().getRegionHeight();

<<<<<<< HEAD
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x / PPM, position.y / PPM);
=======
        // create a body for the bird
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(getPosition().x / PPM + getWidth()/(2*PPM), getPosition().y / PPM + getHeight()/(2*PPM));
>>>>>>> parent of b32d36f (refactored code)

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
<<<<<<< HEAD
        shape.setRadius(width / 2 / PPM);
=======
        shape.setRadius(getWidth() / 2 / PPM);
>>>>>>> parent of b32d36f (refactored code)

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;

<<<<<<< HEAD

        Fixture fixture=body.createFixture(fixtureDef);
        fixture.setUserData(this);
        body.setUserData(this);
        //body.setAwake(false);
        //body.setLinearVelocity(0, 0);  // Prevent movement
        //body.setAngularVelocity(0);    // Prevent rotation
        //body.setGravityScale(0);
        togglephysics();
=======
        Fixture fixture=getBody().createFixture(fixtureDef);
        fixture.setUserData(this);
        getBody().setUserData(this);
>>>>>>> parent of b32d36f (refactored code)
        shape.dispose();

        this.isDead = false;
    }

<<<<<<< HEAD
    public void render(SpriteBatch batch) {
        position.set(body.getPosition().x * PPM, body.getPosition().y * PPM);

        if (!isDead) {
            TextureRegion region = tile.getTextureRegion(); // Get the TextureRegion from TiledMapTile
            if (region != null) {
                batch.draw(region, position.x-region.getRegionWidth()/2f, position.y-region.getRegionHeight()/2f); // Draw using TextureRegion
=======

    public void render(SpriteBatch batch) {
        setPosition(getBody().getPosition().x * PPM, getBody().getPosition().y * PPM);

        if (!isDead()) {
            TextureRegion region = getTile().getTextureRegion();
            if (region != null) {
                batch.draw(region, getPosition().x-region.getRegionWidth()/2f, getPosition().y-region.getRegionHeight()/2f);
>>>>>>> parent of b32d36f (refactored code)
            }
        }
    }

<<<<<<< HEAD
    public void togglephysics(){
        if (body.getType() == BodyDef.BodyType.StaticBody){
            body.setType(BodyDef.BodyType.DynamicBody);
        }
        else{
            body.setType(BodyDef.BodyType.StaticBody);
        }
    }

=======
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
>>>>>>> parent of b32d36f (refactored code)
    public void setDead(){
        isDead = true;
    }

<<<<<<< HEAD
    public void dispose() {
        if (body != null && world != null) {
            world.destroyBody(body);
            body = null;
        }
    }

    public Vector2 getPosition() {
        return position;
    }

=======
    public Vector2 getPosition() {
        return position;
    }
>>>>>>> parent of b32d36f (refactored code)
    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

<<<<<<< HEAD
    public boolean isToBeRemoved() {
        return isDead;
    }

    private void checkForClick() {
        if (Gdx.input.justTouched()) {
            float clickX = Gdx.input.getX();
            float clickY = Gdx.graphics.getHeight() - Gdx.input.getY(); // as libgdx has 0,0 at bottom left while graphic systems usually have top left

            if (clickX >= position.x && clickX <= position.x + width &&
                clickY >= position.y && clickY <= position.y + height) {
                isDead = true;
            }
        }
    }

    public Body getBody() {
        return body;
    }

    public String getTexturePath() {
        return this.texturePath;
    }
=======
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

>>>>>>> parent of b32d36f (refactored code)
}

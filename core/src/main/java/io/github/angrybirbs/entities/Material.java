package io.github.angrybirbs.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static io.github.angrybirbs.levels.Level.PPM;
<<<<<<< HEAD

public class Material {
    protected final TiledMapTile tile;
    protected Vector2 position;
    protected float width, height;
=======
// necessary imports

public abstract class Material {
    // material class
    //  This class is responsible for rendering the material and updating its position
    private final TiledMapTile tile;
    private Vector2 position;
    private float width, height;
>>>>>>> parent of b32d36f (refactored code)

    private Body body;
    private World world;

    private boolean isDead;

<<<<<<< HEAD

    public Material(TiledMapTile tile, float x, float y,World world) {
=======
    public Material(TiledMapTile tile, float x, float y,World world, float density, float friction, float restitution) {
        // Constructor for the material just sets the world, tile, position on the screen and creates the body and fixture
>>>>>>> parent of b32d36f (refactored code)
        this.tile = tile;
        this.world=world;

        this.position = new Vector2(x, y);
        this.width = tile.getTextureRegion().getRegionWidth();
        this.height = tile.getTextureRegion().getRegionHeight();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
<<<<<<< HEAD
        bodyDef.position.set(position.x / PPM, position.y / PPM);
=======
        bodyDef.position.set(getPosition().x / PPM + width / (2 * PPM), getPosition().y / PPM + height / (2 * PPM));
>>>>>>> parent of b32d36f (refactored code)

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
<<<<<<< HEAD
        shape.setAsBox(width/70, height/70); //70

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 10.0f;
        fixtureDef.friction = 10f;
        fixtureDef.restitution = 0.01f;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        body.setUserData(this);
=======
        shape.setAsBox(getWidth() / (2 * PPM), getHeight() / (2 * PPM));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        Fixture fixture = getBody().createFixture(fixtureDef);

        fixture.setUserData(this);
        getBody().setUserData(this);
>>>>>>> parent of b32d36f (refactored code)

        shape.dispose();

        this.isDead = false;
    }

    public void render(SpriteBatch batch) {
<<<<<<< HEAD
        position.set(body.getPosition().x * PPM, body.getPosition().y * PPM);


        if (!isDead) {
            TextureRegion region = tile.getTextureRegion(); // Get the TextureRegion from TiledMapTile
            if (region != null) {
                float rotation = (float) Math.toDegrees(body.getAngle()); // Get the rotation angle in degrees
                batch.draw(region,
                    position.x - width / 2, position.y - height / 2, // Position
                    width / 2, height / 2, // Origin for rotation
                    width, height, // Width and height
                    1, 1, // Scale
                    rotation); // Rotation angle
            }
=======
        setPosition(getBody().getPosition().x * PPM, getBody().getPosition().y * PPM);

        if (!isDead()) {
            TextureRegion region = getTile().getTextureRegion();
            if (region != null) {
                float rotation = (float) Math.toDegrees(getBody().getAngle()); // Get the rotation angle in degrees

                batch.draw(region,
                    getPosition().x - getWidth() / 2, getPosition().y - getHeight() / 2, // Position
                    getWidth() / 2, getHeight() / 2, // Origin for rotation
                    getWidth(), getHeight(), // Width and height
                    1, 1, // Scale
                    rotation); // Rotation angle
            }
            }
        }

    private TiledMapTile getTile() {
        return tile;
    }

    public void dispose () {
        if (getWorld() != null && getBody() != null) {
            for (Fixture fixture : getBody().getFixtureList()) {
                getBody().destroyFixture(fixture);
            }
            getWorld().destroyBody(getBody());
            setBody(null);
            Gdx.app.log("Bird", "Body disposed successfully");
        } else {
            Gdx.app.log("Bird", "Dispose called on a null body or world");
>>>>>>> parent of b32d36f (refactored code)
        }
    }


<<<<<<< HEAD
    public void dispose(World world) {
        if (world != null && body != null) {
            // Safely destroy all fixtures associated with the body
            for (Fixture fixture : body.getFixtureList()) {
                body.destroyFixture(fixture);
            }
            // Destroy the body itself
            world.destroyBody(body);
            body = null; // Nullify the reference to avoid future use
            Gdx.app.log("Material", "Body disposed successfully");
        } else {
            Gdx.app.log("Material", "Dispose called on a null body or world");
        }
=======
    public boolean isDead() {
        return isDead;
    }
    public void setDead(){
        isDead = true;
>>>>>>> parent of b32d36f (refactored code)
    }

    public Vector2 getPosition() {
        return position;
    }
<<<<<<< HEAD

=======
>>>>>>> parent of b32d36f (refactored code)
    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

<<<<<<< HEAD
    public boolean isToBeRemoved() {
        return isDead;
    }

    /*private void checkForClick() {
        if (Gdx.input.justTouched()) {
            float clickX = Gdx.input.getX();
            float clickY = Gdx.graphics.getHeight() - Gdx.input.getY(); // as libgdx has 0,0 at bottom left while graphic systems usually have top left

            if (clickX >= position.x && clickX <= position.x + width &&
                clickY >= position.y && clickY <= position.y + height) {
                isDead = true;
            }
        }
    }*/

    public void setDead() {
        isDead = true;
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


    public void takeDamage(float damage) {  }
    public abstract float getHealth();
    public abstract void setHealth(float health);
>>>>>>> parent of b32d36f (refactored code)

}

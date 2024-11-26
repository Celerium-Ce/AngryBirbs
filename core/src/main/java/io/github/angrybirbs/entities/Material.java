package io.github.angrybirbs.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static io.github.angrybirbs.levels.Level.PPM;

public abstract class Material {
    private final TiledMapTile tile;
    private Vector2 position;
    private float width, height;

    private Body body;
    private World world;

    private boolean isDead;

    public Material(TiledMapTile tile, float x, float y,World world, float density, float friction, float restitution) {
        this.tile = tile;
        this.world=world;

        this.position = new Vector2(x, y);
        this.width = tile.getTextureRegion().getRegionWidth();
        this.height = tile.getTextureRegion().getRegionHeight();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getPosition().x / PPM + width / (2 * PPM), getPosition().y / PPM + height / (2 * PPM));

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / (2 * PPM), getHeight() / (2 * PPM));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        Fixture fixture = getBody().createFixture(fixtureDef);

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


    public void takeDamage(float damage) {  }
    public abstract float getHealth();
    public abstract void setHealth(float health);

}

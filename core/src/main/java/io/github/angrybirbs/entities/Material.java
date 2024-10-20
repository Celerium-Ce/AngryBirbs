package io.github.angrybirbs.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static io.github.angrybirbs.levels.Level.PPM;

public class Material {
    protected final TiledMapTile tile;
    protected Vector2 position;
    protected float width, height;

    private Body body;
    private World world;

    private boolean isDead;


    public Material(TiledMapTile tile, float x, float y,World world) {
        this.tile = tile;
        this.world=world;

        this.position = new Vector2(x, y);
        this.width = tile.getTextureRegion().getRegionWidth();
        this.height = tile.getTextureRegion().getRegionHeight();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x / PPM, position.y / PPM);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/70, height/70); //70

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 10.0f;
        fixtureDef.friction = 10f;
        fixtureDef.restitution = 0.01f;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        body.setUserData(this);

        shape.dispose();

        this.isDead = false;
    }

    public void render(SpriteBatch batch) {
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
        }
    }


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
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

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

}

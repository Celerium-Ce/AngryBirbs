package io.github.angrybirbs.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static io.github.angrybirbs.levels.Level.PPM;

public class Bird {
    protected Texture texture;
    private String texturePath;
    private Body body;
    private World world;

    protected Vector2 position;
    protected float width, height;
    public TiledMapTile tile;
    private boolean isDead;


    public Bird(World world, TiledMapTile tile, float x, float y) {
        this.world=world;

        this.tile = tile;

        this.position = new Vector2(x, y);

        this.width = tile.getTextureRegion().getRegionWidth();
        this.height = tile.getTextureRegion().getRegionHeight();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x / PPM + width/(2*PPM), position.y / PPM + height/(2*PPM));

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;


        Fixture fixture=body.createFixture(fixtureDef);
        fixture.setUserData(this);
        body.setUserData(this);
        //body.setAwake(false);
        //body.setLinearVelocity(0, 0);  // Prevent movement
        //body.setAngularVelocity(0);    // Prevent rotation
        //body.setGravityScale(0);
        togglephysics();
        shape.dispose();

        this.isDead = false;
    }

    public void render(SpriteBatch batch) {
        position.set(body.getPosition().x * PPM, body.getPosition().y * PPM);

        if (!isDead) {
            TextureRegion region = tile.getTextureRegion(); // Get the TextureRegion from TiledMapTile
            if (region != null) {
                batch.draw(region, position.x-region.getRegionWidth()/2f, position.y-region.getRegionHeight()/2f); // Draw using TextureRegion
            }
        }
    }

    public void togglephysics(){
        if (body.getType() == BodyDef.BodyType.StaticBody){
            body.setType(BodyDef.BodyType.DynamicBody);
        }
        else{
            body.setType(BodyDef.BodyType.StaticBody);
        }
    }

    public void setDead(){
        isDead = true;
    }

    public void dispose() {
        if (body != null && world != null) {
            world.destroyBody(body);
            body = null;
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

    public int getPower() {
        return 1;
    }

    public Vector2 getVelocity() {
        return body.getLinearVelocity();
    }

    public void setVelocity(Vector2 velocity) {
        body.setLinearVelocity(velocity);
    }

    public void reduceSpeed(float factor) {
        Vector2 velocity = getVelocity();
        body.setLinearVelocity(velocity.scl(factor));
    }

    public void takeDamage(float damage) {  }

}

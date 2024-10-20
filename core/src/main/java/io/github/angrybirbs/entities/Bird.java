package io.github.angrybirbs.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

    private boolean isAlive;


    public Bird(World world, String texturePath, float x, float y) {
        this.world=world;

        this.texturePath = texturePath;
        this.texture = new Texture(Gdx.files.internal(this.texturePath));

        this.position = new Vector2(x, y);
        this.width = texture.getWidth();
        this.height = texture.getHeight();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position.x / PPM, position.y / PPM);

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(width / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;

        body.createFixture(fixtureDef);
        //body.setAwake(false);
        //body.setLinearVelocity(0, 0);  // Prevent movement
        //body.setAngularVelocity(0);    // Prevent rotation
        //body.setGravityScale(0);
        togglephysics();
        shape.dispose();

        this.isAlive = false;
    }

    public void render(SpriteBatch batch) {
        position.set(body.getPosition().x * PPM, body.getPosition().y * PPM);
        checkForClick();

        if (!isAlive) {
            batch.draw(texture, position.x-texture.getWidth()/2f, position.y-texture.getHeight()/2f);
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

    public void dispose() {
        texture.dispose();
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public boolean isToBeRemoved() {
        return isAlive;
    }

    private void checkForClick() {
        if (Gdx.input.justTouched()) {
            float clickX = Gdx.input.getX();
            float clickY = Gdx.graphics.getHeight() - Gdx.input.getY(); // as libgdx has 0,0 at bottom left while graphic systems usually have top left

            if (clickX >= position.x && clickX <= position.x + width &&
                clickY >= position.y && clickY <= position.y + height) {
                isAlive = true;
            }
        }
    }

    public Body getBody() {
        return body;
    }

    public String getTexturePath() {
        return this.texturePath;
    }
}

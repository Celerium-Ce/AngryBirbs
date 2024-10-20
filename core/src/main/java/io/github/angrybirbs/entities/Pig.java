package io.github.angrybirbs.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static io.github.angrybirbs.levels.Level.PPM;

public class Pig {
    protected Texture texture;
    private String texturePath;
    private Body body;
    private World world;

    protected Vector2 position;
    protected float width, height;

    private boolean isDead;

    public Pig(World world,String texturePath, float x, float y) {
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
        fixtureDef.density = 10.0f;
        fixtureDef.friction = 1000f;
        fixtureDef.restitution = 0.1f;


        Fixture fixture=body.createFixture(fixtureDef);
        fixture.setUserData(this);
        body.setUserData(this);
        shape.dispose();
        //togglephysics();
        this.isDead = false;
    }

    public void render(SpriteBatch batch) {


        position.set(body.getPosition().x * PPM, body.getPosition().y * PPM);
        checkForClick();

        if (!isDead) {
            batch.draw(texture, position.x-texture.getWidth()/2f, position.y-texture.getHeight()/2f);
        }
    }

    public void setDead() {
        isDead = true;
    }

    public void dispose() {
        Gdx.app.log("Pig dispose", "Disposing pig");
        if (body != null && world != null) {
            Gdx.app.log("Pig", "Destroying body");
            while (body.getFixtureList().size > 0){
                body.destroyFixture(body.getFixtureList().first());
            }
            world.destroyBody(body);
            texture.dispose();
        }

    }

    public Vector2 getPosition() {
        return position;
    }

    public void togglephysics(){
        if (body.getType() == BodyDef.BodyType.StaticBody){
            body.setType(BodyDef.BodyType.DynamicBody);
        }
        else{
            body.setType(BodyDef.BodyType.StaticBody);
        }
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

    public String getTexturePath() {
        return this.texturePath;
    }
}

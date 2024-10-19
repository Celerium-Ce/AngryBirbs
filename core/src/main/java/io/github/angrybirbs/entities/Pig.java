package io.github.angrybirbs.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Pig {
    protected Texture texture;
    private String texturePath;

    protected Vector2 position;
    protected float width, height;

    private boolean isAlive;


    public Pig(String texturePath, float x, float y) {
        this.texturePath = texturePath;
        this.texture = new Texture(Gdx.files.internal(this.texturePath));

        this.position = new Vector2(x, y);
        this.width = texture.getWidth();
        this.height = texture.getHeight();

        this.isAlive = false;
    }

    public void render(SpriteBatch batch) {
        checkForClick();

        if (!isAlive) {
            batch.draw(texture, position.x, position.y);
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

    public String getTexturePath() {
        return this.texturePath;
    }
}

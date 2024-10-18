package io.github.angrybirbs.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Material {
    protected Texture texture;
    protected Vector2 position;
    protected String type;  // "ice", "steel", "wood"

    public Material(String type, String texturePath, float x, float y) {
        this.type = type;
        this.texture = new Texture(texturePath);
        this.position = new Vector2(x, y);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y);
    }

    public void dispose() {
        texture.dispose();
    }

    public Vector2 getPosition() {
        return position;
    }

    public String getType() {
        return type;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }
}

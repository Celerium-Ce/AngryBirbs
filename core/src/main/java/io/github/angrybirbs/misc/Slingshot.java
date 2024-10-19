package io.github.angrybirbs.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Slingshot {
    private Vector2 origin;
    private Texture texture;
    private Vector2 dragPosition;
    private boolean isDragging;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private SpriteBatch batch = new SpriteBatch();

    public Slingshot(Vector2 origin) {
        this.origin = origin;
        this.dragPosition = new Vector2(origin); // Default to the origin
        this.isDragging = false;
        texture = new Texture(Gdx.files.internal("entities/slingshot.png"));
    }

    public void startDragging(Vector2 initialPosition) {
        isDragging = true;
        dragPosition.set(initialPosition);
    }

    public void updateDragPosition(Vector2 newPosition) {
        if (isDragging) {
            dragPosition.set(newPosition);
        }
    }

    public void release() {
        isDragging = false;
    }

    public Vector2 getLaunchVector() {
        return new Vector2(origin).sub(dragPosition);
    }

    public boolean isDragging() {
        return isDragging;
    }

    public void render(float delta) {
        batch.begin();
        batch.draw(texture, origin.x, origin.y);
        batch.end();
        if (isDragging) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.line(origin, dragPosition); // Visualize the stretch
            shapeRenderer.end();
        }
    }
}

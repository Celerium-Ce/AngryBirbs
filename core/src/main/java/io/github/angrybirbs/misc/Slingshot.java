package io.github.angrybirbs.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Vector2;
// necessary imports

public class Slingshot {

    // Slingshot class
    // This class is responsible for rendering the slingshot and calculating the launch vector
    // It also visualizes the stretch and the trajectory of the bird
    private final Vector2 origin;
    private final TiledMapTile tile;
    private Vector2 dragPosition;
    private boolean isDragging;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private SpriteBatch batch = new SpriteBatch();
    protected Vector2 position;
    private final float maxStretchDistance = 200f; // Adjust as needed for max stretch distance

    public Slingshot(Vector2 origin, TiledMapTile tile, float x, float y) {

        // Constructor for the slingshot just sets the origin, tile, and position on the screen

        this.origin = origin;
        this.tile = tile;
        this.position = new Vector2(x, y);

        this.dragPosition = new Vector2(origin); // Default to the origin
        this.isDragging = false;
    }

    public void startDragging(Vector2 initialPosition) {

        // Starts dragging action from input processor and sets the initial position

        isDragging = true;
        dragPosition.set(initialPosition);
    }

    public void updateDragPosition(Vector2 newPosition) {

        // Updates the drag position based on the new position given,  and clamps it to the max stretch distance

        if (isDragging) { // Only update if dragging
            Vector2 stretchVector = new Vector2(newPosition).sub(origin);
            if (stretchVector.len() > maxStretchDistance) {
                stretchVector.setLength(maxStretchDistance);
            }
            dragPosition.set(origin).add(stretchVector);
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
        TextureRegion region = tile.getTextureRegion(); // Get the TextureRegion from TiledMapTile

        batch.draw(region, position.x-region.getRegionWidth()/2f, position.y); // Draw using TextureRegion
        batch.end();
        if (isDragging) {
            // Render the stretch of the slingshot
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.line(origin, dragPosition); // Visualize the stretch
            shapeRenderer.end();
        }
    }

    public void renderDraggableArea() {

        // not used, is useful for debugging purposes

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(origin.x, origin.y, 100); // Draw a circle with radius 50
        shapeRenderer.end();
    }

    public void renderTrajectory() {

        // Renders the trajectory of the bird based on the launch vector

        if (!isDragging) return; // Only render when dragging

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);

        // Calculate the initial velocities based on the launch vector and scale it.
        Vector2 launchVector = getLaunchVector();
        float velocityX = launchVector.x * 0.7f;
        float velocityY = launchVector.y * 0.7f;
        float gravity = -9.8f; // Adjust gravity as needed

        Vector2 currentPosition = new Vector2(origin);
        for (float t = 0; t < 4.5f; t += 0.3f) { // Adjust the range and step as needed

            // Calculate the position based on the velocity and gravity using projectile motion equations

            float x = origin.x + velocityX * t;
            float y = origin.y + velocityY * t + 0.5f * gravity * t * t;
            shapeRenderer.circle(x, y, 2); // Draw a small circle at each point
        }

        shapeRenderer.end();
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
    }
}

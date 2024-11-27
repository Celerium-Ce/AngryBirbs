package io.github.angrybirbs.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import io.github.angrybirbs.entities.Birds.Bird;
import io.github.angrybirbs.levels.Level;

import static io.github.angrybirbs.levels.Level.PPM;
// Necessary imports

// input processor for the slingshot and bird
public class Slingshotinputprocessor implements InputProcessor {
    private Slingshot slingshot;
    public static Bird activebird;
    private Level level;

    public Slingshotinputprocessor(Slingshot slingshot, Bird activebird,Level level) { // Constructor nothing special just sets the slingshot and bird
        this.level = level;
        this.slingshot = slingshot;
        this.activebird = activebird;
        activebird.setPosition(slingshot.getOrigin().x, slingshot.getOrigin().y);
    }

    public static Bird getActiveBird() {
        return activebird;
    }

    public void setbirdposition(Vector2 position) {
        activebird.setPosition(position.x, position.y);
        activebird.getBody().setTransform(position.x / PPM, position.y / PPM, activebird.getBody().getAngle()); // Making sure to convert between box2d and texture coordinate units
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        // Get the touch position,pass it to slingshot and log it when the screen is touched

        Vector2 touch = new Vector2(screenX, Gdx.graphics.getHeight()-screenY);
        Gdx.app.log("Slingshotinputprocessor", "touchDown at: " + touch + (Gdx.graphics.getHeight()-touch.y)); //logging it for debugging purposes
        if (activebird!=null && activebird.getPosition().dst(touch) < 100) { // 100 is the radius which works to initiate the drag event for the slingshot
            slingshot.startDragging(touch);  // Start dragging the slingshot
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        // Find current touch position and update the slingshot position, useful for calculating trajectory

        if (slingshot.isDragging()) {
            Gdx.app.log("Slingshotinputprocessor", "dragging at: " + new Vector2(screenX, screenY));
            slingshot.updateDragPosition(new Vector2(screenX, Gdx.graphics.getHeight() - screenY)); // Sending the touch position to the slingshot
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        // When the touch is released, release the slingshot and apply the impulse to the bird (basically to launch the active bird)

        if (slingshot.isDragging()) {
            Gdx.app.log("Slingshotinputprocessor", "touchUp at: " + new Vector2(screenX, screenY));
            if (activebird != null) {
                activebird.togglephysics(); // birds are initially static, so we need to make them dynamic to apply the impulse
                Vector2 launchVector = slingshot.getLaunchVector(); // Getting the launch vector from the slingshot to apply the impulse
                launchVector.scl(0.355f);
                activebird.getBody().applyLinearImpulse(launchVector, activebird.getBody().getWorldCenter(), true); // Applying the impulse to the bird

            }
            // Reset the slingshot
            activebird=null;
            slingshot.release();
            return true;
        }
        return false;
    }

    @Override public boolean keyDown(int keycode) {

        // Pause the game when the escape key is pressed

        if (keycode== Input.Keys.ESCAPE) {
            level.togglePause();
            return true;
        }

        // Use the bird's power when the space key is pressed

        if (keycode == Input.Keys.SPACE && activebird != null) {
            activebird.usePower();
            return true;
        }
        return false;
    }



    public void setActiveBird(Bird bird) {

        // Set the active bird to the bird passed in the argument and set it on the slingshot

        this.activebird = bird;
        if (bird != null) {
            //bird.setPosition(slingshot.getOrigin().x, slingshot.getOrigin().y);
            bird.getBody().setTransform(slingshot.getOrigin().x / PPM, slingshot.getOrigin().y / PPM, bird.getBody().getAngle());
        }
    }

    // Unused methods
    @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int lame) { return false; }
    @Override public boolean keyUp(int keycode) { return false; }
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }

}

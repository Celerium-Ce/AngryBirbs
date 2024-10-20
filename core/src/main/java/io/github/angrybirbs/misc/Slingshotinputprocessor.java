package io.github.angrybirbs.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import io.github.angrybirbs.entities.Bird;
import io.github.angrybirbs.levels.Level;
import io.github.angrybirbs.misc.Slingshot;

import static io.github.angrybirbs.levels.Level.PPM;

public class Slingshotinputprocessor implements InputProcessor {
    private Slingshot slingshot;
    public Bird activebird;
    private Level level;

    public Slingshotinputprocessor(Slingshot slingshot, Bird activebird,Level level) {
        this.level = level;
        this.slingshot = slingshot;
        this.activebird = activebird;
        activebird.setPosition(slingshot.getOrigin().x, slingshot.getOrigin().y);
    }

    public void setbirdposition(Vector2 position) {
        activebird.setPosition(position.x, position.y);
        activebird.getBody().setTransform(position.x / PPM, position.y / PPM, activebird.getBody().getAngle());
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 touch = new Vector2(screenX, Gdx.graphics.getHeight()-screenY);
        Gdx.app.log("Slingshotinputprocessor", "touchDown at: " + touch + (Gdx.graphics.getHeight()-touch.y));
        if (activebird!=null && activebird.getPosition().dst(touch) < 100) {
            slingshot.startDragging(touch);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (slingshot.isDragging()) {
            Gdx.app.log("Slingshotinputprocessor", "dragging at: " + new Vector2(screenX, screenY));
            slingshot.updateDragPosition(new Vector2(screenX, Gdx.graphics.getHeight() - screenY));
            return true;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (slingshot.isDragging()) {
            Gdx.app.log("Slingshotinputprocessor", "touchUp at: " + new Vector2(screenX, screenY));
            if (activebird != null) {
                activebird.togglephysics();
                Vector2 launchVector = slingshot.getLaunchVector();
                activebird.getBody().applyLinearImpulse(launchVector, activebird.getBody().getWorldCenter(), true);
            }
            slingshot.release();
            return true;
        }
        return false;
    }

    @Override public boolean keyDown(int keycode) {
        if (keycode== Input.Keys.ESCAPE) {
            level.togglePause();
            return true;
        }
        return false;
    }



    public void setActiveBird(Bird bird) {
        this.activebird = bird;
        if (bird != null) {
            //bird.setPosition(slingshot.getOrigin().x, slingshot.getOrigin().y);
            bird.getBody().setTransform(slingshot.getOrigin().x / PPM, slingshot.getOrigin().y / PPM, bird.getBody().getAngle());
        }
    }


    @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int lame) { return false; }
    @Override public boolean keyUp(int keycode) { return false; }
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }

}

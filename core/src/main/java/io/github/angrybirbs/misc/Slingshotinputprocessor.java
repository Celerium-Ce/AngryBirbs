package io.github.angrybirbs.misc;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import io.github.angrybirbs.entities.Bird;
import io.github.angrybirbs.misc.Slingshot;

public class Slingshotinputprocessor implements InputProcessor {
    private Slingshot slingshot;
    public Bird activebird;

    public Slingshotinputprocessor(Slingshot slingshot, Bird activebird) {
        this.slingshot = slingshot;
        this.activebird = activebird;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 touch = new Vector2(screenX, screenY);
        if (activebird!=null && activebird.getPosition().dst(touch) < 50) {
            slingshot.startDragging(touch);
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        slingshot.updateDragPosition(new Vector2(screenX, screenY));
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        slingshot.release();
        return true;
    }

    @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int lame) { return false; }
    @Override public boolean keyDown(int keycode) { return false; }
    @Override public boolean keyUp(int keycode) { return false; }
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }

}

package io.github.angrybirbs;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.angrybirbs.menu.MainMenu;
import java.io.Serializable;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game implements Serializable {

    public SpriteBatch batch;

    @Override
    public void create() {
        setScreen(new MainMenu(this));
    }

    @Override
    public void render() {
        super.render(); // important!
    }
}

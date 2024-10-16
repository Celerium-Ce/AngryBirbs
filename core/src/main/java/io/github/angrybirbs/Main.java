package io.github.angrybirbs;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.angrybirbs.menu.MainMenu;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    public SpriteBatch batch;

    @Override
    public void create() {
        setScreen(new MainMenu(this));
    }
}

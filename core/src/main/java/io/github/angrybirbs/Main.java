package io.github.angrybirbs;

import com.badlogic.gdx.Game;
import io.github.angrybirbs.menu.FirstScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
    @Override
    public void create() {
        setScreen(new FirstScreen());
    }
}

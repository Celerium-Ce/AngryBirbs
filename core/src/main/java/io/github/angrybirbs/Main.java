package io.github.angrybirbs;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.angrybirbs.menu.MainMenu;
import java.io.Serializable;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game implements Serializable {

    // Main class that extends Game

    // This class is used to create the game and set the first screen to the main menu
    public SpriteBatch batch;

    @Override
    public void create() {
        setScreen(new MainMenu(this));
    }
    // The create method is called when the game is created and it starts by calling main menu

    @Override
    public void render() {
        super.render(); // important!
    }
}

package io.github.angrybirbs.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.angrybirbs.Main;
// necessary imports

public abstract class Menu implements Screen {

    // Abstract class Menu that implements Screen
    // This class is used to create all other menus in the game
    // It has a reference to the game object and a stage object

    protected static Main game;
    protected final Stage stage;
    protected Texture backgroundTexture;
    public Menu(Main game) {

        // Constructor for the Menu class

        this.game = game;
        stage=new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    public void setUi(Table ui) {

        // Method to set the UI of the menu

        ui.setFillParent(true);
        stage.addActor(ui);
    }


    // The following methods are overridden from the Screen interface
    // Some unused methods are left empty
    @Override
    public void show() {
        // Prepare your screen here.
    }

    @Override
    public void render(float delta){
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

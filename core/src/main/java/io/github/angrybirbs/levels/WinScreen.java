package io.github.angrybirbs.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.angrybirbs.Main;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class WinScreen implements Screen {
    private Main game;
    private Stage stage;
    private Image bgImage;

    public WinScreen(Main game) {
        this.game = game;

        stage = new Stage(new ScreenViewport());

        Texture bgTexture = new Texture(Gdx.files.internal("Win.png"));
        bgImage = new Image(new TextureRegionDrawable(new TextureRegion(bgTexture)));

        bgImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage.addActor(bgImage);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        bgImage.remove();
    }
}

package io.github.angrybirbs.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.angrybirbs.Main;
import io.github.angrybirbs.entities.*;
import io.github.angrybirbs.menu.LevelsMenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Level implements Screen, Serializable {
    protected Main game;
    protected Texture backgroundTexture;
    protected SpriteBatch batch;

    protected List<Bird> birds;
    protected List<Pig> pigs;

    protected boolean isPaused;

    private Stage stage;
    private ImageButton pauseButton;
    private ImageButton backButton;
    private ImageButton menuButton;
    private ImageButton resumeButton;
    private ImageButton restartButton;
    private ImageButton saveButton;
    private Image menubg;

    public Level(Main game, List<Bird> birds, List<Pig> pigs) {
        this.game = game;
        backgroundTexture = new Texture(Gdx.files.internal("level.png"));
        batch = new SpriteBatch();

        this.birds = birds;
        this.pigs = pigs;

        isPaused = false;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        setupButtons();
    }

    private void setupButtons() {


        pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/pause.png")))));
        pauseButton.setPosition(10, Gdx.graphics.getHeight() - 60);
        pauseButton.setSize(50, 50);
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                togglePause();
            }
        });

        backButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/back.png")))));
        backButton.setSize(Gdx.graphics.getWidth() / 20f, Gdx.graphics.getHeight() / 20f);

        menuButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/menu.png")))));
        menuButton.setSize(Gdx.graphics.getWidth() / 20f, Gdx.graphics.getHeight() / 20f);

        resumeButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/resume.png")))));
        resumeButton.setSize(Gdx.graphics.getWidth() / 20f, Gdx.graphics.getHeight() / 20f);

        restartButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/restart.png")))));
        restartButton.setSize(Gdx.graphics.getWidth() / 20f, Gdx.graphics.getHeight() / 20f);

        saveButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/Save.png")))));
        saveButton.setSize(Gdx.graphics.getWidth() / 20f, Gdx.graphics.getHeight() / 20f);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LoseScreen(game));
                dispose();
            }
        });

        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LevelsMenu(game));
                dispose();
            }
        });

        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isPaused = false;
                hidePauseMenuButtons();
            }
        });

        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LoseScreen(game));
                dispose();
            }
        });

        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // save game implementation here
            }
        });

        menubg = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/menubg.png")))));

        stage.addActor(pauseButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        for (Bird bird : birds) {
            bird.render(batch);
        }
        for (Pig pig : pigs) {
            pig.render(batch);
        }

        batch.end();

        stage.act(delta);
        stage.draw();

        if (isPaused) {
            showPauseMenuButtons();
        }
    }

    private void showPauseMenuButtons() {
        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;

        backButton.setPosition(centerX - backButton.getWidth() / 2f, centerY + backButton.getHeight() + 20);
        menuButton.setPosition(centerX - menuButton.getWidth() - 10, centerY);
        resumeButton.setPosition(centerX - resumeButton.getWidth() / 2f, centerY);
        restartButton.setPosition(centerX + 10, centerY);
        saveButton.setPosition(centerX - saveButton.getWidth()/2f, centerY - saveButton.getHeight() - 20);
        menubg.setPosition(centerX - menubg.getWidth()/2f, centerY - menubg.getHeight()/2f + 30);
        menubg.setSize(Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() / 3f);

        stage.addActor(menubg);
        menubg.setZIndex(0);
        stage.addActor(backButton);
        stage.addActor(menuButton);
        stage.addActor(resumeButton);
        stage.addActor(restartButton);
        stage.addActor(saveButton);
    }

    private void hidePauseMenuButtons() {
        backButton.remove();
        menuButton.remove();
        resumeButton.remove();
        restartButton.remove();
        saveButton.remove();
        menubg.remove();
    }

    private void togglePause() {
        isPaused = !isPaused;
        if (!isPaused) {
            hidePauseMenuButtons();
        }
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
        batch.dispose();
        backgroundTexture.dispose();
        stage.dispose();

        for (Bird bird : birds) {
            bird.dispose();
        }
    }
}

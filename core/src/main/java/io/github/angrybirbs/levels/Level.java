package io.github.angrybirbs.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.angrybirbs.Main;
import io.github.angrybirbs.entities.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Level implements Screen {
    protected Main game;
    protected Texture backgroundTexture;
    protected SpriteBatch batch;

    protected List<Bird> birds;
    protected List<Pig> pigs;

    protected boolean isPaused;
    protected Texture pauseButtonTexture;
    protected Texture pauseButtonPressedTexture;

    protected Texture mainButtonTexture;
    protected Texture mainButtonPressedTexture;
    protected boolean showPauseMenuButtons;

    protected Texture button1Texture;
    protected Texture menuTexture;
    protected Texture resumeTexture;
    protected Texture restartTexture;

    public Level(Main game) {
        this.game = game;
        backgroundTexture = new Texture(Gdx.files.internal("level.png"));
        batch = new SpriteBatch();

        birds = new ArrayList<>();
        pigs = new ArrayList<>();

        loadLevelData(); // Load specific level data

        isPaused = false;

        pauseButtonTexture = new Texture(Gdx.files.internal("Buttons/pause.png"));
        button1Texture = new Texture(Gdx.files.internal("Buttons/back.png"));
        menuTexture = new Texture(Gdx.files.internal("Buttons/menu.png"));
        resumeTexture = new Texture(Gdx.files.internal("Buttons/resume.png"));
        restartTexture = new Texture(Gdx.files.internal("Buttons/restart.png"));

        showPauseMenuButtons = false;
    }

    protected abstract void loadLevelData();

    @Override
    public void show() {
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

        if (isPaused) {
            batch.draw(pauseButtonPressedTexture, 10, Gdx.graphics.getHeight() - 60, 50, 50);
        } else {
            batch.draw(pauseButtonTexture, 10, Gdx.graphics.getHeight() - 60, 50, 50);
        }

        if (showPauseMenuButtons) {
            drawPauseMenuButtons();
        }

        batch.end();

        if (Gdx.input.isTouched()) {
            float x = Gdx.input.getX();
            float y = Gdx.input.getY();

            // Check if pause button is clicked
            if (x < 60 && y > Gdx.graphics.getHeight() - 60) {
                togglePause();
            }

            if (x < 60 && y < Gdx.graphics.getHeight() - 120 && !showPauseMenuButtons) {
                showPauseMenuButtons = true;
            }

            if (showPauseMenuButtons) {
                handlePauseMenuButtonClicks(x, y);
            }
        }
    }

    private void drawPauseMenuButtons() {
        float centerX = Gdx.graphics.getWidth() / 2f - (Gdx.graphics.getWidth() / 20f) / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f - (Gdx.graphics.getHeight() / 20f) / 2f;

        batch.draw(button1Texture, centerX, centerY + (Gdx.graphics.getWidth() / 20f), Gdx.graphics.getWidth() / 20f, Gdx.graphics.getHeight() / 20f);
        batch.draw(menuTexture, centerX - (Gdx.graphics.getWidth() / 20f) - 5, centerY, Gdx.graphics.getWidth() / 20f, Gdx.graphics.getHeight() / 20f);
        batch.draw(resumeTexture, centerX, centerY, Gdx.graphics.getWidth() / 20f, Gdx.graphics.getHeight() / 20f);
        batch.draw(restartTexture, centerX + (Gdx.graphics.getWidth() / 20f) + 5, centerY, Gdx.graphics.getWidth() / 20f, Gdx.graphics.getHeight() / 20f);
    }

    private void handlePauseMenuButtonClicks(float x, float y) {
        float centerX = Gdx.graphics.getWidth() / 2f - (Gdx.graphics.getWidth() / 20f) / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f - (Gdx.graphics.getHeight() / 20f) / 2f;

        if (x > centerX - 30 && x < centerX + 30 && y > centerY + 60 && y < centerY + 110) {
            // Button 1 clicked (do nothing)
        } else if (x > centerX - 30 && x < centerX + 30 && y > centerY && y < centerY + 50) {
            // Button 2 clicked (do nothing)
        } else if (x > centerX + 30 && x < centerX + 90 && y > centerY && y < centerY + 50) {
            // Button 3 clicked (remove PauseMenu buttons)
            showPauseMenuButtons = false;
        } else if (x > centerX + 90 && x < centerX + 140 && y > centerY && y < centerY + 50) {
            // Button 4 clicked (do nothing)
        }
    }

    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
            pause();
        } else {
            resume();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        // Implement any PauseMenu pause logic here if needed
    }

    @Override
    public void resume() {
        // Implement any PauseMenu resume logic here if needed
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
        batch.dispose();
        pauseButtonTexture.dispose();
        pauseButtonPressedTexture.dispose();
        button1Texture.dispose();
        menuTexture.dispose();
        resumeTexture.dispose();
        restartTexture.dispose();

        for (Bird bird : birds) {
            bird.dispose();
        }
    }
}

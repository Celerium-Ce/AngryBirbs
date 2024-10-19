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

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Level implements Screen {
    protected Main game;
    protected Texture backgroundTexture;
    protected SpriteBatch batch;

    private int levelNum;

    protected List<Bird> birds;
    protected List<Pig> pigs;
    protected List<Material> materials;

    protected List<Bird> initialBirds;
    protected List<Pig> initialPigs;
    protected List<Material> initialMaterials;

    protected boolean isPaused;

    private Stage stage;

    private ImageButton pauseButton;
    private ImageButton menuButton;
    private ImageButton nextButton;
    private ImageButton resumeButton;
    private ImageButton restartButton;
    private ImageButton saveButton;

    private Image menubg;
    private Image winImage;
    private Image looseImage;

    public Level(Main game, List<Bird> birds, List<Pig> pigs,  List<Material> materials, int levelNum) {
        this.game = game;
        this.levelNum = levelNum;
        backgroundTexture = new Texture(Gdx.files.internal("level.png"));
        batch = new SpriteBatch();

        this.birds = birds;
        this.pigs = pigs;
        this.materials = materials;

        this.initialBirds = cloneBirds(birds);
        this.initialPigs = clonePigs(pigs);
        this.initialMaterials = cloneMaterials(materials);


        isPaused = false;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        setupButtons();
        setupGameEnd();
    }


    private List<Bird> cloneBirds(List<Bird> birds) {
        List<Bird> clonedBirds = new ArrayList<>();
        for (Bird bird : birds) {
            clonedBirds.add(new Bird(bird.getTexturePath(), bird.getPosition().x, bird.getPosition().y));
        }
        return clonedBirds;
    }

    private List<Pig> clonePigs(List<Pig> pigs) {
        List<Pig> clonedPigs = new ArrayList<>();
        for (Pig pig : pigs) {
            clonedPigs.add(new Pig(pig.getTexturePath(), pig.getPosition().x, pig.getPosition().y));
        }
        return clonedPigs;
    }

    private List<Material> cloneMaterials(List<Material> materials) {
        List<Material> cloneMaterials = new ArrayList<>();
        for (Material material : materials) {
            cloneMaterials.add(new Material(material.getTexturePath(), material.getPosition().x, material.getPosition().y));
        }
        return cloneMaterials;
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

        menuButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/menu.png")))));
        menuButton.setSize(Gdx.graphics.getWidth() / 20f, Gdx.graphics.getHeight() / 20f);

        nextButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/next.png")))));
        nextButton.setSize(Gdx.graphics.getWidth() / 20f, Gdx.graphics.getHeight() / 20f);

        resumeButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/resume.png")))));
        resumeButton.setSize(Gdx.graphics.getWidth() / 20f, Gdx.graphics.getHeight() / 20f);

        restartButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/restart.png")))));
        restartButton.setSize(Gdx.graphics.getWidth() / 20f, Gdx.graphics.getHeight() / 20f);

        saveButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/Save.png")))));
        saveButton.setSize(Gdx.graphics.getWidth() / 20f, Gdx.graphics.getHeight() / 20f);

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
        nextButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                loadNextLevel();
            }
        });
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isPaused = false;
                hidePauseMenuButtons();
                restartLevel();
            }
        });

        saveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            }
        });

        menubg = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/menubg.png")))));

        stage.addActor(pauseButton);
    }

    private void setupGameEnd() {
        winImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/winscreen.png")))));
        winImage.setSize(Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() / 3f);
        winImage.setPosition((Gdx.graphics.getWidth() - winImage.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - winImage.getHeight()) / 2f + 10);
        winImage.setVisible(false);
        stage.addActor(winImage);

        looseImage = new Image(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/losescreen.png")))));
        looseImage.setSize(Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() / 3f);
        looseImage.setPosition((Gdx.graphics.getWidth() - looseImage.getWidth()) / 2f,
            (Gdx.graphics.getHeight() - looseImage.getHeight()) / 2f + 10);
        looseImage.setVisible(false);
        stage.addActor(looseImage);
    }

    private void loadNextLevel() {
        int nextLevelNum = this.levelNum + 1;

        File nextLevelFile = new File(Gdx.files.local("Levels/" + nextLevelNum + ".json").file().getAbsolutePath());

        if (nextLevelFile.exists()) {
            Level nextLevel = LevelsMenu.createLevelFromJson(nextLevelFile, nextLevelNum);
            game.setScreen(nextLevel);
        } else {
            game.setScreen(new LevelsMenu(game));
        }
    }

    private void showGameEndMenuButtons() {
        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;

        menuButton.setPosition(centerX - menuButton.getWidth()/2f - 50, centerY);
        stage.addActor(menuButton);

        //nextButton.setPosition(centerX - nextButton.getWidth()/2f, centerY);
        //stage.addActor(nextButton);

        restartButton.setPosition(centerX - restartButton.getWidth()/2f + 50, centerY);
        stage.addActor(restartButton);

        //saveButton.setPosition(centerX - saveButton.getWidth()/2f, centerY -  saveButton.getWidth()/2f - 10);
        //stage.addActor(saveButton);
    }

    private void restartLevel() {
        Level level = new Level(game, initialBirds, initialPigs, initialMaterials, levelNum);
        game.setScreen(level);
    }

    private boolean checkWinCondition() {
        return pigs.isEmpty();
    }

    private void showWinScreen() {
        showGameEndMenuButtons();
        winImage.setVisible(true);
        Gdx.input.setInputProcessor(stage);
    }

    private boolean checkLooseCondition() {
        return birds.isEmpty();
    }

    private void showLooseScreen() {
        showGameEndMenuButtons();
        looseImage.setVisible(true);
        Gdx.input.setInputProcessor(stage);
    }

    private void showPauseMenuButtons() {
        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;

        menuButton.setPosition(centerX - menuButton.getWidth() - 10, centerY);
        resumeButton.setPosition(centerX - resumeButton.getWidth() / 2f, centerY);
        restartButton.setPosition(centerX + 10, centerY);
        saveButton.setPosition(centerX - saveButton.getWidth()/2f, centerY - saveButton.getHeight() - 20);
        menubg.setPosition(centerX - menubg.getWidth()/2f, centerY - menubg.getHeight()/2f + 30);
        menubg.setSize(Gdx.graphics.getWidth() / 3f, Gdx.graphics.getHeight() / 3f);

        stage.addActor(menubg);
        menubg.setZIndex(0);
        stage.addActor(menuButton);
        stage.addActor(resumeButton);
        stage.addActor(restartButton);
        stage.addActor(saveButton);
    }

    private void hidePauseMenuButtons() {
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
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Iterator<Bird> birdIterator = birds.iterator();
        while (birdIterator.hasNext()) {
            Bird bird = birdIterator.next();
            bird.render(batch);
            if (bird.isToBeRemoved()) {
                bird.dispose();
                birdIterator.remove();
            }
        }

        Iterator<Pig> pigIterator = pigs.iterator();
        while (pigIterator.hasNext()) {
            Pig pig = pigIterator.next();
            pig.render(batch);
            if (pig.isToBeRemoved()) {
                pig.dispose();
                pigIterator.remove();
            }
        }

        for (Material material : materials) {
            material.render(batch);
            if (material.isToBeRemoved()) {
                material.dispose();
            }
        }


        batch.end();

        stage.act(delta);
        stage.draw();

        if (isPaused) {
            showPauseMenuButtons();
        }
        if (checkWinCondition()) {
            showWinScreen();
        }
        if (checkLooseCondition()) {
            showLooseScreen();
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
        for (Pig pig : pigs) {
            pig.dispose();
        }
        for (Material material : materials) {
            material.dispose();
        }
    }
}

package io.github.angrybirbs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.angrybirbs.entities.*;

import java.util.ArrayList;
import java.util.List;

public class Level implements Screen {
    private Main game;
    private Texture backgroundTexture;
    private SpriteBatch batch;

    private List<Bird> birds;
    private List<Pig> pigs;

    public Level(Main game) {
        this.game = game;
        backgroundTexture = new Texture(Gdx.files.internal("level.png"));
        batch = new SpriteBatch();

        birds = new ArrayList<>();

        birds.add(new Red(300, 250));
        birds.add(new Blue(250, 150));
        birds.add(new Yellow(150, 150));
        birds.add(new Red(50, 150));


        pigs = new ArrayList<>();

        pigs.add(new Normal(1850, 150));
        pigs.add(new King(1750, 150));
        pigs.add(new General(1650, 150));
        pigs.add(new Normal(1550, 150));
    }

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
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
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
        backgroundTexture.dispose();
        batch.dispose();

        for (Bird bird : birds) {
            bird.dispose();
        }
    }
}

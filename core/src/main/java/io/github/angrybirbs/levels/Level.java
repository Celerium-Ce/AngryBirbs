package io.github.angrybirbs.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;


<<<<<<< HEAD
=======
import io.github.angrybirbs.misc.LoadSave;
>>>>>>> parent of b32d36f (refactored code)
import io.github.angrybirbs.Main;
import io.github.angrybirbs.entities.*;
import io.github.angrybirbs.menu.LevelsMenu;
import io.github.angrybirbs.misc.GameContactListener;
import io.github.angrybirbs.misc.Slingshot;
import io.github.angrybirbs.misc.Slingshotinputprocessor;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Level implements Screen {
    protected Main game;
    protected Texture backgroundTexture;
    protected SpriteBatch batch;
    protected ShapeRenderer shapeRenderer;

    public static final float PPM = 100/3f;
    private World world;

    private Body ground;

    private int levelNum;

    protected List<Bird> birds;
    protected List<Pig> pigs;
    protected List<Material> materials;


    protected List<Bird> initialBirds;
    protected List<Pig> initialPigs;

    protected boolean isPaused;

    private Stage stage;

    private ImageButton pauseButton, menuButton, nextButton, resumeButton, restartButton, saveButton;

    private Image menubg, winImage, looseImage;

    private Slingshot slingshot;
    private Slingshotinputprocessor slingshotinputprocessor;

    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private BitmapFont font;

    private boolean gameEnded;

    public Level(Main game,World world, List<Bird> birds, List<Pig> pigs, List<Material> materials, int levelNum) {
        this.game = game;
        gameEnded = false;
        this.levelNum = levelNum;
        this.world = world;
        world.setContactListener(new GameContactListener());

        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(2*Gdx.graphics.getWidth()/PPM, 2*Gdx.graphics.getHeight()/PPM);


        backgroundTexture = new Texture(Gdx.files.internal("level.png"));
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        slingshot= new Slingshot(new Vector2(350,300));

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0/PPM, (150-50)/PPM); //50 offset to allign correctly lets see how to fix later

        ground = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Gdx.graphics.getWidth(), 1);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1000.0f;
        fixtureDef.friction=100f;
        Fixture fixture = ground.createFixture(fixtureDef);
        ground.setUserData("ground");
        fixture.setUserData("ground");
        shape.dispose();

        this.birds = birds;
        this.pigs = pigs;
        this.materials = materials;
        slingshotinputprocessor = new Slingshotinputprocessor(slingshot,birds.getFirst(),this);
        this.initialBirds = cloneBirds(birds);
        this.initialPigs = new ArrayList<>(pigs);
        this.initialPigs=pigs;
        isPaused = false;

        stage = new Stage();
        //Gdx.input.setInputProcessor(stage);
        Gdx.input.setInputProcessor(slingshotinputprocessor);
        setupButtons();
        setupGameEnd();
    }


    private List<Bird> cloneBirds(List<Bird> birds) {
        List<Bird> clonedBirds = new ArrayList<>();
        for (Bird bird : birds) {
            clonedBirds.add(new Bird(world ,bird.tile, bird.getPosition().x, bird.getPosition().y));
        }
        return clonedBirds;
    }

    private List<Pig> clonePigs(List<Pig> pigs) {
        List<Pig> clonedPigs = new ArrayList<>();
        for (Pig pig : pigs) {
            clonedPigs.add(new Pig(world,pig.tile, pig.getPosition().x, pig.getPosition().y));
        }
        return clonedPigs;
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
//        nextButton.addListener(new ChangeListener() {
//            @Override
//            public void changed(ChangeEvent event, Actor actor) {
//                loadNextLevel();
//            }
//        });
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

//    private void loadNextLevel() {
//        int nextLevelNum = this.levelNum + 1;
//
//        File nextLevelFile = new File(Gdx.files.local("Levels/" + nextLevelNum + ".json").file().getAbsolutePath());
//
//        if (nextLevelFile.exists()) {
//            Level nextLevel = LevelsMenu.createLevelFromJson(nextLevelFile, nextLevelNum);
//            game.setScreen(nextLevel);
//        } else {
//            game.setScreen(new LevelsMenu(game));
//        }
//    }

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
        Level level = new Level(game,world, initialBirds, initialPigs, materials, levelNum);
        game.setScreen(level);
    }

    private boolean checkWinCondition() {

        return pigs.isEmpty() && !gameEnded;

    }

    private void showWinScreen() {
        gameEnded = true;
        showGameEndMenuButtons();
        winImage.setVisible(true);
        Gdx.input.setInputProcessor(stage);
    }

    private boolean checkLooseCondition() {

        return birds.isEmpty() && !gameEnded;
    }

    private void showLooseScreen() {
        gameEnded = true;
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
        Gdx.input.setInputProcessor(stage);
    }

    private void hidePauseMenuButtons() {
        menuButton.remove();
        resumeButton.remove();
        restartButton.remove();
        saveButton.remove();
        menubg.remove();
        Gdx.input.setInputProcessor(slingshotinputprocessor);
    }

    public void togglePause() {
        isPaused = !isPaused;
        if (!isPaused) {
            hidePauseMenuButtons();
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(slingshotinputprocessor);
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
                if (slingshotinputprocessor.activebird == bird) {
                    slingshotinputprocessor.setActiveBird(null);
                }
            }
        }

        Iterator<Pig> pigIterator = pigs.iterator();
        while (pigIterator.hasNext()) {
            Pig pig = pigIterator.next();
            pig.render(batch);
            if (pig.isToBeRemoved()) {
                pig.dispose(world);
                System.out.println(1);
                pigIterator.remove();
            }
        }
        Iterator<Material> materialIterator = materials.iterator();
        while (materialIterator.hasNext()) {
            Material material = materialIterator.next();
            material.render(batch);
            if (material.isToBeRemoved()) {
                material.dispose(world);
                materialIterator.remove();
            }
        }

        batch.end();



        drawGrid();

        if (slingshotinputprocessor.activebird == null && !birds.isEmpty()) {
            slingshotinputprocessor.setActiveBird(birds.iterator().next());
            slingshotinputprocessor.setbirdposition(slingshot.getOrigin());
        }

        slingshot.renderDraggableArea();
        slingshot.render(delta);
        slingshot.renderTrajectory();
        world.step(1/60f, 6, 2);
        stage.act(delta);
        stage.draw();


        camera.update();
        debugRenderer.render(world, camera.combined);

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
    public void resize(int width, int height) {stage.getViewport().update(width, height, true);}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    private void drawGrid() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);

        for (int x = 0; x < Gdx.graphics.getWidth(); x += 50) {
            shapeRenderer.line(x, 0, x, Gdx.graphics.getHeight());
        }

        for (int y = 0; y < Gdx.graphics.getHeight(); y += 50) {
            shapeRenderer.line(0, y, Gdx.graphics.getWidth(), y);
        }

        shapeRenderer.end();

        batch.begin();
        font.setColor(Color.BLACK);
        for (int x = 0; x < Gdx.graphics.getWidth(); x += 50) {
            font.draw(batch, "" + x, x + 5, 15);
        }
        for (int y = 0; y < Gdx.graphics.getHeight(); y += 50) {
            font.draw(batch,""+y, 5, y + 15);
        }

        batch.end();
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
            pig.dispose(world);
        }
    }
}

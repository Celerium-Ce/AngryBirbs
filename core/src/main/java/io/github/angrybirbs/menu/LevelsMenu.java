package io.github.angrybirbs.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.angrybirbs.Main;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import io.github.angrybirbs.entities.*;
import io.github.angrybirbs.levels.Level;
import io.github.angrybirbs.misc.Slingshot;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LevelsMenu extends Menu {
    private Texture backgroundTexture;
    private TextureRegion bgTextureRegion;
    private TextureRegionDrawable bgDrawable;
    private Image bgImage;
    private ImageButton back;
    private ArrayList<ImageTextButton> levels;

    public LevelsMenu(Main game) {
        super(game);
        backgroundTexture = new Texture(Gdx.files.internal("MainMenuBG.png"));
        bgTextureRegion = new TextureRegion(backgroundTexture);
        bgDrawable = new TextureRegionDrawable(bgTextureRegion);
        bgImage = new Image(bgDrawable);
        bgImage.setFillParent(true);
        bgImage.setZIndex(0);
        stage.addActor(bgImage);

        back = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/back.png")))),
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/back.png"))))
        );

        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
                dispose();
            }
        });

        back.setSize(Gdx.graphics.getWidth() / 10f, Gdx.graphics.getHeight() / 10f);
        back.setPosition(0 - Gdx.graphics.getWidth() / 50f, 0);
        stage.addActor(back);

        levels = new ArrayList<ImageTextButton>();

        File levelDataDir = new File(Gdx.files.local("../Levels").file().getAbsolutePath());
        if (!(levelDataDir.exists())) {
            levelDataDir = new File(Gdx.files.local("Levels").file().getAbsolutePath());
        }

        File[] levelFiles = levelDataDir.listFiles((dir, name) -> name.endsWith(".tmx"));

        if (levelFiles != null) {
            Skin skin = new Skin(Gdx.files.internal("skin/cloud-form-ui.json"));
            Table table = new Table();
            table.setFillParent(true);
            stage.addActor(table);

            for (int i = 0; i < levelFiles.length; i++) {
                File file = levelFiles[i];
                ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
                style.font = skin.getFont("title");
                style.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("Buttons/empty.png")));

                ImageTextButton saveButton = new ImageTextButton("Level " + (i + 1), style);

                Table buttonTable = new Table();
                buttonTable.add(saveButton.getImage()).expand().bottom().row();
                buttonTable.add(saveButton.getLabel()).expand().center().padBottom(10f);

                saveButton.clearChildren();
                saveButton.add(buttonTable).expand().fill();

                int finalI = i;
                saveButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                        Level level = loadLevelFromFile(finalI + 1);
                        if (level != null) {
                            game.setScreen(level);
                        }
                        dispose();
                    }
                });

                levels.add(saveButton);
                table.add(saveButton).size(Gdx.graphics.getWidth() / 10f, Gdx.graphics.getHeight() / 10f).pad(10);

                if (levels.size() % 5 == 0) {
                    table.row();
                }
            }
        }
        else {
            ImageButton noLevels = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/noSaves.png")))),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/noSaves.png"))))
            );

            noLevels.setSize(Gdx.graphics.getWidth() / 5f, Gdx.graphics.getHeight() / 5f);
            noLevels.setPosition(
                Gdx.graphics.getWidth() / 2f - noLevels.getWidth() / 2,
                Gdx.graphics.getHeight() / 2f - noLevels.getHeight() / 2
            );
            stage.addActor(noLevels);
        }
    }

    public static Level loadLevelFromFile(int levelNum) {
        World world = new World(new Vector2(0, -9.8f), true);
        Slingshot slingshot = null;
        File levelDataDir = new File(Gdx.files.local("../Levels").file().getAbsolutePath());

        if (!levelDataDir.exists()) {
            levelDataDir = new File(Gdx.files.local("Levels").file().getAbsolutePath());
        }

        String fileName = levelDataDir.getAbsolutePath() + "/" + levelNum + ".tmx";
        File levelFile = new File(fileName);

        if (!levelFile.exists()) {
            System.out.println("Level file not found: " + fileName);
            return null;
        }
        SpriteBatch batch = new SpriteBatch();

        TiledMap tiledMap = new TmxMapLoader().load(fileName);

        OrthogonalTiledMapRenderer tiledMapRenderer;
        OrthographicCamera camera = new OrthographicCamera();

        ArrayList<Bird> birds = new ArrayList<>();
        ArrayList<Pig> pigs = new ArrayList<>();
        ArrayList<Material> materials = new ArrayList<>();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        MapLayer objectLayer = tiledMap.getLayers().get("Ground");
        MapObject groundObject = objectLayer.getObjects().get("Ground");
        float groundY = 0;
        if (groundObject != null) {
            groundY = (float) groundObject.getProperties().get("y", Float.class);
            System.out.println("Ground Y coordinate: " + groundY);
        }
                MapLayer layer = tiledMap.getLayers().get("Objects");
                for (MapObject obj : layer.getObjects()) {
                    if (obj instanceof TiledMapTileMapObject) {
                        TiledMapTileMapObject tileObject = (TiledMapTileMapObject) obj;

                        // Get the position of the tile
                        float x = tileObject.getX();
                        float y = tileObject.getY();
                        TiledMapTile tile = tileObject.getTile();

                        // Check the properties to determine the type of object
                        String entityType = (String) tileObject.getProperties().get("type");
                        if ("Slingshot".equals(entityType)) {
                            slingshot = new Slingshot(new Vector2(350, 300), tile, x, y);
                            System.out.println("Loaded Slingshot at: (" + x + ", " + y + ")");
                        } else if ("Red".equals(entityType)) {
                            birds.add(new Red(world, tile, x, y));
                            System.out.println("Loaded Red Bird at: (" + x + ", " + y + ")");
                        } else if ("Blue".equals(entityType)) {
                            birds.add(new Blue(world, tile, x, y));
                            System.out.println("Loaded Blue Bird at: (" + x + ", " + y + ")");
                        } else if ("Yellow".equals(entityType)) {
                            birds.add(new Yellow(world, tile, x, y));
                            System.out.println("Loaded Yellow Bird at: (" + x + ", " + y + ")");
                        } else if ("Normal".equals(entityType)) {
                            pigs.add(new Normal(world, tile, x, y));
                            System.out.println("Loaded Normal Pig at: (" + x + ", " + y + ")");
                        } else if ("General".equals(entityType)) {
                            pigs.add(new General(world, tile, x, y));
                            System.out.println("Loaded General Pig at: (" + x + ", " + y + ")");
                        } else if ("King".equals(entityType)) {
                            pigs.add(new King(world, tile, x, y));
                            System.out.println("Loaded King Pig at: (" + x + ", " + y + ")");
                        } else if ("Wood".equals(entityType)) {
                            materials.add(new Wood(tile, x, y, world));
                            System.out.println("Loaded wood at: (" + x + ", " + y + ")");
                        } else if ("Ice".equals(entityType)) {
                            materials.add(new Ice(tile, x, y, world));
                            System.out.println("Loaded ice at: (" + x + ", " + y + ")");
                        } else if ("Steel".equals(entityType)) {
                            materials.add(new Steel(tile, x, y, world));
                            System.out.println("Loaded steel at: (" + x + ", " + y + ")");
                        }
                    }
                }
                sortBirds(birds);
        return new Level(game, world, slingshot, birds, pigs, materials, levelNum, groundY);

    }
    public static void sortBirds(ArrayList<Bird> birds) {
        Collections.sort(birds, new Comparator<Bird>() {
            @Override
            public int compare(Bird b1, Bird b2) {
                int xComparison = Float.compare(b2.getPosition().x, b1.getPosition().x);
                if (xComparison != 0) {
                    return xComparison;
                }
                return 0;
            }
        });
    }
}

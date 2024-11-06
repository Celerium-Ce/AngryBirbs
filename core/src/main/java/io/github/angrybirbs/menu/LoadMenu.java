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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import io.github.angrybirbs.Main;
import com.badlogic.gdx.Gdx;
import io.github.angrybirbs.entities.*;
import io.github.angrybirbs.levels.Level;
import io.github.angrybirbs.misc.Slingshot;


import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LoadMenu extends Menu{

    private TextureRegion bgTextureReigon;
    private TextureRegionDrawable bgDrawable;
    private Image bgImage;
    private ImageButton back;
    private ArrayList<ImageTextButton> saves;
    private int saveFileCount = 0;

    public LoadMenu(Main game) {
        super(game);
        backgroundTexture = new Texture(Gdx.files.internal("MainMenuBG.png"));
        bgTextureReigon = new TextureRegion(backgroundTexture);
        bgDrawable = new TextureRegionDrawable(bgTextureReigon);
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

        back.setSize(Gdx.graphics.getWidth()/10f, Gdx.graphics.getHeight()/10f);

        back.setPosition(
            0 - Gdx.graphics.getWidth()/50f,
            0
        );

        stage.addActor(back);

        saves = new ArrayList<ImageTextButton>();

        File saveDataDir = new File(Gdx.files.local("SaveData").file().getAbsolutePath());
        File[] saveFiles = saveDataDir.listFiles((dir, name) -> name.endsWith(".json"));

        if (saveFiles != null) {
            Skin skin = new Skin(Gdx.files.internal("skin/cloud-form-ui.json"));
            Table table = new Table();
            table.setFillParent(true);
            stage.addActor(table);

            for (File file : saveFiles) {
                ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
                style.font = skin.getFont("title");
                style.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("Buttons/empty.png")));

                ImageTextButton saveButton = new ImageTextButton(file.getName().substring(0, file.getName().indexOf(".")), style);

                Table buttonTable = new Table();
                buttonTable.add(saveButton.getImage()).expand().bottom().row();
                buttonTable.add(saveButton.getLabel()).expand().center().padBottom(10f);

                saveButton.clearChildren();
                saveButton.add(buttonTable).expand().fill();

                saveButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        String levelNum = saveButton.getText().toString();

                        Level level = loadLevelFromFile(file.getName());
                        game.setScreen(level);
                        System.out.println("Loading save: " + file.getName());
                    }
                });

                saves.add(saveButton);
                table.add(saveButton).size(Gdx.graphics.getWidth() / 10f, Gdx.graphics.getHeight() / 10f).pad(10);

                if (saves.size() % 5 == 0) {
                    table.row();
                }
            }
        }
        else {
            ImageButton noSaves = new ImageButton(
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/noSaves.png")))),
                new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/noSaves.png"))))
            );

            noSaves.setSize(Gdx.graphics.getWidth()/5f, Gdx.graphics.getHeight()/5f);
            noSaves.setPosition(
                Gdx.graphics.getWidth() / 2f - noSaves.getWidth() / 2 ,
                Gdx.graphics.getHeight() / 2f - noSaves.getHeight() / 2
            );
            stage.addActor(noSaves);
        }
    }

    private Level loadLevelFromFile(String fileName) {
        World world = new World(new Vector2(0, -9.8f), true);
        Slingshot slingshot = null;
        File levelDataDir = new File(Gdx.files.local("../Levels").file().getAbsolutePath());

        if (!levelDataDir.exists()) {
            levelDataDir = new File(Gdx.files.local("Levels").file().getAbsolutePath());
        }

        fileName = levelDataDir.getAbsolutePath() + "/" + fileName;

        SpriteBatch batch = new SpriteBatch();
        ;
        TiledMap tiledMap = new TmxMapLoader().load(fileName);

        OrthogonalTiledMapRenderer tiledMapRenderer;
        OrthographicCamera camera = new OrthographicCamera();
        ;
        ArrayList<Bird> birds = new ArrayList<>();
        ArrayList<Pig> pigs = new ArrayList<>();
        ArrayList<Material> materials = new ArrayList<>();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        MapLayer objectLayer = tiledMap.getLayers().get("Ground");
        MapObject groundObject = objectLayer.getObjects().get("Ground");

        if (groundObject != null) {
            float groundY = (float) groundObject.getProperties().get("y", Float.class);
            System.out.println("Ground Y coordinate: " + groundY);
        } else {
            System.out.println("Ground object not found!");
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


        return new Level(game, world, slingshot, birds, pigs, materials, 1);
    }
    public void sortBirds(ArrayList<Bird> birds) {
        Collections.sort(birds, new Comparator<Bird>() {
            @Override
            public int compare(Bird b1, Bird b2) {
                // First compare by x position (max x first)
                int xComparison = Float.compare(b2.getPosition().x, b1.getPosition().x);
                if (xComparison != 0) {
                    return xComparison; // If x positions are different, sort by x
                }
                // If x positions are the same, compare by y position (min y first)
                return 0;
            }
        });
    }}

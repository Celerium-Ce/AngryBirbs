package io.github.angrybirbs.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

import java.io.File;
import java.util.ArrayList;

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

        File levelDataDir = new File(Gdx.files.local("Levels").file().getAbsolutePath());
        File[] levelFiles = levelDataDir.listFiles((dir, name) -> name.endsWith(".json"));

        if (levelFiles != null) {
            Skin skin = new Skin(Gdx.files.internal("skin/cloud-form-ui.json"));
            Table table = new Table();
            table.setFillParent(true);
            stage.addActor(table);

            for (File file : levelFiles) {
                ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
                style.font = skin.getFont("title");
                style.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("Buttons/empty.png")));

                ImageTextButton saveButton = new ImageTextButton(file.getName().substring(0, file.getName().indexOf(".")), style);

                Table buttonTable = new Table();
                buttonTable.add(saveButton.getImage()).expand().bottom().row();
                buttonTable.add(saveButton.getLabel()).expand().center().padBottom(10f);

                saveButton.clearChildren();
                saveButton.add(buttonTable).expand().fill();

                // Listener for loading save
                saveButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        String levelNum = saveButton.getText().toString();

                        Level level = createLevelFromJson(file, Integer.parseInt(levelNum));
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

    public static Level createLevelFromJson(File file, int levelNum) {
        Json json = new Json();

        String jsonString = Gdx.files.absolute(file.getAbsolutePath()).readString();
        JsonValue jsonData = json.fromJson(null, jsonString);

        ArrayList<Bird> birds = new ArrayList<>();
        ArrayList<Pig> pigs = new ArrayList<>();

        World world = new World(new Vector2(0, -9.8f), true);

        JsonValue birdsData = jsonData.get("bird");
        for (JsonValue birdEntry : birdsData) {
            String type = birdEntry.name();
            for (JsonValue position : birdEntry) {
                float x = position.get(0).asFloat();
                float y = position.get(1).asFloat();
                switch (type) {
                    case "red":
                        birds.add(new Red(world,(int) x, (int) y));
                        break;
                    case "blue":
                        birds.add(new Blue(world, (int) x, (int) y));
                        break;
                    case "yellow":
                        birds.add(new Yellow(world, (int) x, (int) y));
                        break;
                }
            }
        }

        JsonValue pigsData = jsonData.get("pig");
        for (JsonValue pigEntry : pigsData) {
            String type = pigEntry.name();
            for (JsonValue position : pigEntry) {
                float x = position.get(0).asFloat();
                float y = position.get(1).asFloat();
                switch (type) {
                    case "king":
                        pigs.add(new King((int) x, (int) y));
                        break;
                    case "normal":
                        pigs.add(new Normal((int) x, (int) y));
                        break;
                    case "general":
                        pigs.add(new General((int) x, (int) y));
                        break;
                }
            }
        }

        return new Level(game, world, birds, pigs, levelNum);
    }
}

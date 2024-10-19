package io.github.angrybirbs.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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


import java.io.File;
import java.util.ArrayList;

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

                        Level level = createLevelFromJson(file, Integer.parseInt(levelNum));
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

    private Level createLevelFromJson(File file, int levelNum) {
        Json json = new Json();

        String jsonString = Gdx.files.absolute(file.getAbsolutePath()).readString();
        JsonValue jsonData = json.fromJson(null, jsonString);

        ArrayList<Bird> birds = new ArrayList<>();
        ArrayList<Pig> pigs = new ArrayList<>();

        JsonValue birdsData = jsonData.get("bird");
        for (JsonValue birdEntry : birdsData) {
            String type = birdEntry.name();
            for (JsonValue position : birdEntry) {
                float x = position.get(0).asFloat();
                float y = position.get(1).asFloat();
                switch (type) {
                    case "red":
                        birds.add(new Red((int) x, (int) y));
                        break;
                    case "blue":
                        birds.add(new Blue((int) x, (int) y));
                        break;
                    case "yellow":
                        birds.add(new Yellow((int) x, (int) y));
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

        return new Level(game, birds, pigs, levelNum);
    }
}

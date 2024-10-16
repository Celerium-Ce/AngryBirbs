package io.github.angrybirbs.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.angrybirbs.Main;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.io.File;
import java.util.ArrayList;

public class LoadMenu extends Menu{

    private TextureRegion bgTextureReigon;
    private TextureRegionDrawable bgDrawable;
    private Image bgImage;
    private ImageButton back;
    private ArrayList<ImageButton> saves;
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

        saves = new ArrayList<ImageButton>();

        File saveDataDir = new File(Gdx.files.local("SaveData").file().getAbsolutePath());
        File[] saveFiles = saveDataDir.listFiles((dir, name) -> name.endsWith(".bat"));

        if (saveFiles != null) {
            Table table = new Table();
            table.setFillParent(true);
            stage.addActor(table);

            for (File file : saveFiles) {
                ImageButton saveButton = new ImageButton(
                    new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/back.png")))),
                    new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/back.png"))))
                );

                saveButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        // Handle save button click
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
}

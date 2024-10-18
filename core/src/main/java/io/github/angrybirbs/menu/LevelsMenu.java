package io.github.angrybirbs.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.angrybirbs.Main;
import java.io.File;
import java.util.ArrayList;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import io.github.angrybirbs.levels.Level1;
import io.github.angrybirbs.levels.Level2;
import io.github.angrybirbs.levels.Level3;

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

        File levelDataDir = new File(Gdx.files.local("SaveData/Levels").file().getAbsolutePath());
        File[] levelFiles = levelDataDir.listFiles((dir, name) -> name.endsWith(".ser"));

        if (levelFiles != null) {
            Skin skin = new Skin(Gdx.files.internal("skin/cloud-form-ui.json"));
            Table table = new Table();
            table.setFillParent(true);
            stage.addActor(table);

            for (File file : levelFiles) {
                String levelName = file.getName().substring(0, file.getName().indexOf("."));
                ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
                style.font = skin.getFont("title");
                style.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("Buttons/empty.png")));

                ImageTextButton saveButton = new ImageTextButton(levelName, style);

                Table buttonTable = new Table();
                buttonTable.add(saveButton.getImage()).expand().bottom().row();
                buttonTable.add(saveButton.getLabel()).expand().center().padBottom(10f);

                saveButton.clearChildren();
                saveButton.add(buttonTable).expand().fill();

                saveButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        switch (levelName) {
                            case "1":
                                game.setScreen(new Level1(game));
                                break;
                            case "2":
                                game.setScreen(new Level2(game));
                                break;
                            case "3":
                                game.setScreen(new Level3(game));
                                break;
                            // Add cases for other levels as needed
                            default:
                                // Handle unknown level or do nothing
                                break;
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
        } else {
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
}

package io.github.angrybirbs.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.angrybirbs.misc.LoadSave;
import io.github.angrybirbs.Main;
import io.github.angrybirbs.levels.Level;


import java.io.File;
import java.util.ArrayList;
// necessary imports

public class LoadMenu extends Menu{

    // LoadMenu class that extends Menu
    // This class is used to create the load menu of the game
    // This menu is used to load saved games and delete them

    private TextureRegion bgTextureReigon;
    private TextureRegionDrawable bgDrawable;
    private Image bgImage;
    private ImageButton back;
    private ArrayList<ImageTextButton> saves;
    private int saveFileCount = 0;

    public LoadMenu(Main game) {

        // Constructor for the LoadMenu class

        // Calls the constructor of the Menu class
        super(game);

        // Sets the background texture of the load menu
        backgroundTexture = new Texture(Gdx.files.internal("MainMenuBG.png"));
        bgTextureReigon = new TextureRegion(backgroundTexture);
        bgDrawable = new TextureRegionDrawable(bgTextureReigon);
        bgImage = new Image(bgDrawable);
        bgImage.setFillParent(true);
        bgImage.setZIndex(0);
        stage.addActor(bgImage);

        /*
        How a button is created:
        new ImageButton is created with two TextureRegionDrawables as parameters
        The first TextureRegionDrawable is the image of the button when it is not pressed
        The second TextureRegionDrawable is the image of the button when it is pressed
        The button is then added to the stage
        A ChangeListener is added to the button to listen for changes
         */

        // Sets the back button of the load menu
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


        // Load the save files a
        saves = new ArrayList<ImageTextButton>();

        // Load the save files
        File saveDataDir = new File(Gdx.files.local("../SaveData").file().getAbsolutePath());
        if (!(saveDataDir.exists())) {
            saveDataDir = new File(Gdx.files.local("SaveData").file().getAbsolutePath());
        }
        File[] saveFiles = saveDataDir.listFiles((dir, name) -> name.endsWith(".ser"));

        // If there are save files, create buttons for each save file
        if (saveFiles != null) {
            Skin skin = new Skin(Gdx.files.internal("skin/cloud-form-ui.json"));
            Table table = new Table();
            table.setFillParent(true);
            stage.addActor(table);
            for (int i = 0; i < saveFiles.length; i++) {
                File file = saveFiles[i];

                // Create a button for each save file
                ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
                style.font = skin.getFont("title");
                style.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("Buttons/empty.png")));

                ImageTextButton saveButton = new ImageTextButton("Save " + (i + 1), style);

                // Create a table to hold the button and its label
                Table buttonTable = new Table();
                buttonTable.add(saveButton.getImage()).expand().bottom().row();
                buttonTable.add(saveButton.getLabel()).expand().center().padBottom(10f);

                saveButton.clearChildren();
                saveButton.add(buttonTable).expand().fill();

                int finalI = i;
                // Add a listener to the button to load the save file
                saveButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                        World world = new World(new Vector2(0, -9.8f), true);
                        Level level = LoadSave.loadLevel(game, world, finalI + 1);
                        if (level != null) {
                            game.setScreen(level);
                        }
                        dispose();
                    }
                });

                ImageButton.ImageButtonStyle deleteStyle = new ImageButton.ImageButtonStyle();
                deleteStyle.imageUp = new TextureRegionDrawable(new Texture(Gdx.files.internal("Buttons/del.png")));
                ImageButton deleteButton = new ImageButton(deleteStyle);

                // Add a listener to the delete button to delete the save file
                deleteButton.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                        if (file.delete()) {
                            System.out.println("Save file " + (finalI + 1) + " deleted successfully.");
                            game.setScreen(new LoadMenu(game));
                            dispose();
                        } else {
                            System.out.println("Failed to delete save file " + (finalI + 1) + ".");
                        }
                    }
                });

                Table combinedTable = new Table();
                combinedTable.add(saveButton).size(100, 100);
                combinedTable.add(deleteButton).size(35, 35).top().left();

                saves.add(saveButton);
                table.add(combinedTable).size(Gdx.graphics.getWidth() / 10f, Gdx.graphics.getHeight() / 10f).pad(10);

                if (saves.size() % 5 == 0) {
                    table.row();
                }
            }
        }
        else {
            // If there are no save files, display a message
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

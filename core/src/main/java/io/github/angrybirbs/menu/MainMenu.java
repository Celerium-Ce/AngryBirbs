package io.github.angrybirbs.menu;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.angrybirbs.Main;
// necessary imports
/** First screen of the application. Displayed after the application is created. */
public class MainMenu extends Menu {

    // MainMenu class that extends Menu
    // This class is used to create the main menu of the game

    private TextureRegion bgTextureReigon;
    private TextureRegionDrawable bgDrawable;
    private Texture titletext;
    private Image titleimage;
    private Image bgImage;
    private ImageButton play;
    private ImageButton exit;
    private ImageButton load;

    public MainMenu(Main game) {

        // Constructor for the MainMenu class

        // Calls the constructor of the Menu class
        super(game);

        // Sets the background texture of the main menu
        backgroundTexture = new Texture(Gdx.files.internal("MainMenuBG.png"));
        bgTextureReigon = new TextureRegion(backgroundTexture);
        bgDrawable = new TextureRegionDrawable(bgTextureReigon);
        bgImage = new Image(bgDrawable);
        bgImage.setFillParent(true);
        bgImage.setZIndex(0);
        stage.addActor(bgImage);

        // Sets the title image of the main menu
        titletext = new Texture(Gdx.files.internal("Buttons/AngryBirbs.png"));
        titleimage = new Image(new TextureRegionDrawable(new TextureRegion(titletext)));
        titleimage.setZIndex(1);
        stage.addActor(titleimage);
        titleimage.setSize(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
        titleimage.setPosition(
            Gdx.graphics.getWidth() / 2f - titleimage.getWidth() / 2,
            Gdx.graphics.getHeight() / 2f - titleimage.getHeight() / 2 + Gdx.graphics.getHeight() / 4f
        );

        /*
        How a button is created:
        new ImageButton is created with two TextureRegionDrawables as parameters
        The first TextureRegionDrawable is the image of the button when it is not pressed
        The second TextureRegionDrawable is the image of the button when it is pressed
        The button is then added to the stage
        A ChangeListener is added to the button to listen for changes
         */


        // Sets the play button of the main menu
        play = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/levels.png")))),
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/levelsp.png"))))
        );
        play.setSize(Gdx.graphics.getWidth()/5f, Gdx.graphics.getHeight()/5f);
        play.setPosition(
            Gdx.graphics.getWidth() / 2f - play.getWidth() / 2 ,
            Gdx.graphics.getHeight() / 2f - play.getHeight() / 2 - Gdx.graphics.getHeight()/10f
        );
        stage.addActor(play);
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LevelsMenu(game));
                dispose();

            }
        });

        // Sets the exit button of the main menu
        exit = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/exit.png")))),
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/exit.png"))))
        );

        stage.addActor(exit);
        exit.setSize(Gdx.graphics.getWidth()/10f, Gdx.graphics.getHeight()/10f);

        exit.setPosition(
            0 - Gdx.graphics.getWidth()/50f,
            0
        );

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                Gdx.app.exit();
            }
        });

        // Sets the load button of the main menu
        load = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/LoadGame.png")))),
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/LoadGamep.png"))))
        );

        load.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new LoadMenu(game));
                dispose();
            }
        });

        stage.addActor(load);



        load.setSize(Gdx.graphics.getWidth()/10f, Gdx.graphics.getHeight()/10f);
        load.setPosition(
            Gdx.graphics.getWidth()/2f - load.getWidth()/2,
            Gdx.graphics.getHeight()/2f - load.getHeight()/2 - Gdx.graphics.getHeight()/5f
        );

    }



}

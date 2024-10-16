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

/** First screen of the application. Displayed after the application is created. */
public class MainMenu extends Menu {
    private TextureRegion bgTextureReigon;
    private TextureRegionDrawable bgDrawable;
    private Texture titletext;
    private Image titleimage;
    private Image bgImage;
    private ImageButton play;
    private ImageButton exit;

    public MainMenu(Main game) {
        super(game);
        backgroundTexture = new Texture(Gdx.files.internal("MainMenuBG.png"));
        bgTextureReigon = new TextureRegion(backgroundTexture);
        bgDrawable = new TextureRegionDrawable(bgTextureReigon);
        bgImage = new Image(bgDrawable);
        bgImage.setFillParent(true);
        bgImage.setZIndex(0);
        stage.addActor(bgImage);

        titletext = new Texture(Gdx.files.internal("Buttons/AngryBirbs.png"));
        titleimage = new Image(new TextureRegionDrawable(new TextureRegion(titletext)));
        titleimage.setZIndex(1);
        stage.addActor(titleimage);
        titleimage.setSize(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f);
        titleimage.setPosition(
            Gdx.graphics.getWidth() / 2f - titleimage.getWidth() / 2,
            Gdx.graphics.getHeight() / 2f - titleimage.getHeight() / 2 + Gdx.graphics.getHeight() / 4f
        );


        play = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/Play.png")))),
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/Play.png"))))
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
                dispose();
                Gdx.app.exit();
            }
        });


        exit = new ImageButton(
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/exit.png")))),
            new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Buttons/exit.png"))))
        );

        stage.addActor(exit);
        exit.setSize(Gdx.graphics.getWidth()/7f, Gdx.graphics.getHeight()/7f);

        exit.setPosition(
            0 - Gdx.graphics.getWidth()/30f,
            0
        );

        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                Gdx.app.exit();
            }
        });

        render(1f);
    }



}

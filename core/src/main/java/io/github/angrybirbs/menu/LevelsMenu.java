package io.github.angrybirbs.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.angrybirbs.Main;

public class LevelsMenu extends Menu {
    private TextureRegion bgTextureReigon;
    private TextureRegionDrawable bgDrawable;
    private Image bgImage;
    private ImageButton back;
    public LevelsMenu(Main game) {
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


    }
}

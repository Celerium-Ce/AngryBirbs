package io.github.angrybirbs.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import io.github.angrybirbs.entities.Bird;
import io.github.angrybirbs.entities.Pig;

public class GameContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        if (birdandground(contact)){
            Gdx.app.log("GameContactListener", "Bird hit the ground");
            Object userDataA = contact.getFixtureA().getUserData();
            Object userDataB = contact.getFixtureB().getUserData();

            if (userDataB instanceof Bird) {
                Gdx.app.log("GameContactListener", "Object B");
                ((Bird) userDataB).setDead();
            }
            if (userDataA instanceof Bird) {
                Gdx.app.log("GameContactListener", "Object A");
                ((Bird) userDataA).setDead();
            }
        }

        if (birdandpig(contact)){
            Gdx.app.log("GameContactListener", "Bird hit the pig");
            Object userDataA = contact.getFixtureA().getUserData();
            Object userDataB = contact.getFixtureB().getUserData();

            if (userDataB instanceof Pig) {
                //Gdx.app.log("GameContactListener", "Object B");
                ((Pig) userDataB).setDead();
            }
            if (userDataA instanceof Pig) {
                //Gdx.app.log("GameContactListener", "Object A");
                ((Pig) userDataA).setDead();
            }

            /*if (userDataB instanceof Bird) {
                //Gdx.app.log("GameContactListener", "Object B");
                ((Bird) userDataB).setDead();
            }

            if (userDataA instanceof Bird) {
                //Gdx.app.log("GameContactListener", "Object A");
                ((Bird) userDataA).setDead();
            }*/
        }
        //Gdx.app.log("GameContactListener", "beginContact");
    }

    @Override
    public void endContact(Contact contact) {
        //Gdx.app.log("GameContactListener", "endContact");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //Gdx.app.log("GameContactListener", "preSolve");
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        //Gdx.app.log("GameContactListener", "postSolve");
    }

    private boolean birdandground(Contact contact) {
        return contact.getFixtureA().getUserData() instanceof Bird && contact.getFixtureB().getUserData().equals("ground") ||
                contact.getFixtureA().getUserData().equals("ground") && contact.getFixtureB().getUserData() instanceof Bird;
    }

    private boolean birdandpig(Contact contact) {
        return contact.getFixtureA().getUserData() instanceof Bird && contact.getFixtureB().getUserData() instanceof Pig ||
                contact.getFixtureA().getUserData() instanceof Pig && contact.getFixtureB().getUserData() instanceof Bird;
    }
}

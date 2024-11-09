package io.github.angrybirbs.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.angrybirbs.entities.Bird;
import io.github.angrybirbs.entities.Material;
import io.github.angrybirbs.entities.Pig;

public class GameContactListener implements ContactListener {
    private static final float DAMAGE_MULTIPLIER = 0.1f;
    private static final float MIN_VELOCITY = 0.5f;

    @Override
    public void beginContact(Contact contact) {
        Object userDataA = contact.getFixtureA().getUserData();
        Object userDataB = contact.getFixtureB().getUserData();

        Gdx.app.log("GameContactListener", "Collision detected between objects: ");
        Gdx.app.log("GameContactListener", "Fixture A: " + (userDataA != null ? userDataA.getClass().getSimpleName() : "No data"));
        Gdx.app.log("GameContactListener", "Fixture B: " + (userDataB != null ? userDataB.getClass().getSimpleName() : "No data"));
    }

    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("GameContactListener", "Contact ended.");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) { // disable contact of inactive birds and materials on bird side
        Object userDataA = contact.getFixtureA().getUserData();
        Object userDataB = contact.getFixtureB().getUserData();

        Bird activeBird = Slingshotinputprocessor.getActiveBird();
        boolean isBirdA = userDataA instanceof Bird;
        boolean isBirdB = userDataB instanceof Bird;

        if (isBirdA && !activeBird.equals(userDataA)) {
            contact.setEnabled(false);
        } else if (isBirdB && !activeBird.equals(userDataB)) {
            contact.setEnabled(false);
        }

        boolean isMaterialA = userDataA instanceof Material;
        boolean isMaterialB = userDataB instanceof Material;

        if (isMaterialA) {
            Material materialA = (Material) userDataA;
            if (materialA.getPosition().x < 500) {
                contact.setEnabled(false);
                materialA.getBody().setGravityScale(0);
            }
        }

        if (isMaterialB) {
            Material materialB = (Material) userDataB;
            if (materialB.getPosition().x < 500) {
                contact.setEnabled(false);
                materialB.getBody().setGravityScale(0);
            }
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        float impactForce = impulse.getNormalImpulses()[0];

        Object userDataA = contact.getFixtureA().getUserData();
        Object userDataB = contact.getFixtureB().getUserData();

        applyDamage(userDataA, userDataB, impactForce);
        applyDamage(userDataB, userDataA, impactForce);

        if ((userDataA instanceof Bird) || (userDataB instanceof Bird)) {
            Bird bird = (userDataA instanceof Bird) ? (Bird) userDataA : (Bird) userDataB;
            float friction = getFriction(userDataA, userDataB);
            bird.reduceSpeed(1-friction);
            if (bird.getVelocity().len() < MIN_VELOCITY) {
                bird.setVelocity(Vector2.Zero);
                bird.setDead();
            }
        }

        if ((userDataA instanceof Pig) || (userDataB instanceof Pig)) {
            Pig pig = (userDataA instanceof Pig) ? (Pig) userDataA : (Pig) userDataB;
            float friction = getFriction(userDataA, userDataB);
            pig.reduceSpeed(1-friction);
            if (pig.getVelocity().len() < MIN_VELOCITY) {
                pig.setVelocity(Vector2.Zero);
            }
        }

    }

    private float getFriction(Object userDataA, Object userDataB) {
        if ((userDataA instanceof Material) || (userDataB instanceof Material)) {
            Material Material = (userDataA instanceof Material) ? (Material) userDataA : (Material) userDataB;
            Body otherBody = Material.getBody();
            float friction = 0.1f;
            for (Fixture fixture : otherBody.getFixtureList()) {
                friction = fixture.getFriction();
                break;
            }
            return friction;
        }
        return 0.1f;
    }

    private void applyDamage(Object source, Object target, float impactForce) {
        if (source == null || target == null) return;

        Vector2 velocityA = ((source instanceof Bird) || (source instanceof Pig) || (source instanceof Material))
            ? ((source instanceof Bird) ? ((Bird) source).getBody()
            : (source instanceof Pig) ? ((Pig) source).getBody()
            : ((Material) source).getBody()).getLinearVelocity()
            : new Vector2(0, 0);

        Vector2 velocityB = ((target instanceof Bird) || (target instanceof Pig) || (target instanceof Material))
            ? ((target instanceof Bird) ? ((Bird) target).getBody()
            : (target instanceof Pig) ? ((Pig) target).getBody()
            : ((Material) target).getBody()).getLinearVelocity()
            : new Vector2(0, 0);

        float relativeVelocity = velocityA.sub(velocityB).len();

        if (relativeVelocity >= MIN_VELOCITY) {
            int calculatedDamage = (int) (impactForce * DAMAGE_MULTIPLIER);

            if (target instanceof Pig) {
                Pig pig = (Pig) target;
                pig.takeDamage(calculatedDamage);
            } else if (target instanceof Material) {
                Material material = (Material) target;
                material.takeDamage(calculatedDamage);
            }
        }
    }

}

package io.github.angrybirbs.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.angrybirbs.entities.Birds.Bird;
import io.github.angrybirbs.entities.Materials.Material;
import io.github.angrybirbs.entities.Pigs.Pig;
// necessary imports

// This class is responsible for handling the collision events between the game objects.
public class GameContactListener implements ContactListener {

    // Constants
    private static final float DAMAGE_MULTIPLIER = 0.1f; // The damage multiplier for the impact force
    private static final float MIN_VELOCITY = 0.5f; // The minimum velocity for the objects to be considered moving

    @Override
    public void beginContact(Contact contact) {

        // Get the user data of the fixtures involved in the collision ad log them for debugging purposes

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
    public void preSolve(Contact contact, Manifold oldManifold) {

        // disable contact of birds and materials on bird side

        Object userDataA = contact.getFixtureA().getUserData();
        Object userDataB = contact.getFixtureB().getUserData();

        boolean isBirdA = userDataA instanceof Bird;
        boolean isBirdB = userDataB instanceof Bird;

        // Check if the objects are birds
        if (isBirdA && isBirdB) {
            contact.setEnabled(false);
        }


        // Disable contact of materials on the bird side
        boolean isMaterialA = userDataA instanceof Material;
        boolean isMaterialB = userDataB instanceof Material;

        // Check if the objects are materials and if they are on the bird side
        if (isMaterialA) {
            Material materialA = (Material) userDataA;
            if (materialA.getPosition().x < 500) {
                contact.setEnabled(false);
                materialA.getBody().setGravityScale(0);
            }
        }

        // Check if the objects are materials and if they are on the bird side
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

        // Calculate the impact force and apply damage to the objects involved in the collision

        float impactForce = impulse.getNormalImpulses()[0]; // Get the normal impulse from the impulse


        //Get the user data of the fixtures involved in the collision
        Object userDataA = contact.getFixtureA().getUserData();
        Object userDataB = contact.getFixtureB().getUserData();

        // Apply damage to the objects
        applyDamage(userDataA, userDataB, impactForce);
        applyDamage(userDataB, userDataA, impactForce);

        // For the bird reduce speed and kill if speed is less than minimum velocity
        if ((userDataA instanceof Bird) || (userDataB instanceof Bird)) {
            Bird bird = (userDataA instanceof Bird) ? (Bird) userDataA : (Bird) userDataB;
            float friction = getFriction(userDataA, userDataB);
            bird.reduceSpeed(1-friction);
            if (bird.getVelocity().len() < MIN_VELOCITY) {
                bird.setVelocity(Vector2.Zero);
                bird.setDead();
            }
        }

        // For the pig reduce speed and set velocity to zero if speed is less than minimum velocity
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

        // Get the friction of the material object

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

        // Apply damage to the target object based on the impact force and relative velocity of the source and target objects

        // Check if the source and target objects are not null
        if (source == null || target == null) return;

        // Get the velocity of the source and target objects
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

        // Calculate the relative velocity of the source and target objects
        float relativeVelocity = velocityA.sub(velocityB).len();

        // Check if the relative velocity is greater than the minimum velocity
        if (relativeVelocity >= MIN_VELOCITY) {
            // Calculate the damage based on the impact force and relative velocity
            int calculatedDamage = (int) (impactForce * DAMAGE_MULTIPLIER);

            // Apply the damage to the target object
            if (target instanceof Pig) {
                // Cast the target object to Pig
                Pig pig = (Pig) target;
                // Apply the damage to the pig
                pig.takeDamage(calculatedDamage);
            } else if (target instanceof Material) {
                // Cast the target object to Material
                Material material = (Material) target;
                // Apply the damage to the material
                material.takeDamage(calculatedDamage);
            }
        }
    }

}

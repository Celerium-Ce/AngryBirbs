import static org.junit.Assert.*;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.angrybirbs.entities.Pigs.Normal;
import io.github.angrybirbs.entities.Pigs.Pig;
import org.junit.Before;
import org.junit.Test;

public class PigTest extends HeadlessTest {

    // PigTest class
    // This class is responsible for testing the Pig class
    // It tests the initialization, toggling physics, setting dead, setting position, getting velocity, disposing of the pig, and taking damage
    // Extends HeadlessTest to allow for testing of libGDX classes without opengl context

    private World world;
    private Pig pig;

    @Before
    public void setUp() {

        // Set up the world and pig for testing

        // Create a new world with gravity and allow for sleeping bodies
        world = new World(new Vector2(0, -9.81f), true);

        // Create a new pixmap and texture for the pig
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        TextureRegion textureRegion = new TextureRegion(texture);
        StaticTiledMapTile tile = new StaticTiledMapTile(textureRegion);

        // Create a new pig with the world, tile, and position
        pig = new Normal(world, tile, 100f, 200f);
    }

    @Test
    public void testInitialization() {

        // Test the initialization of the pig

        assertEquals(new Vector2(100f, 200f), pig.getPosition());
        assertNotNull(pig.getBody());
    }

    @Test
    public void testTogglePhysics() {

        // Test toggling the physics of the pig

        pig.getBody().setType(BodyDef.BodyType.StaticBody);
        pig.togglephysics();
        assertEquals(BodyDef.BodyType.DynamicBody, pig.getBody().getType());
    }

    @Test
    public void testSetDead() {

        // Test setting the pig to dead

        pig.setDead();
        assertTrue(pig.isDead());
    }

    @Test
    public void testSetPosition() {

        // Test setting the position of the pig

        pig.setPosition(300f, 400f);
        assertEquals(new Vector2(300f, 400f), pig.getPosition());
    }

    @Test
    public void testGetVelocity() {

        // Test getting the velocity of the pig

        pig.getBody().setLinearVelocity(new Vector2(10f, 5f));
        Vector2 velocity = pig.getVelocity();
        assertEquals(new Vector2(10f, 5f), velocity);
    }

    @Test
    public void testDispose() {

        // Test disposing of the pig

        pig.dispose();
        assertNull(pig.getBody());
    }

    @Test
    public void testTakeDamage() {

        // Test taking damage by the pig

        float initialHealth = pig.getHealth();
        pig.takeDamage(10f);
        assertEquals(initialHealth - 10f, pig.getHealth(), 0.01f);
    }
}

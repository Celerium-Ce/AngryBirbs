import static org.junit.Assert.*;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.angrybirbs.entities.Bird;
import io.github.angrybirbs.entities.Red;
import org.junit.Before;
import org.junit.Test;
// necessary imports


public class BirdTest extends HeadlessTest {

    // BirdTest class
    // This class is responsible for testing the Bird class
    // It tests the initialization, toggling physics, setting dead, setting position, getting velocity, and disposing of the bird
    // Extends HeadlessTest to allow for testing of libGDX classes without opengl context

    private World world;
    private Bird bird;

    @Before
    public void setUp() {

        // Set up the world and bird for testing

        // Create a new world with gravity and allow for sleeping bodies
        world = new World(new Vector2(0, -9.81f), true);

        // Create a new pixmap and texture for the bird
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        TextureRegion textureRegion = new TextureRegion(texture);
        StaticTiledMapTile tile = new StaticTiledMapTile(textureRegion);

        // Create a new bird with the world, tile, and position
        bird = new Red(world, tile, 100f, 200f);
    }

    @Test
    public void testInitialization() {

        // Test the initialization of the bird

        assertEquals(new Vector2(100f, 200f), bird.getPosition());
        assertNotNull(bird.getBody());
    }

    @Test
    public void testTogglePhysics() {

        // Test toggling the physics of the bird

        bird.getBody().setType(BodyDef.BodyType.StaticBody);
        bird.togglephysics();
        assertEquals(BodyDef.BodyType.DynamicBody, bird.getBody().getType());
    }

    @Test
    public void testSetDead() {

        // Test setting the bird to dead

        bird.setDead();
        assertTrue(bird.isToBeRemoved());
    }

    @Test
    public void testSetPosition() {

        // Test setting the position of the bird

        bird.setPosition(300f, 400f);
        assertEquals(new Vector2(300f, 400f), bird.getPosition());
    }

    @Test
    public void testGetVelocity() {

        // Test getting the velocity of the bird

        bird.togglephysics();
        bird.getBody().setLinearVelocity(new Vector2(10f, 5f));
        Vector2 velocity = bird.getVelocity();
        assertEquals(new Vector2(10f, 5f), velocity);
    }

    @Test
    public void testDispose() {

        // Test disposing of the bird

        bird.dispose();
        assertNull(bird.getBody());
    }
}

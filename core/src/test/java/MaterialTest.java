import static org.junit.Assert.*;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.angrybirbs.entities.Materials.Material;
import io.github.angrybirbs.entities.Materials.Wood;
import org.junit.Before;
import org.junit.Test;

public class MaterialTest extends HeadlessTest {

    // MaterialTest class
    // This class is responsible for testing the Material class
    // It tests the initialization, setting position, getting velocity, disposing of the material, and taking damage
    // Extends HeadlessTest to allow for testing of libGDX classes without opengl context

    private World world;
    private Material material;

    @Before
    public void setUp() {

        // Set up the world and material for testing

        // Create a new world with gravity and allow for sleeping bodies
        world = new World(new Vector2(0, -9.81f), true);

        // Create a new pixmap and texture for the material
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        TextureRegion textureRegion = new TextureRegion(texture);
        StaticTiledMapTile tile = new StaticTiledMapTile(textureRegion);

        // Create a new material with the tile, position, and world
        material = new Wood(tile, 100f, 200f,world);
    }

    @Test
    public void testInitialization() {

        // Test the initialization of the material

        assertEquals(new Vector2(100f, 200f), material.getPosition());
        assertNotNull(material.getBody());
    }

    @Test
    public void testSetPosition() {

        // Test setting the position of the material

        material.setPosition(300f, 400f);
        assertEquals(new Vector2(300f, 400f), material.getPosition());
    }

    @Test
    public void testGetVelocity() {

        // Test getting the velocity of the material

        material.getBody().setLinearVelocity(new Vector2(10f, 5f));
        Vector2 velocity = material.getVelocity();
        assertEquals(new Vector2(10f, 5f), velocity);
    }

    @Test
    public void testDispose() {

        // Test disposing of the material

        material.dispose();
        assertNull(material.getBody());
    }

    @Test
    public void testTakeDamage() {

        // Test taking damage by the material

        float initialHealth = material.getHealth();
        material.takeDamage(10f);
        assertEquals(initialHealth - 10f, material.getHealth(), 0.01f);
    }
}

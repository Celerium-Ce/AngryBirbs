import static org.junit.Assert.*;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.angrybirbs.entities.Material;
import io.github.angrybirbs.entities.Wood;
import org.junit.Before;
import org.junit.Test;

public class MaterialTest extends HeadlessTest {

    private World world;
    private Material material;

    @Before
    public void setUp() {
        world = new World(new Vector2(0, -9.81f), true);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        TextureRegion textureRegion = new TextureRegion(texture);
        StaticTiledMapTile tile = new StaticTiledMapTile(textureRegion);

        //material = new Material(world, tile, 100f, 200f);
        material = new Wood(tile, 100f, 200f,world);
    }

    @Test
    public void testInitialization() {
        assertEquals(new Vector2(100f, 200f), material.getPosition());
        assertNotNull(material.getBody());
    }

    @Test
    public void testSetPosition() {
        material.setPosition(300f, 400f);
        assertEquals(new Vector2(300f, 400f), material.getPosition());
    }

    @Test
    public void testGetVelocity() {
        material.getBody().setLinearVelocity(new Vector2(10f, 5f));
        Vector2 velocity = material.getVelocity();
        assertEquals(new Vector2(10f, 5f), velocity);
    }

    @Test
    public void testDispose() {
        material.dispose(world);
        assertNull(material.getBody());
    }

    @Test
    public void testTakeDamage() {
        // Assuming the Material class has a method to get health
        float initialHealth = material.getHealth();
        material.takeDamage(10f);
        assertEquals(initialHealth - 10f, material.getHealth(), 0.01f);
    }
}

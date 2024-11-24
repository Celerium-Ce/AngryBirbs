import static org.junit.Assert.*;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.angrybirbs.entities.Normal;
import io.github.angrybirbs.entities.Pig;
import org.junit.Before;
import org.junit.Test;

public class PigTest extends HeadlessTest {

    private World world;
    private Pig pig;

    @Before
    public void setUp() {
        world = new World(new Vector2(0, -9.81f), true);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        TextureRegion textureRegion = new TextureRegion(texture);
        StaticTiledMapTile tile = new StaticTiledMapTile(textureRegion);

        pig = new Normal(world, tile, 100f, 200f);
    }

    @Test
    public void testInitialization() {
        assertEquals(new Vector2(100f, 200f), pig.getPosition());
        assertNotNull(pig.getBody());
    }

    @Test
    public void testTogglePhysics() {
        pig.getBody().setType(BodyDef.BodyType.StaticBody);
        pig.togglephysics();
        assertEquals(BodyDef.BodyType.DynamicBody, pig.getBody().getType());
    }

    @Test
    public void testSetDead() {
        pig.setDead();
        assertTrue(pig.isToBeRemoved());
    }

    @Test
    public void testSetPosition() {
        pig.setPosition(300f, 400f);
        assertEquals(new Vector2(300f, 400f), pig.getPosition());
    }

    @Test
    public void testGetVelocity() {
        pig.getBody().setLinearVelocity(new Vector2(10f, 5f));
        Vector2 velocity = pig.getVelocity();
        assertEquals(new Vector2(10f, 5f), velocity);
    }

    @Test
    public void testDispose() {
        pig.dispose(world);
        assertNull(pig.getBody());
    }

    @Test
    public void testTakeDamage() {
        float initialHealth = pig.getHealth();
        pig.takeDamage(10f);
        assertEquals(initialHealth - 10f, pig.getHealth(), 0.01f);
    }
}

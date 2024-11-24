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

public class BirdTest extends HeadlessTest {

    private World world;
    private Bird bird;

    @Before
    public void setUp() {
        world = new World(new Vector2(0, -9.81f), true);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        TextureRegion textureRegion = new TextureRegion(texture);
        StaticTiledMapTile tile = new StaticTiledMapTile(textureRegion);

        bird = new Red(world, tile, 100f, 200f);
    }

    @Test
    public void testInitialization() {
        assertEquals(new Vector2(100f, 200f), bird.getPosition());
        assertNotNull(bird.getBody());
    }

    @Test
    public void testTogglePhysics() {
        bird.getBody().setType(BodyDef.BodyType.StaticBody);
        bird.togglephysics();
        assertEquals(BodyDef.BodyType.DynamicBody, bird.getBody().getType());
    }

    @Test
    public void testSetDead() {
        bird.setDead();
        assertTrue(bird.isToBeRemoved());
    }

    @Test
    public void testSetPosition() {
        bird.setPosition(300f, 400f);
        assertEquals(new Vector2(300f, 400f), bird.getPosition());
    }

    @Test
    public void testGetVelocity() {
        bird.togglephysics();
        bird.getBody().setLinearVelocity(new Vector2(10f, 5f));
        Vector2 velocity = bird.getVelocity();
        assertEquals(new Vector2(10f, 5f), velocity);
    }

    @Test
    public void testDispose() {
        bird.dispose();
        assertNull(bird.getBody());
    }
}

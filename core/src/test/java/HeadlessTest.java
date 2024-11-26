import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.ApplicationListener;
import org.mockito.Mockito;
// necessary imports

public class HeadlessTest {

    // Sets up the headless application for testing purposes and mocks the OpenGL context so that the tests can run without errors due to OpenGL.

    static {

        // Setup the headless application
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();

        // Create the headless application
        new HeadlessApplication(new ApplicationListener() {

            // Empty methods to satisfy the interface
            @Override
            public void create() {}

            @Override
            public void resize(int width, int height) {}

            @Override
            public void render() {}

            @Override
            public void pause() {}

            @Override
            public void resume() {}

            @Override
            public void dispose() {}
        }, config);
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        // Mock the OpenGL context
        Gdx.gl = Mockito.mock(GL20.class);
        Gdx.gl20 = Gdx.gl;
    }
}

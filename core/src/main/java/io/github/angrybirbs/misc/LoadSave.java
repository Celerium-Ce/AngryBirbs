package io.github.angrybirbs.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import io.github.angrybirbs.Main;
import io.github.angrybirbs.entities.*;
import io.github.angrybirbs.levels.Level;

import java.io.*;
import java.util.*;

public class LoadSave {

    // LoadSave class that contains methods to save and load levels
    // This class is used to save and load levels of the game


    private static String getFileName(int saveNum){

        // Method to get the file name of the save file

        File levelDataDir = new File(Gdx.files.local("../SaveData").file().getAbsolutePath());
        if (!(levelDataDir.exists())) {
            levelDataDir = new File(Gdx.files.local("SaveData").file().getAbsolutePath());
        }
        if (saveNum == -1){
            String[] files = levelDataDir.list();
            saveNum = files != null ? files.length + 1 : 1;
        }
        return levelDataDir.getAbsolutePath() + "/" + saveNum + ".ser";
    }

    public static void saveLevel(Level level) {

        // Method to save the level

        // Serializes the current level state to a file.
        // Includes the slingshot, birds, pigs, and materials,
        // capturing their types, positions, rotations, and other attributes.


        String fileName = getFileName(-1);
        LevelState state = new LevelState();
        state.groundY = level.getGroundY();
        state.levelNum = level.levelNum;
        ObjectState slingshortState = new ObjectState();
        slingshortState.type = level.getSlingshot().getClass().getSimpleName();
        slingshortState.bodyX = level.getSlingshot().getOrigin().x;
        slingshortState.bodyY = level.getSlingshot().getOrigin().y;
        slingshortState.renderedX = level.getSlingshot().getPosition().x;
        slingshortState.renderedY = level.getSlingshot().getPosition().y;
        state.slingshot = slingshortState;

        for (Bird bird : level.getBirds()) {
            ObjectState birdState = new ObjectState();
            birdState.type = bird.getClass().getSimpleName();
            birdState.bodyX = bird.getBody().getPosition().x;
            birdState.bodyY = bird.getBody().getPosition().y;
            birdState.renderedX = bird.getPosition().x;
            birdState.renderedY = bird.getPosition().y;
            birdState.rotation = bird.getBody().getAngle();
            birdState.health = bird.getHealth();
            state.birds.add(birdState);
        }

        for (Pig pig : level.getPigs()) {
            ObjectState pigState = new ObjectState();
            pigState.type = pig.getClass().getSimpleName();
            pigState.bodyX = pig.getBody().getPosition().x;
            pigState.bodyY = pig.getBody().getPosition().y;
            pigState.renderedX = pig.getPosition().x;
            pigState.renderedY = pig.getPosition().y;
            pigState.rotation = pig.getBody().getAngle();
            pigState.health = pig.getHealth();
            state.pigs.add(pigState);
        }

        for (Material material : level.getMaterials()) {
            ObjectState materialState = new ObjectState();
            materialState.type = material.getClass().getSimpleName();
            materialState.bodyX = material.getBody().getPosition().x;
            materialState.bodyY = material.getBody().getPosition().y;
            materialState.renderedX = material.getPosition().x;
            materialState.renderedY = material.getPosition().y;
            materialState.rotation = material.getBody().getAngle();
            materialState.health = material.getHealth();
            materialState.isH = material.getWidth()>material.getHeight();
            state.materials.add(materialState);
        }

        // Writes the state to a file
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(state);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Level loadLevel(Main game, World world, int saveNum) {

        // Method to load a level
        // Deserializes the level state from a file and creates the level from the state


        // Loads a saved level by deserializing the level state from a file.
        // Recreates the level by loading the slingshot, birds, pigs, and materials
        // from their saved states, including their positions, types, health, and rotations.
        // Also loads the associated tiled map for the level and restores the layout
        // using tile types and properties. Returns a reconstructed Level object.


        LevelState state = null;
        String fileName = getFileName(saveNum);

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            state = (LevelState) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (state == null) {
            return null;
        }



        File levelDataDir = new File(Gdx.files.local("../Levels").file().getAbsolutePath());

        if (!levelDataDir.exists()) {
            levelDataDir = new File(Gdx.files.local("Levels").file().getAbsolutePath());
        }

        String levelName = levelDataDir.getAbsolutePath() + "/" + state.levelNum + ".tmx";
        TiledMap tiledMap = new TmxMapLoader().load(levelName);
        Map<String, TiledMapTile> tiles = new HashMap<>();
        MapLayer layer = tiledMap.getLayers().get("Objects");
        for (MapObject obj : layer.getObjects()) {
            if (obj instanceof TiledMapTileMapObject) {
                TiledMapTileMapObject tileObject = (TiledMapTileMapObject) obj;

                TiledMapTile tile = tileObject.getTile();
                String entityType = (String) tileObject.getProperties().get("type");
                tiles.put(entityType,tile);
            }
        }


        ObjectState slingshortState = state.slingshot;
        Vector2 origin = new Vector2(slingshortState.bodyX, slingshortState.bodyY);
        Slingshot slingshot = new Slingshot(origin, tiles.get(slingshortState.type), slingshortState.renderedX, slingshortState.renderedY);

        List<Bird> birds = new ArrayList<>();
        List<Pig> pigs = new ArrayList<>();
        List<Material> materials = new ArrayList<>();

        for (ObjectState objectState : state.birds) {
            if ("Blue".equals(objectState.type)) {
                Bird bird = new Blue(world, tiles.get(objectState.type), objectState.bodyX, objectState.bodyY);
                bird.getBody().setTransform(objectState.bodyX, objectState.bodyY, objectState.rotation);
                bird.setPosition(objectState.renderedX, objectState.renderedY);
                bird.setHealth(objectState.health);
                birds.add(bird);
                System.out.println("Loaded Blue Bird at: (" + objectState.bodyX + ", " + objectState.bodyY + ")");
            } else if ("Red".equals(objectState.type)) {
                Bird bird = new Red(world, tiles.get(objectState.type), objectState.bodyX, objectState.bodyY);
                bird.getBody().setTransform(objectState.bodyX, objectState.bodyY, objectState.rotation);
                bird.setPosition(objectState.renderedX, objectState.renderedY);
                bird.setHealth(objectState.health);
                birds.add(bird);
                System.out.println("Loaded Red Bird at: (" + objectState.bodyX + ", " + objectState.bodyY + ")");
            } else if ("Yellow".equals(objectState.type)) {
                Bird bird = new Yellow(world, tiles.get(objectState.type), objectState.bodyX, objectState.bodyY);
                bird.getBody().setTransform(objectState.bodyX, objectState.bodyY, objectState.rotation);
                bird.setPosition(objectState.renderedX, objectState.renderedY);
                bird.setHealth(objectState.health);
                birds.add(bird);
                System.out.println("Loaded Yellow Bird at: (" + objectState.bodyX + ", " + objectState.bodyY + ")");
            }
        }

        for (ObjectState objectState : state.pigs) {
            if ("Normal".equals(objectState.type)) {
                Pig pig = new Normal(world, tiles.get(objectState.type), objectState.bodyX, objectState.bodyY);
                pig.getBody().setTransform(objectState.bodyX, objectState.bodyY, objectState.rotation);
                pig.setPosition(objectState.renderedX, objectState.renderedY);
                pig.setHealth(objectState.health);
                pigs.add(pig);
                System.out.println("Loaded Normal Pig at: (" + objectState.bodyX + ", " + objectState.bodyY + ")");
            } else if ("King".equals(objectState.type)) {
                Pig pig = new King(world, tiles.get(objectState.type), objectState.bodyX, objectState.bodyY);
                pig.getBody().setTransform(objectState.bodyX, objectState.bodyY, objectState.rotation);
                pig.setPosition(objectState.renderedX, objectState.renderedY);
                pig.setHealth(objectState.health);
                pigs.add(pig);
                System.out.println("Loaded King Pig at: (" + objectState.bodyX + ", " + objectState.bodyY + ")");
            } else if ("General".equals(objectState.type)) {
                Pig pig = new General(world, tiles.get(objectState.type), objectState.bodyX, objectState.bodyY);
                pig.getBody().setTransform(objectState.bodyX, objectState.bodyY, objectState.rotation);
                pig.setPosition(objectState.renderedX, objectState.renderedY);
                pig.setHealth(objectState.health);
                pigs.add(pig);
                System.out.println("Loaded King Pig at: (" + objectState.bodyX + ", " + objectState.bodyY + ")");
            }
        }

        for (ObjectState objectState : state.materials) {
            if ("Wood".equals(objectState.type)) {
                Material material = new Wood(objectState.isH ? tiles.get("WoodH") : tiles.get("WoodV"), objectState.bodyX, objectState.bodyY, world);
                material.getBody().setTransform(objectState.bodyX, objectState.bodyY, objectState.rotation);
                material.setPosition(objectState.renderedX, objectState.renderedY);
                materials.add(material);
                System.out.println("Loaded Wood Material at: (" + objectState.bodyX + ", " + objectState.bodyY + ")");
            } else if ("Ice".equals(objectState.type)) {
                Material material = new Ice(objectState.isH ? tiles.get("IceH") : tiles.get("IceV"), objectState.bodyX, objectState.bodyY, world);
                material.getBody().setTransform(objectState.bodyX, objectState.bodyY, objectState.rotation);
                material.setPosition(objectState.renderedX, objectState.renderedY);
                materials.add(material);
                System.out.println("Loaded Ice Material at: (" + objectState.bodyX + ", " + objectState.bodyY + ")");
            } else if ("Steel".equals(objectState.type)) {
                Material material = new Steel(objectState.isH ? tiles.get("SteelH") : tiles.get("SteelV"), objectState.bodyX, objectState.bodyY, world);
                material.getBody().setTransform(objectState.bodyX, objectState.bodyY, objectState.rotation);
                material.setPosition(objectState.renderedX, objectState.renderedY);
                materials.add(material);
                System.out.println("Loaded Steel Material at: (" + objectState.bodyX + ", " + objectState.bodyY + ")");
            }
        }

        return new Level(game, world, slingshot, birds, pigs, materials, state.levelNum, state.groundY);
    }


    public static class LevelState implements Serializable {

        // LevelState class that contains the state of the level

        public List<ObjectState> birds = new ArrayList<>();
        public List<ObjectState> pigs = new ArrayList<>();
        public List<ObjectState> materials = new ArrayList<>();
        public ObjectState slingshot;
        public float groundY;
        public int levelNum;
    }

    public static class ObjectState implements Serializable {

        // ObjectState class that contains the state of an object

        public String type;
        public float bodyX;
        public float bodyY;
        public float renderedX;
        public float renderedY;
        public float rotation;
        public float health;
        public boolean isH;
    }
}

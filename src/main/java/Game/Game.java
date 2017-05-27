package Game;


import Engine.Graphics.Camera;
import Engine.Graphics.OBJLoader;
import Engine.Graphics.Texture;
import Engine.Item;
import Engine.Graphics.Mesh;
import Engine.MouseInput;
import Engine.Window;
import org.joml.Vector2f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Game implements GameLogic {

    private static final float MOUSE_SENSITIVITY = 0.2f;

    private static final float CAMERA_POS_STEP = 0.05f;

    private final Vector3f cameraInc;

    private final Renderer renderer;

    private final Camera camera;

    private Item[] items;

    public Game() {
        renderer = new Renderer();
        camera = new Camera();
        cameraInc = new Vector3f(0, 0, 0);
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);
        Mesh mesh = OBJLoader.loadMesh("src/main/java/Resources/Models/bunny.obj");
//        Texture texture = new Texture("src/main/java/Resources/Textures/brick.png");
//        mesh.setTexture(texture);
        Item item = new Item(mesh);
        item.setScale(0.5f);
        item.setPosition(0, 0, -2);
        items = new Item[]{item};

//        int itemNumber = 10;
//        items = new Item[itemNumber];
//        for (int i = 0; i < itemNumber; i++) {
//            items[i] = new Item(mesh);
//            items[i].setScale((float) Math.random() * 2);
//            items[i].setPosition(
//                    (float) Math.random() * 3,
//                    (float) Math.random() * 3,
//                    (float) Math.random() * 3);
//
//        }
    }

    @Override
    public void input(Window window, MouseInput mouseInput) {
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -1;
        } else if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -1;
        } else if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = 1;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            cameraInc.y = -1;
        } else if (window.isKeyPressed(GLFW_KEY_X)) {
            cameraInc.y = 1;
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        // Update camera position
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP, cameraInc.z * CAMERA_POS_STEP);

        // Update camera based on mouse
        if (mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }
    }

    @Override
    public void render(Window window) {
        renderer.render(window, camera, items);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for (Item gameItem : items) {
            gameItem.getMesh().cleanUp();
        }
    }
}

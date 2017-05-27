package Game;

import Engine.Graphics.Camera;
import Engine.Item;
import Engine.Graphics.Shader;
import Engine.Util.File;
import Engine.Graphics.Transformation;
import Engine.Window;
import org.joml.Matrix4f;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    private final Transformation transformation;

    private int vboId;

    private int vaoId;

    private Shader shader;

    public Renderer() {
        transformation = new Transformation();
    }

    public void init(Window window) throws Exception {
        // Create shader
        shader = new Shader();
        shader.createVertexShader(File.loadResource("src/main/java/Shaders/Shader.vert"));
        shader.createFragmentShader(File.loadResource("src/main/java/Shaders/Shader.frag"));
        shader.link();

        // Create uniforms for world and projection matrices
        shader.createUniform("projectionMatrix");
        shader.createUniform("modelViewMatrix");
        shader.createUniform("texture_sampler");

        window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void render(Window window, Camera camera, Item[] items) {
        clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shader.bind();
        // Update projection Matrix
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shader.setUniform("projectionMatrix", projectionMatrix);

        // Update view Matrix
        Matrix4f viewMatrix = transformation.getViewMatrix(camera);

        shader.setUniform("texture_sampler", 0);

        // Render each gameItem
        for(Item gameItem : items) {
            // Set world matrix for this item
            Matrix4f modelViewMatrix = transformation.getModelViewMatrix(gameItem, viewMatrix);
            shader.setUniform("modelViewMatrix", modelViewMatrix);
            // Render the mes for this game item
            gameItem.getMesh().render();
        }

        shader.unbind();
    }

    public void cleanup() {
        if (shader != null) {
            shader.cleanup();
        }
    }


    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}

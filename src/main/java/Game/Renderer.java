package Game;

import Engine.Item;
import Engine.Util.Mesh;
import Engine.Util.ShaderUtil;
import Engine.Util.FileUtil;
import Engine.Util.Transformation;
import Engine.Window;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

public class Renderer {

    private static final float FOV = (float) Math.toRadians(60.0f);

    private static final float Z_NEAR = 0.01f;

    private static final float Z_FAR = 1000.f;

    private final Transformation transformation;

    private int vboId;

    private int vaoId;

    private ShaderUtil shaderUtil;

    public Renderer() {
        transformation = new Transformation();
    }

    public void init(Window window) throws Exception {
        // Create shader
        shaderUtil = new ShaderUtil();
        shaderUtil.createVertexShader(FileUtil.loadResource("src/main/java/Shaders/Shader.vert"));
        shaderUtil.createFragmentShader(FileUtil.loadResource("src/main/java/Shaders/Shader.frag"));
        shaderUtil.link();

        // Create uniforms for world and projection matrices
        shaderUtil.createUniform("projectionMatrix");
        shaderUtil.createUniform("worldMatrix");

        window.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public void render(Window window, Item[] items) {
        clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shaderUtil.bind();
        // Update projection Matrix
        Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(), Z_NEAR, Z_FAR);
        shaderUtil.setUniform("projectionMatrix", projectionMatrix);

        // Render each gameItem
        for(Item gameItem : items) {
            // Set world matrix for this item
            Matrix4f worldMatrix = transformation.getWorldMatrix(
                    gameItem.getPosition(),
                    gameItem.getRotation(),
                    gameItem.getScale());
            shaderUtil.setUniform("worldMatrix", worldMatrix);
            // Render the mes for this game item
            gameItem.getMesh().render();
        }

        shaderUtil.unbind();
    }

    public void cleanup() {
        if (shaderUtil != null) {
            shaderUtil.cleanup();
        }
    }


    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }
}

package Game;

import Engine.Util.Mesh;
import Engine.Util.ShaderUtil;
import Engine.Util.FileUtil;
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

    private Matrix4f projectionMatrix;

    private int vboId;

    private int vaoId;

    private ShaderUtil shaderUtil;

    public Renderer() {
    }

    public void init(Window window) throws Exception {
        // Create shader
        shaderUtil = new ShaderUtil();
        shaderUtil.createVertexShader(FileUtil.loadResource("src/main/java/Shaders/Shader.vert"));
        shaderUtil.createFragmentShader(FileUtil.loadResource("src/main/java/Shaders/Shader.frag"));
        shaderUtil.link();

        // Create projection matrix
        float aspectRatio = (float) window.getWidth() / window.getHeight();
        projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
        shaderUtil.createUniform("projectionMatrix");
    }

    public void render(Window window, Mesh mesh) {
        clear();

        if (window.isResized()) {
            glViewport(0, 0, window.getWidth(), window.getHeight());
            window.setResized(false);
        }

        shaderUtil.bind();
        shaderUtil.setUniform("projectionMatrix", projectionMatrix);

        // Draw the mesh
        glBindVertexArray(mesh.getVaoId());
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawElements(GL_TRIANGLES, mesh.getVertexCount(), GL_UNSIGNED_INT, 0);

        // Restore state
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glBindVertexArray(0);

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

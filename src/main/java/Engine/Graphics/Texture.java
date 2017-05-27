package Engine.Graphics;


import Engine.Util.Buffer;
import Engine.Util.Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

public class Texture {
    private final int id;

    public Texture(String path) throws IOException {
        id = load(path);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public int getId() {
        return id;
    }

    private int load(String path) throws IOException {
        Decoder decoder = new Decoder(path);
        int textureID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureID);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, decoder.getWidth(), decoder.getHeight(),
                0, GL_RGBA, GL_UNSIGNED_BYTE, Buffer.create(decoder.getData()));
        glGenerateMipmap(GL_TEXTURE_2D);
        return textureID;
    }

    public void cleanup() {
        glDeleteTextures(id);
    }
}

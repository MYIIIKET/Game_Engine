package Engine.Util;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Buffer {

    private Buffer() {
    }

    public static ByteBuffer create(byte[] array) {
        ByteBuffer result = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
        result.put(array).flip();
        return result;
    }

    public static FloatBuffer create(float[] array) {
        FloatBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        result.put(array).flip();
        return result;
    }

    public static IntBuffer create(int[] array) {
        IntBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
        result.put(array).flip();
        return result;
    }
}

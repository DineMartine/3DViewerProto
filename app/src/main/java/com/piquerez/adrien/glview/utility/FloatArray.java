package com.piquerez.adrien.glview.utility;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Adrien on 01/05/2015.
 */
public class FloatArray
{
    private final static int BYTES_PER_FLOAT = 4;

    public static FloatBuffer toFloatBuffer(float[] array)
    {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                array.length * BYTES_PER_FLOAT);
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        FloatBuffer vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(array);
        vertexBuffer.position(0);
        return vertexBuffer;
    }
}

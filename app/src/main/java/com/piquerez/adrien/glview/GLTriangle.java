package com.piquerez.adrien.glview;

import com.piquerez.adrien.glview.utility.FloatArray;
import com.piquerez.adrien.model.geometry._3d.Triangle;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Adrien on 25/04/2015.
 */
public class GLTriangle extends GLPrimitive
{
    private static final int VERTEX_COUNT = 3;

    private FloatBuffer vertexBuffer;

    public GLTriangle(Triangle triangle)
    {
        vertexBuffer = FloatArray.toFloatBuffer(triangle.ToFloatArray());
    }

    @Override
    public FloatBuffer vertexBuffer() {
        return vertexBuffer;
    }

    @Override
    public int vertexCount() {
        return VERTEX_COUNT;
    }
}

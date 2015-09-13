package com.piquerez.adrien.glview;

import java.nio.FloatBuffer;

/**
 * Created by Adrien on 25/04/2015.
 */
public abstract class GLPrimitive
{
    public static final int COORDS_PER_VERTEX = 3;
    public static final int BYTES_PER_COORD = 4;

    public abstract FloatBuffer vertexBuffer();
    public abstract int vertexCount();
}

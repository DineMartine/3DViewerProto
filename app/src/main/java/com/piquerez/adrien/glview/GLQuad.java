package com.piquerez.adrien.glview;

import com.piquerez.adrien.glview.utility.FloatArray;
import com.piquerez.adrien.model.geometry._3d.Point;
import com.piquerez.adrien.model.geometry._3d.Quadrilateral;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Adrien on 30/04/2015.
 */
public class GLQuad extends GLPrimitive
{
    private static final int[] DRAW_ORDER = {0, 1, 2, 0, 2, 3};
    public static final int VERTEX_COUNT = 6;

    private FloatBuffer vertexBuffer;

    public GLQuad(Point a, Point b, Point c, Point d)
    {
        this(new Quadrilateral(a, b, c, d));
    }

    public GLQuad(Quadrilateral quad)
    {
        Point[] corners = quad.Corners();
        Point[] vertices = new Point[VERTEX_COUNT];
        int i = 0;
        for(int j : DRAW_ORDER)
        {
            vertices[i] = corners[j];
            i++;
        }
        vertexBuffer = FloatArray.toFloatBuffer(
                Point.ToFloatArray(vertices));
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

package com.piquerez.adrien.glview;

import com.piquerez.adrien.model.geometry._3d.Point;
import com.piquerez.adrien.model.geometry._3d.Quadrilateral;

import java.nio.FloatBuffer;

/**
 * Created by Adrien on 14/07/2015.
 */
public class GLCube extends GLPrimitive
{
    private static final int VERTEX_COUNT = 6; //6 * GLQuad.VERTEX_COUNT;
    private FloatBuffer vertexBuffer;

    public GLCube(Point a, Point b, Point c, Point d, Point e, Point f, Point g, Point h)
    {
        GLQuad[] quads = new GLQuad[6];
        quads[0] = new GLQuad(new Quadrilateral(a, b, c, d));
        quads[1] = new GLQuad(new Quadrilateral(a, e, f, b));
        quads[2] = new GLQuad(new Quadrilateral(b, f, g, c));
        quads[3] = new GLQuad(new Quadrilateral(c, g, h, d));
        quads[4] = new GLQuad(new Quadrilateral(d, h, e, a));
        quads[5] = new GLQuad(new Quadrilateral(e, f, g, h));

        FloatBuffer quadBuffer = quads[0].vertexBuffer();
        int quadBufferCapacity = quadBuffer.capacity();
        vertexBuffer = FloatBuffer.allocate(quadBufferCapacity*quads.length);
        for(GLQuad quad : quads)
        {
            quadBuffer = quad.vertexBuffer();
            vertexBuffer.put(quadBuffer);
        }
        vertexBuffer.position(0);
    }

    @Override
    public FloatBuffer vertexBuffer()
    {
        return vertexBuffer;
    }

    @Override
    public int vertexCount()
    {
        return VERTEX_COUNT;
    }
}

package com.piquerez.adrien.model.geometry._3d;

/**
 * Created by Adrien on 25/04/2015.
 */
public class Point {

    private final float[] coords;

    public Point(float x, float y, float z)
    {
        coords = new float[]{x, y, z};
    }

    public Point(float[] coordinates)
    {
        if(coordinates.length != 3)
            throw new IllegalArgumentException();

        this.coords = coordinates;
    }

    public float X()
    {
        return coords[0];
    }

    public float Y()
    {
        return coords[1];
    }

    public float Z()
    {
        return coords[2];
    }

    public float[] Coordinates()
    {
        return coords;
    }

    public static float[] ToFloatArray(Point[] points)
    {
        float[] result = new float[3 * points.length];
        int pos = 0;
        for(Point p : points)
        {
            System.arraycopy(p.coords, 0, result, pos, 3);
            pos += 3;
        }
        return result;
    }
}

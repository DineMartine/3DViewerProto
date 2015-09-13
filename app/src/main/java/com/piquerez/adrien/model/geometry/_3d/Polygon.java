package com.piquerez.adrien.model.geometry._3d;

/**
 * Created by Adrien on 25/04/2015.
 */
public class Polygon {
    private Point[] corners;

    protected Polygon(Point[] corners)
    {
        this.corners = corners;
    }

    // TODO Verify is immutable : cant do corners[i] = new Point()
    public Point[] Corners()
    {
        return corners;
    }

    public float[] ToFloatArray()
    {
        return Point.ToFloatArray(corners);
    }
}

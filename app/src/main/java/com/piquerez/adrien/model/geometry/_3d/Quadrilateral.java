package com.piquerez.adrien.model.geometry._3d;

/**
 * Created by Adrien on 30/04/2015.
 */
public class Quadrilateral extends Polygon
{
    public Quadrilateral(Point a, Point b, Point c, Point d)
    {
        super(new Point[]{a, b, c, d});
    }
}

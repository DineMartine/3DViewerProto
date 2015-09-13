package com.piquerez.adrien.model.geometry._3d;

/**
 * Created by Adrien on 25/04/2015.
 */
public class Triangle extends Polygon
{
    public Triangle(Point a, Point b, Point c)
    {
        super(new Point[]{a,b,c});
    }
}

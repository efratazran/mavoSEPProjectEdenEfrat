package geometries;

import primitives.Point3D;

public class Triangle extends Polygon {

   // constructor that gets 3 points of triangle
   public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }

    @Override
    public String toString() {
        return "points of Triangle:" + vertices +'\n' +"Plane"+ plane;
    }
}


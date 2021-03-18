package geometries;

import primitives.Point3D;
/**
 * this is the class of a triangle.
 */
public class Triangle extends Polygon {

    /*** constructor ***/


    /**
     * ctor that gets 3 points of triangle and goes to implement his father
     * @param p1 point3D value
     * @param p2 point3D value
     * @param p3 point3D value
     */
   public Triangle(Point3D p1, Point3D p2, Point3D p3) {
        super(p1, p2, p3);
    }

    @Override
    public String toString() {
        return "points of Triangle:" + vertices +'\n' +"Plane"+ plane;
    }
}


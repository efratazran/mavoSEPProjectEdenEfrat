package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Sphere implements Geometry{

    /*** field ***/
    Point3D center;
    double radius;

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
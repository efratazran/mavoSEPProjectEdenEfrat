package geometries;

import primitives.Point3D;
import primitives.Vector;
import primitives.Ray;

public class Tube implements Geometry {

    /*** field ***/
    double radius;
    Ray axisRay;

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}

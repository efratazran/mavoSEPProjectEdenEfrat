package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Cylinder extends Tube implements Geometry  {

    /*** field ***/
    double height;

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * this is the class for a cylinder shape.
 */
public class Cylinder extends Tube implements Geometry {

    /*** field ***/
    double height;

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}

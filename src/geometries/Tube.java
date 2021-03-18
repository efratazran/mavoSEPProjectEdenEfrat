package geometries;

import primitives.Point3D;
import primitives.Vector;
import primitives.Ray;

/**
 * this is a tube class
 */
public class Tube implements Geometry {

    /*** field ***/
    double radius;
    Ray axisRay;

    /********** getter **********/

    public double getRadius() {
        return radius;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "radius=" + radius +
                ", axisRay=" + axisRay +
                '}';
    }
}

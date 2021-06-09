package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * this is the class for a cylinder shape.
 */
public class Cylinder extends Tube  {

    /***
     * field
     * ***/
    double _height;

    public Cylinder(double _radius, Ray _axisRay, double _height) {
        super(_radius, _axisRay);
        this._height = _height;
    }

    /***
     *
     * @param point
     * @return vector normal to the point in cylinder
     */
    @Override
    public Vector getNormal(Point3D point) {
        Vector v = _axisRay.getDir();
        Point3D o = _axisRay.getP0();

        double val;
        try {
            val = alignZero(point.subtract(o).dotProduct(v));
        } catch (IllegalArgumentException e) { //case that if point=0 the start point of ray
            return v;
        }
        // case that point exist in two base of cylinder
        if (val == 0 || isZero(_height - val)) {
            return v;
        }
        //another point
        o = o.add(v.scale(val));
        return point.subtract(o).normalize();
    }

}

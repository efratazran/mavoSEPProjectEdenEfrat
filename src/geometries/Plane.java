package geometries;

import primitives.Point3D;
import primitives.Vector;
/**
 * this is a plane class
 */
public class Plane implements Geometry {

    /*** field ***/
    Point3D _q0;
    Vector _normal;

    /*** constructors ***/
    /**
     * constructor that get 3 points of plane
     * @param p1 point3D value
     * @param p2 point3D value
     * @param p3 point3D value
     */
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0=p1;
        _normal=null;
    }

    /**
     * constructor that get point and normal
     * @param q0 point3D value
     * @param normal Vector value
     */
    public Plane(Point3D q0,Vector normal) {
        this._q0=q0;
        this._normal=normal;
    }

    /**
     * gets the normal of the plane
     * @param point point3D value
     * @return vector vector value
     */
    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}

package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane implements Geometry {

    /*** field ***/
    Point3D _q0;
    Vector _normal;

    //constructor that get 3 points of plane
    public Plane(Point3D p1, Point3D p2, Point3D p3) {
        _q0=p1;
        _normal=null;
    }
    //constructor that get point and normal
    public Plane(Point3D q0,Vector normal) {
        this._q0=q0;
        this._normal=normal;
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}

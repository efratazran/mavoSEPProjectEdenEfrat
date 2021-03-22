package geometries;

import primitives.Point3D;
import primitives.Vector;
import primitives.Ray;

import java.io.PipedOutputStream;
import java.util.List;

import static primitives.Util.isZero;

/**
 * this is a tube class
 */
public class Tube implements Geometry {

    /*** field ***/
    double _radius;
    Ray _axisRay;

    /********** getter **********/

    public double getRadius() {
        return _radius;
    }

    public Ray getAxisRay() {
        return _axisRay;
    }

    /***
     *
     * @param p - Point in tube
     * @return vector normal to the point
     */
    @Override
    public Vector getNormal(Point3D p) {
       Point3D P0= _axisRay.getP0();
       Vector v= _axisRay.getDir();
       Vector P0_P= p.subtract(P0);

       double t=v.dotProduct(P0_P);

        //if vector v and vector p0_p is vertical
        if(isZero(t)){
            return  P0_P.normalize();
        }

        Point3D O=P0.add(v.scale(t));
        //if p is point of the ray
        if (O.equals(p)){
            throw new IllegalArgumentException("point a cannot be on the tube's axis");
        }
        Vector n=p.subtract(O);
        return  n.normalize();

    }

    @Override
    public String toString() {
        return "Tube{" +
                "radius=" + _radius +
                ", axisRay=" + _axisRay +
                '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}

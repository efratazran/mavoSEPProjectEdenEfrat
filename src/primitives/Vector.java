package primitives;
import static primitives.Point3D.ZERO;

public class Vector {
    Point3D _head;

    public Point3D get_head() {
        return _head;
    }

    //constructors
    public Vector(Point3D head) {
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("Vector had cannot be Point(0,0,0)");
        }
        this._head = _head;
    }

    public Vector(double x, double y, double z) {
        this(new Point3D(x, y, z));
    }

    public Vector crossProduct(Vector v) {
        double u1 = _head._x._coord;
        double u2 = _head._y._coord;
        double u3 = _head._z._coord;

        double v1 = v._head._x._coord;
        double v2 = v._head._y._coord;
        double v3 = v._head._z._coord;

        return new Vector(
                u2 * v3 - u3 * v2,
                u3 * v1 - u1 * v3,
                u1 * v2 - u2 * v1
        );
    }
   //???
    public int dotProduct(Vector n) {
        return 0;
    }
}



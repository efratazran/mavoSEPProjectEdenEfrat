package primitives;

public class Point3D {
    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;
    public final static Point3D ZERO=new Point3D(0,0,0);

    //constructors
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        _x = x;
        _y = y;
        _z = z;
    }
    public Point3D(double x, double y, double z) {
        this(new Coordinate(x),new Coordinate(y),new Coordinate(z));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return _x.equals(point3D._x) && _y.equals(point3D._y) && _z.equals(point3D._z);
    }

    @Override
    public String toString() {
        return "("+_x +","+_y +","+_z +")";
    }

    public Vector subtract(Point3D p) {
        Point3D head=new Point3D(
                _x._coord -p._x._coord,
                _y._coord -p._y._coord,
                _z._coord -p._z._coord
        );
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("Vector had cannot be Point(0,0,0)");
        }
        return new Vector(head);
    }
    public Point3D add(Vector vec){
        Point3D point = new Point3D(_x.add(vec.get_head()._x), _y.add( vec.get_head()._y), _z.add( vec.get_head()._z));
        return point;
    }

    public double distanceSquared(Point3D point){
       return ((point._x._coord- _x._coord)*(point._x._coord- _x._coord)
               +(point._y._coord- _y._coord)*(point._y._coord- _y._coord)+
                (point._z._coord- _z._coord)*(point._z._coord- _z._coord));

    }
    public double distance(Point3D point){
        return Math.sqrt(distanceSquared(point));
    }




}

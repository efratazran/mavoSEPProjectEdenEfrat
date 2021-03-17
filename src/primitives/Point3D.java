package primitives;

public class Point3D {

    /*** field ***/
    final Coordinate _x;
    final Coordinate _y;
    final Coordinate _z;
    public final static Point3D ZERO=new Point3D(0,0,0);

    //constructor that get 3 Coordinate
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        _x = x;
        _y = y;
        _z = z;
    }
    //constructor that get 3 numbers
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

    //return vector of sub between 2 points
    public Vector subtract(Point3D p) {
        Point3D head=new Point3D(
                _x.sub(p._x),
                _y.sub(p._y),
                _z.sub(p._z)
        );
        if (ZERO.equals(head)) {
            throw new IllegalArgumentException("Vector had cannot be Point(0,0,0)");
        }
        return new Vector(head);
    }
    //return point of add of vector and point
    public Point3D add(Vector vec){
        Point3D point = new Point3D(_x.add(vec.get_head()._x), _y.add( vec.get_head()._y), _z.add( vec.get_head()._z));
        return point;
    }
    //return distance between 2 points in pow 2
    public double distanceSquared(Point3D point){
       return ((point._x._coord- _x._coord)*(point._x._coord- _x._coord)
               +(point._y._coord- _y._coord)*(point._y._coord- _y._coord)+
                (point._z._coord- _z._coord)*(point._z._coord- _z._coord));

    }
    //return distance between 2 points
    public double distance(Point3D point){
        return Math.sqrt(distanceSquared(point));
    }




}

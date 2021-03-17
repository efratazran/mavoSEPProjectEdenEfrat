package primitives;

public class Ray {

    /*** field ***/
    Point3D p0;
    Vector dir;

    //constructor that get point and vector
    public Ray(Point3D p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalized();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    @Override
    public String toString() {
        return "p0: "+ p0.toString() + ", dir=" + dir.toString();
    }
}

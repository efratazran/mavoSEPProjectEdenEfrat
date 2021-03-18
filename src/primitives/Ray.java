package primitives;
/**
 * a ray class with a starting point
 */
public class Ray {

    /*** field ***/
    private final Point3D p0;
    private final Vector dir;
    /*** constructor ***/


    /**
     * constructor that gets a point and a vector
     * @param p0 point3D value
     * @param dir vector value
     */
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

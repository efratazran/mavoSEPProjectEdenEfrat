package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    /**
     * Test method for {@link.geometries.Cylinder#v}.
     */
    @Test
    void testGetNormal() {
        Point3D p0center = new Point3D(0, 0, 0);
        Vector dir = new Vector(0, 0, 1);
        Ray r = new Ray(p0center, dir);
        Cylinder cylinder = new Cylinder(25, r , 10);
        //============ Equivalence Partitions Tests ==============
        //point in tube
        Point3D p = new Point3D(25, 0, 1);
        double t = p.subtract(p0center).dotProduct(dir);

        Point3D o = null;
        if (!Util.isZero(t))
            o = p0center.add(dir.scale(t));

        Vector n = (p.subtract(o)).normalize();
        Vector exRes = new Vector(1, 0, 0);

        assertEquals(n, exRes, "ERROR, Tube's GetNormal() - wrong result ");

        // =============== Boundary Values Tests ==================
        //point in base 1 of the cylinder

        Point3D p2 = new Point3D(25, 0, 0);
        double t2 = p2.subtract(p0center).dotProduct(dir);

        if (Util.isZero(t2))
        {
            assertEquals(cylinder.getNormal(p2) ,dir , "ERROR, Tube's GetNormal() - wrong result " );
        }
        else throw new IllegalArgumentException("ERROR, Tube's GetNormal() - wrong result ");

        //point in base 2 of the cylinder

        Point3D p3 = new Point3D(25, 0, 10);
        double t3 = p3.subtract(p0center).dotProduct(dir);

        if (Util.isZero(t3- cylinder._height))
        {
            assertEquals(cylinder.getNormal(p3) ,dir, "ERROR, Tube's GetNormal() - wrong result " );

        }
        else throw new IllegalArgumentException("ERROR, Tube's GetNormal() - wrong result ");

    }
}
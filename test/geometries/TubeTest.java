package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    /**
     * Test method for {@link.geometries.Tube#getNormal(geometries.Tube)}.
     */
    @Test
    void testGetNormal() {
        Point3D p0center = new Point3D(0, 0, 0);
        Vector dir = new Vector(0, 0, 1);
        Ray r = new Ray(p0center, dir);
        Tube tube = new Tube(25, r);
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

        Point3D p2 = new Point3D(25, 0, 0);
        double t2 = p2.subtract(p0center).dotProduct(dir);

        if (Util.isZero(t2))
        {
            Vector v=new Vector(1,0,0);
            assertEquals(tube.getNormal(p2) ,v , "ERROR, Tube's GetNormal() - wrong result " );

        }
        else throw new IllegalArgumentException("ERROR, Tube's GetNormal() - wrong result ");


    }
}
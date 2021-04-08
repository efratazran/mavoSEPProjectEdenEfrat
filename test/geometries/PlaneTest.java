package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    /**
     * Test method for {@link.geometries.Plane#getNormal(geometries.Plane)}.
     */
    @Test
    void testGetNormal() {

        Plane plane = new Plane(new Point3D(1,2,3),new Point3D(1,1,1), new Point3D(2,2,2));
        // ============ Equivalence Partitions Tests ==============
        assertEquals(plane.getNormal(new Point3D(1,2,3)),new Vector(1,-2,1).normalize());

    }

    @Test
    public void testConstructor() {
        // ============ Boundary Values Tests==============
        // 3 points
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point3D(1,1,1),new Point3D(2,2,2),new Point3D(3,3,3))
        , "the constructor not throw exception");

        //mitlacdot
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point3D(1,1,1),new Point3D(1,1,1),new Point3D(1,2,3))
                , "the constructor not throw exception");


    }
}
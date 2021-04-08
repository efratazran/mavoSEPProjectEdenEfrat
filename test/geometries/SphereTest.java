package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point3D)} 
     */
    @Test
    void testGetNormal() {

        Point3D p0 = new Point3D(1,0,0);
        Sphere s=new Sphere(p0,1);
        //point in the sphera
        Point3D p2=new Point3D(2,0,0);
        assertEquals(new Vector(1,0,0) ,s.getNormal(p2) , "the func get bad normal" );

    }
}
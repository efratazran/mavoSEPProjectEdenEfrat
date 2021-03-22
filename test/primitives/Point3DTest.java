package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {

    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        //Test that the subtract point works correct
        Point3D p1=new Point3D(1,1,1);
        Point3D p2=new Point3D(1,1,1.5);
        assertTrue(new Vector(0,0,-0.5).equals(p1.subtract(p2)),"the new vector not true");

        // =============== Boundary Values Tests ==================
        // test zero vector for subtract opposite points
        try{
            p1.subtract(new Point3D(1, 1, 1));
            fail("the  didn't work");
        }
        catch(Exception e){
        }

    }

    @Test
    void testAdd() {
       // ============ Equivalence Partitions Tests ==============
       // Test that the add point works correct
       Vector v=new Vector(1,1,1);
       Point3D p=new Point3D(1,2,-3);
       assertTrue(new Point3D(2,3,-2).equals(p.add(v)),"The function not work correct");
    }

    @Test
    void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // Test that the DistanceSquared point works correct
        Point3D p1=new Point3D(1,2,3);
        Point3D p2=new Point3D(1,1,1);
        assertEquals(p1.distanceSquared(p2),5,0.00001,"The DistanceSquared not true");
        assertEquals(p2.distanceSquared(p1),5,0.00001,"The DistanceSquared not true");
    }

    @Test
    void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // Test that the Distance point works correct
        Point3D p1=new Point3D(1,2,3);
        Point3D p2=new Point3D(1,2,1);
        assertEquals(p1.distance(p2),2,0.00001,"The Distance not true");
        assertEquals(p2.distance(p1),2,0.00001,"The Distance not true");
    }
}
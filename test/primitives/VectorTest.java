package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTest {
    Vector v = new Vector(1, 2, 3);
    @Test
    /**
     * test of add of 2 vectors
     */
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        //Test that the add vector works correct
        Vector v1=new Vector(1,1,1);
        Vector v2=new Vector(-1,-1,-1.5);
        assertTrue(new Vector(0,0,-0.5).equals(v1.add(v2)),"the new vector not true");

        // =============== Boundary Values Tests ==================
        // test zero vector for add opposite vectors
        try{
            v1.add(new Vector(-1, -1, -1));
            fail("the operator subtract didn't work");
        }
        catch(Exception e){
        }
    }

    @Test
    void testSubtract() {
        // ============ Equivalence Partitions Tests ==============
        //Test that the subtract vector works correct
        Vector v1=new Vector(1,1,1);
        Vector v2=new Vector(1,1,1.5);
        assertTrue(new Vector(0,0,-0.5).equals(v1.subtract(v2)),"the new vector not true");

        // =============== Boundary Values Tests ==================
        // test zero vector for subtract opposite vectors
        try{
            v1.subtract(new Vector(1, 1, 1));
            fail("the operator subtract didn't work");
        }
        catch(Exception e){
        }

    }

    @Test
    void testScale() {
        // ============ Equivalence Partitions Tests ==============
        //Test that the mult in scalar vector works correct
        Vector v1=new Vector(1,1,1);
        assertTrue(new Vector(4,4,4).equals(v1.scale(4)),"the new vector not true");

        //=============== Boundary Values Tests ==================
        // checks if when scaling by zero an exception is thrown
        try
        {
            v1.scale(0);
            fail("Didn't throw can not scale by zero Exception");
        }
        catch(IllegalArgumentException ex)
        {
            assertTrue(true);
        }

    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals( v1.length() * v2.length(), vr.length(), 0.00001,"crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");
        // try {
        //     v1.crossProduct(v2);
        //     fail("crossProduct() for parallel vectors does not throw an exception");
        // } catch (Exception e) {}
    }


    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        assertEquals(v.lengthSquared(),14, 0.00001," wrong result length");

    }

    @Test
    void testLength() {
        // ============ Equivalence Partitions Tests ==============
        Vector vCopy = new Vector(v.get_head());
        Vector vCopyNormalize = vCopy.normalize();
        assertTrue(isZero(vCopyNormalize.length() - 1),"ERROR: normalize() result is not a unit vector");
    }

    @Test
    void testNormalize() {
        // ============ Equivalence Partitions Tests ==============
        Vector vCopy = new Vector(3.5,-5,10);
        Vector vCopyNormalize = vCopy.normalize();
        assertEquals(vCopy, vCopyNormalize,"ERROR: normalize() function creates a new vector");
    }

    @Test
    void testNormalized() {
        // ============ Equivalence Partitions Tests ==============
        Vector u = v.normalized();
        assertNotEquals(u, v, "ERROR: normalizated() function does not create a new vector");
    }

    @Test
    void testdotProduct() {

        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(0, 3, -2);
        // ============ Equivalence Partitions Tests ==============
        //Test that the dotProduct vector works correct
        double v = v1.dotProduct(v3);
        assertEquals(v, 0.0, 0.00001);
        //Test that the dotProduct vector works correct
        v = v1.dotProduct(v2) + 28;
        assertEquals(v,0.0, 0.00001);

        // not have Boundary Values Tests

    }


}
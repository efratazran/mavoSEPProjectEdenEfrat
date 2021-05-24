package geometries;

import primitives.*;

import java.util.List;

public abstract class Geometry implements intersectable {

    /**field**/
    protected Color emmission=Color.BLACK;
    private Material material=new Material();

    /**
     *
     * @param point should be nul for flat geomtry
     * @return normak to the geometry
     */
    public abstract Vector getNormal(Point3D point);

    /**
     * @return color emmission
     */
    public Color getEmmission() {
        return emmission;
    }

    /**
     * @param emmission
     * @return geomtry
     */
    public Geometry setEmission(Color emmission) {
         this.emmission = emmission;
         return this;
    }

    /**
     * @return material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * @param material
     * @return Geometry for format builder
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
}

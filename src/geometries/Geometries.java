package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements intersectable {
    /**
     * field
     */
    private List<intersectable> _intersectables = new LinkedList<>();
    /*** getters ***/
    public List<intersectable> get_intersectables() {
        return _intersectables;
    }

    /**
     * ctor that gets unlimited amount of geometries
     * @param geos Intersectable geometries
     */
    public Geometries(intersectable... geos) {
        add(geos);
    }

    /**
     * adds the geometries to the geometries list
     * @param geos Intersectable geometries
     */
    public void add(intersectable... geos) {
        Collections.addAll(_intersectables, geos);
    }

    /**
     * finds intersections of the geometries
     * @param ray ray value
     * @return a list of intersection points
     */

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> result = null;

        for (intersectable item : _intersectables) {
            List<GeoPoint> itempoints = item.findGeoIntersections(ray);
            if (itempoints != null) {
                //first time initialize result to new LinkedList
                if (result == null) {
                    result = new LinkedList<>();
                }
                //add all item points to the resulting list
                result.addAll(itempoints);
            }
        }
        return result;
    }
}

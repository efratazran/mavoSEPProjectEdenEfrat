package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements intersectable{


    private List<intersectable> _intersectables = new LinkedList<>();

    public List<intersectable> get_intersectables() {
        return _intersectables;
    }

    public Geometries(intersectable... geos) {
        add(geos);
    }

    public void add(intersectable... geos) {
        Collections.addAll(_intersectables, geos);
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        List<Point3D> result = null;
        for (intersectable item : _intersectables) {
            //get intersections points of a particular item from _intersectables
            List<Point3D> itempoints = item.findIntersections(ray);
            if(itempoints!= null){
                //first time initialize result to new LinkedList
                if(result== null){
                    result= new LinkedList<>();
                }
                //add all item points to the resulting list
                result.addAll(itempoints);
            }
        }
        return result;
    }

}

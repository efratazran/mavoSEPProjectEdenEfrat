package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * interface for interctions points
 */
public interface intersectable {

     class GeoPoint {
          /**
           * field
           */
          public Geometry geometry;
          public Point3D point;

          /**constructor
           * @param geometry
           * @param point
           */
          public GeoPoint(Geometry geometry, Point3D point) {
               this.geometry = geometry;
               this.point = point;
          }

          @Override
          public boolean equals(Object o) {
               if (this == o) return true;
               if (o == null || getClass() != o.getClass()) return false;
               GeoPoint geoPoint = (GeoPoint) o;
               return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
          }

     }

     default List<Point3D> findIntersections(Ray ray) {
          var geoList = findGeoIntersections(ray);
          return geoList == null ? null
                  : geoList.stream()
                  .map(gp -> gp.point)
                  .collect(Collectors.toList());
     }

     /**
      * finds intersection points of a ray and a geometry
      * @param ray ray value
      * @return list of geoPoints
      */
     List<GeoPoint> findGeoIntersections(Ray ray);

}

package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;

/**
 * @author meerz
 *
 */
public interface Intersectable {
	static class GeoPoint{
		public Geometry geometry;
		public Point3D point;
		
		public GeoPoint(Geometry geometry, Point3D point) {
			this.geometry = geometry;
			this.point = point;
		}
		
		public GeoPoint(GeoPoint geoPoint) {
			this.geometry = geoPoint.geometry;
			this.point = geoPoint.point;
		}
		
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof GeoPoint))
				return false;
			return point.equals(((GeoPoint)obj).point);
		}
	}
	public List<GeoPoint> findIntersections(Ray _ray);
	public static final List<GeoPoint> EMPTY_LIST = new ArrayList<>();
}

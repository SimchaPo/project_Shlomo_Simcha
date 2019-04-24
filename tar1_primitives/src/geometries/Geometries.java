package geometries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;
import tar1_primitives.src.geometries.Intersectable;
import geometries.Geometry;

public class Geometries implements Intersectable {
	List<Intersectable> _geometries;

	public Geometries(Intersectable... geometries) {
		_geometries = new ArrayList<Intersectable>(null);
		add(geometries);
	}

	/**
	 * Adding figures to the _geometries function using "foreach" for iterate in
	 * list of figures
	 * 
	 * @param geometries - list of figures
	 */
	public void add(Intersectable... geometries) {
		int LIndex = this._geometries.size() + 1;
		for (Intersectable intersectable : geometries) {
			_geometries.add(LIndex, intersectable);
			LIndex++;
		}
	}

	@Override
	public List<Point3D> findIntersections(Ray _ray) {
		List<Point3D> gmtriesIntrsctnsLst = new ArrayList<Point3D>(null);
		int count = 0;
		for (Intersectable _intrscble : _geometries) {
			List<Point3D> tmpLst = new ArrayList<Point3D>(null);
			tmpLst.addAll(0, _intrscble.findIntersections(_ray));
			for (Point3D point3d : tmpLst) {
				gmtriesIntrsctnsLst.add(count, point3d);
				count++;
			}
		}
		return gmtriesIntrsctnsLst;
	}

}

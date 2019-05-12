package geometries;

import java.util.ArrayList;
import java.util.Iterator;
//import java.util.Iterator;
import java.util.List;

import primitives.Point3D;
import primitives.Ray;
//import geometries.Geometry;
import geometries.Intersectable;

public class Geometries implements Intersectable {
	private List<Intersectable> _geometries;

	public List<Intersectable> get_geometries() {
		return _geometries;
	}

	public Geometries(Intersectable... geometries) {
		_geometries = new ArrayList<Intersectable>();
		add(geometries);
	}

	/**
	 * Adding figures to the _geometries function using "foreach" for iterate in
	 * list of figures
	 * 
	 * @param geometries - list of figures
	 */
	public void add(Intersectable... geometries) {
		for (Intersectable intersectable : geometries) {
			_geometries.add(intersectable);
		}
	}

	@Override
	public List<Point3D> findIntersections(Ray _ray) {
		List<Point3D> gmtriesIntrsctnsLst = new ArrayList<Point3D>();
		for (Intersectable _intrscble : _geometries) {
			gmtriesIntrsctnsLst.addAll(_intrscble.findIntersections(_ray));
		}
		return gmtriesIntrsctnsLst;
	}

	public Iterator<Intersectable> getGeometriesIterator() {
		return _geometries.iterator();

	}

	@Override
	public String toString() {
		String str = "";
		for (Intersectable geo : _geometries) {
			str += geo + "\n";
		}
		return str;
	}
}

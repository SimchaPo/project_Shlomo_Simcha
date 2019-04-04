package geometries;

/**
 * super class for radius
 * @author OWNER
 *
 */
public abstract class RadialGeometry implements Geometry {
	protected double _radius;
	
	/**
	 * Constructor
	 * @param rad
	 */
	public RadialGeometry(double rad) {
		_radius = rad;
	}
	
	/**
	 * Radius Getter
	 * @return
	 */
	double getRadius() {
		return this._radius;
	}
	
}

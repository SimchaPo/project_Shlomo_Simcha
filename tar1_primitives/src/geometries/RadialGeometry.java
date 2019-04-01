package geometries;

public abstract class RadialGeometry {
	protected double _radius;
	
	/**
	 * Constructor
	 * @param rad
	 */
	public RadialGeometry(double rad) {
		_radius=rad;
	}
	
	/**
	 * Copy Constructor
	 * @param _otherRad
	 */
	public RadialGeometry(RadialGeometry _otherRad) {
		this(_otherRad._radius);
	}
	
	/**
	 * Radius Getter
	 * @return
	 */
	double getRadius() {
		return this._radius;
	}
	
}

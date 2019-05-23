package geometries;

import primitives.Color;

/**
 * super class for radius
 * 
 * @author OWNER
 *
 */
public abstract class RadialGeometry extends Geometry {
	protected double _radius;

	/**
	 * Constructor
	 * 
	 * @param rad
	 */
	public RadialGeometry(double rad) {
		this(rad, Color.BLACK);
	}
	
	public RadialGeometry(double rad, Color emmission) {
		_emmission = emmission;
		_radius = rad;
	}

	/**
	 * Radius Getter
	 * 
	 * @return
	 */
	double getRadius() {
		return this._radius;
	}

}

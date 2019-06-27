//Simcha Podolsky 311215149
//Shlomo Meirzon

package geometries;

import primitives.Color;
import primitives.Material;

/**
 * super class for radius
 * 
 * @author OWNER
 *
 */
public abstract class RadialGeometry extends Geometry {
	protected double _radius;

	/********** constructors *********/
	/**
	 * Constructor
	 * 
	 * @param rad
	 */
	public RadialGeometry(double rad) {
		this(rad, Color.BLACK, new Material());
	}

	public RadialGeometry(double rad, Color emmission, Material material) {
		_material = new Material(material);
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

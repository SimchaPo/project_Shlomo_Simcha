//Simcha Podolsky 311215149
//Shlomo Meirzon

package elements;

import primitives.Color;

/**
 * 
 * @author OWNER this class sets the amount of light the color needs to get at
 *         point
 */
public class AmbientLight extends Light {
	public AmbientLight(Color ia, double ka) {
		super(ia.scale(ka));
	}
}

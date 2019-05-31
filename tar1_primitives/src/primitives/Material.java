package primitives;

/**
 * class for material - color of geometry
 * 
 * @author OWNER
 *
 */
public class Material {

	private double _kD, _kS, _kR, _kT;
	private int _nShininess;

	public Material() {
		this(0.5, 0.5, 40, 0, 0);
	}

	public Material(double kD, double kS, int nShininess, double kR, double kT) {
		_kD = kD;
		_kS = kS;
		_nShininess = nShininess;
		_kR = kR;
		_kT = kT;
	}

	public Material(double kD, double kS, int nShininess) {
		this(kD, kS, nShininess, 0, 0);
	}

	public Material(Material mat) {
		_kD = mat._kD;
		_kS = mat._kS;
		_nShininess = mat._nShininess;
		_kR = mat._kR;
		_kT = mat._kT;
	}

	public double getKD() {
		return _kD;
	}

	public double getKS() {
		return _kS;
	}

	public int getNShininess() {
		return _nShininess;
	}

	public double getKR() {
		return _kR;
	}

	public double getKT() {
		return _kT;
	}

}

package primitives;

public class Material {

	private double _kD, _kS;
	private int _nShininess;

	public Material() {
		this(0, 0, 0);
	}

	public Material(double kD, double kS, int nShininess) {
		_kD = kD;
		_kS = kS;
		_nShininess = nShininess;
	}

	public Material(Material mat) {
		_kD = mat._kD;
		_kS = mat._kS;
		_nShininess = mat._nShininess;
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

}

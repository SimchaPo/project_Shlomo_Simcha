package scene;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

/**
 * Scene class has all information for for a image scene
 * 
 * @author Simcha
 *
 */
public class Scene {

	private String _scene;
	private Color _background;
	private AmbientLight _ambientLight;
	private Geometries _geometries;
	private Camera _camera;
	private double screenDistance;

	// ******** Constructor **********//
	public Scene(String name) {
		_scene = name;
		_geometries = new Geometries();
	}

	// ****** Getters/Setters *******//
	public void setBackground(Color _background) {
		this._background = _background;
	}

	public void setAmbientLight(AmbientLight _ambientLight) {
		this._ambientLight = _ambientLight;
	}

	public void setCamera(Camera _camera, double _screenDistance) {
		this._camera = _camera;
		this.screenDistance = _screenDistance;
	}

	public String getScene() {
		return _scene;
	}

	public Color getBackground() {
		return _background;
	}

	public AmbientLight getAmbientLight() {
		return _ambientLight;
	}

	public Geometries getGeometries() {
		return _geometries;
	}

	public Camera getCamera() {
		return _camera;
	}

	public double getScreenDistance() {
		return screenDistance;
	}

	// ******* Functions *******//
	/**
	 * add geometries shapes to geometries
	 * 
	 * @param geometries
	 */
	public void addGeometries(Intersectable... geometries) {
		_geometries.add(geometries);
	}
}

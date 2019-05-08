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
	public void set_background(Color _background) {
		this._background = _background;
	}

	public void set_ambientLight(AmbientLight _ambientLight) {
		this._ambientLight = _ambientLight;
	}

	public void set_camera(Camera _camera, double _screenDistance) {
		this._camera = _camera;
		this.screenDistance = _screenDistance;
	}

	public String get_scene() {
		return _scene;
	}

	public Color get_background() {
		return _background;
	}

	public AmbientLight get_ambientLight() {
		return _ambientLight;
	}

	public Geometries get_geometries() {
		return _geometries;
	}

	public Camera get_camera() {
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

package scene;

import java.util.ArrayList;
import java.util.List;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import geometries.Plane;
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
	private double _screenDistance;
	private List<LightSource> _lights;
	private Plane _viewPlane;
	private Plane _focalPlane;

	/**
	 * ****** Constructors *********
	 */
	public Scene(String name) {
		_scene = name;
		_geometries = new Geometries();
		_lights = new ArrayList<LightSource>();
	}

	/**
	 * set lights
	 */
	public void setLights(LightSource... lights) {
		for (LightSource light : lights) {
			_lights.add(light);
		}
	}

	/**
	 * set background
	 * 
	 * @param background
	 */
	public void setBackground(Color background) {
		this._background = background;
	}

	/**
	 * set ambient light
	 * 
	 * @param ambientLight
	 */
	public void setAmbientLight(AmbientLight ambientLight) {
		this._ambientLight = ambientLight;
	}

	/**
	 * set camera and distance
	 * 
	 * @param camera
	 * @param screenDistance
	 */
	public void setCamera(Camera camera, double screenDistance) {
		this._camera = camera;
		this._screenDistance = screenDistance;
		this._viewPlane = new Plane(_camera.getP0().addVec(_camera.getVTo().scale(screenDistance)), camera.getVTo());
		if (_camera.isFocus()) {
			_focalPlane = new Plane(
					_camera.getP0().addVec(_camera.getVTo().scale(screenDistance + _camera.getFocusDistance())),
					camera.getVTo());
		}
	}

	/**
	 * get scene
	 * 
	 * @return
	 */
	public String getScene() {
		return _scene;
	}

	/**
	 * get background color
	 * 
	 * @return
	 */
	public Color getBackground() {
		return _background;
	}

	/**
	 * get ambient light
	 * 
	 * @return
	 */
	public AmbientLight getAmbientLight() {
		return _ambientLight;
	}

	/**
	 * get geometries
	 * 
	 * @return
	 */
	public Geometries getGeometries() {
		return _geometries;
	}

	/**
	 * get camera
	 * 
	 * @return
	 */
	public Camera getCamera() {
		return _camera;
	}

	/**
	 * get screen distance
	 * 
	 * @return
	 */
	public double getScreenDistance() {
		return _screenDistance;
	}

	/**
	 * get lights
	 * 
	 * @return
	 */
	public List<LightSource> getLights() {
		return _lights;
	}

	/**
	 * get focal plane
	 * 
	 * @return
	 */
	public Plane getFocalPlane() {
		return _focalPlane;
	}

	/**
	 * get view plane
	 * 
	 * @return
	 */
	public Plane getViewPlane() {
		return _viewPlane;
	}

	/**
	 * add geometries shapes to geometries
	 * 
	 * @param geometries
	 */
	public void addGeometries(Intersectable... geometries) {
		_geometries.add(geometries);
	}

	@Override
	public String toString() {
		return "name: " + _scene + "\ncamera: " + _camera + " " + _screenDistance
				+ (_camera.isFocus() ? _camera.getFocusDistance() : " without focus") + "\nback: " + _background
				+ "\nambient: " + _ambientLight.getIntensity() + "\n" + _geometries + "\n" + _lights;
	}
}

package scene;

import java.util.ArrayList;
import java.util.List;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
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
	private List<LightSource> _lights;

	/* ******* Constructors ********* */
	public Scene(String name) {
		_scene = name;
		_geometries = new Geometries();
		_lights = new ArrayList<LightSource>();
	}

	/* ***** Getters/Setters ****** */
	public void setLights(LightSource... lights) {
		for (LightSource light : lights) {
			_lights.add(light);
		}
	}

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

	public List<LightSource> getLights() {
		return _lights;
	}

	/******* Functions *******/
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
		return "name: " + _scene + "\ncamera: " + _camera + " " + screenDistance + "\nback: " + _background
				+ "\nambient: " + _ambientLight.getIntensity() + "\n" + _geometries + "\n" + _lights;
	}
}

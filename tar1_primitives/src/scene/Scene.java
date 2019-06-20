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
	private Plane _focalPlane;

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

	public void setBackground(Color background) {
		this._background = background;
	}

	public void setAmbientLight(AmbientLight ambientLight) {
		this._ambientLight = ambientLight;
	}

	public void setCamera(Camera camera, double screenDistance) {
		this._camera = camera;
		this._screenDistance = screenDistance;
		if(_camera.isFocus()) {
			_focalPlane = new Plane(_camera.getP0().addVec(_camera.getVTo().scale(screenDistance + _camera.getFocusDistance())), camera.getVTo());
		}
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
		return _screenDistance;
	}

	public List<LightSource> getLights() {
		return _lights;
	}
	
	public Plane getFocalPlane() {
		return _focalPlane;
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
		return "name: " + _scene + "\ncamera: " + _camera + " " + _screenDistance
				+ (_camera.isFocus() ? _camera.getFocusDistance() : " without focus") + "\nback: " + _background + "\nambient: "
				+ _ambientLight.getIntensity() + "\n" + _geometries + "\n" + _lights;
	}
}

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
	private double _screenDistance;
	private List<LightSource> _lights;
	private boolean _focus;
	private double _focusDistance;
	private double _apertureRadius;

	/* ******* Constructors ********* */
	public Scene(String name) {
		_scene = name;
		_geometries = new Geometries();
		_lights = new ArrayList<LightSource>();
	}

	public Scene(Scene sc) {
		_scene = sc._scene;
		_ambientLight = sc._ambientLight;
		_background = sc._background;
		_camera = sc._camera;
		_geometries = new Geometries(sc._geometries);
		_screenDistance = sc._screenDistance;
		_lights = new ArrayList<LightSource>(sc._lights);
		_apertureRadius = sc._apertureRadius;
		_focus = sc._focus;
		_focusDistance = sc._focusDistance;

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
		this._focus = false;
	}

	public void setCamera(Camera camera, double screenDistance, double focusDistance, double apertureRadius) {
		this._camera = camera;
		this._screenDistance = screenDistance;
		this._focusDistance = focusDistance;
		this._apertureRadius = apertureRadius;
		this._focus = true;
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

	public boolean isFocus() {
		return _focus;
	}

	public double getFocusDistance() {
		return _focusDistance;
	}

	public double getApertureRadius() {
		return _apertureRadius;
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
				+ (isFocus() ? _focusDistance : " without focus") + "\nback: " + _background + "\nambient: "
				+ _ambientLight.getIntensity() + "\n" + _geometries + "\n" + _lights;
	}
}

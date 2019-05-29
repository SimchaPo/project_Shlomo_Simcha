package renderer;

import java.util.List;

import elements.LightSource;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;
import static geometries.Intersectable.GeoPoint;

/**
 * Render class gets scene and image writer and sets everything together to in
 * image
 * 
 * @author OWNER
 *
 */
public class Render {

	private Scene _scene;
	private ImageWriter _imageWriter;

	public Render(Scene s, ImageWriter im) {
		_scene = s;
		_imageWriter = im;
	}

	public Scene get_scene() {
		return _scene;
	}

	public ImageWriter get_imageWriter() {
		return _imageWriter;
	}

	/**
	 * function colors each point in the image according to the intersections
	 */
	public void renderImage() {
		for (int i = 0; i < _imageWriter.getNx(); ++i) {
			for (int j = 0; j < _imageWriter.getNy(); ++j) {
				Ray ray = _scene.getCamera().constructRayThroughPixel(_imageWriter.getNx(), _imageWriter.getNy(), i, j,
						_scene.getScreenDistance(), _imageWriter.getWidth(), _imageWriter.getHeight());
				List<GeoPoint> intersections = _scene.getGeometries().findIntersections(ray);
				if (intersections.isEmpty()) {
					_imageWriter.writePixel(i, j, _scene.getBackground().getColor());
				} else {
					GeoPoint closestPoint = getClosestPoint(intersections);
					_imageWriter.writePixel(i, j, calcColor(closestPoint).getColor());
				}
			}
		}
	}

	/**
	 * prints a grid and in image
	 * 
	 * @param size
	 * @param opt
	 */
	public void printGrid(int size, java.awt.Color... opt) {
		int nX = _imageWriter.getNx(), nY = _imageWriter.getNy();
		int sizeX = nX % 2 == 0 ? nX / size : nX / size + 1, sizeY = nY % 2 == 0 ? nY / size : nY / size + 1;
		java.awt.Color _color = opt.length > 0 ? opt[0] : java.awt.Color.white;
		for (int i = 0; i < nX; i += sizeX) {
			for (int j = 0; j < nY; ++j) {
				_imageWriter.writePixel(i, j, _color);
				_imageWriter.writePixel(nX - 1, j, _color);
			}
		}
		for (int j = 0; j < nY; j += sizeY) {
			for (int i = 0; i < nX; ++i) {
				_imageWriter.writePixel(i, j, _color);
				_imageWriter.writePixel(i, nY - 1, _color);
			}
		}
	}

	/**
	 * gets a point and sets its color
	 * 
	 * @param intersection
	 * @return
	 */
	private Color calcColor(GeoPoint intersection) {
		Color color = new Color(_scene.getAmbientLight().getIntensity());
		color = color.add(intersection.geometry.getEmmission());
		Vector v = intersection.point.subtract(_scene.getCamera().getP0()).normalize();
		Vector n = intersection.geometry.getNormal(intersection.point);
		int nShininess = intersection.geometry.getMaterial().getNShininess();
		double kd = intersection.geometry.getMaterial().getKD();	
		double ks = intersection.geometry.getMaterial().getKS();
		//if(kd > 0 || ks > 0) System.out.println("a" + color);
		for (LightSource lightSource : _scene.getLights()) {
			Vector l = lightSource.getL(intersection.point);
			if(n.vectorsDotProduct(l)*n.vectorsDotProduct(v) > 0) {
				Color lightIntensity = new Color(lightSource.getIntensity(intersection.point));
				color = color.add(calcDiffusive(kd, l, n, lightIntensity), calcSpecular(ks, l, n, v, nShininess, lightIntensity));
				if(kd > 0 || ks > 0) System.out.println("kd " + kd + " ks " + ks + " nS " + nShininess + " l " + l + " n " + n + " v " + v + " light " + lightIntensity );
			}
		}

		//if(kd > 0 || ks > 0) System.out.println(color);
		return color;
	}
	
	private Color calcDiffusive(double kd, Vector l, Vector n,Color lightIntensity) {
		//if(kd > 0) System.out.println(kd*Math.abs(l.vectorsDotProduct(n)));
		return new Color(lightIntensity.scale(kd*Math.abs(l.vectorsDotProduct(n))));
	}

	private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
		Vector r = l.vectorSub(n.scale(2*(l.vectorsDotProduct(n)))).normalize();
		//if(ks > 0) System.out.println(ks*Math.pow(Math.max(0, v.scale(-1).vectorsDotProduct(r)), nShininess));
		return new Color(lightIntensity.scale(ks*Math.pow(Math.max(0, -1*v.vectorsDotProduct(r)), nShininess)));
	}
	
	/**
	 * function gets a Point3D list and returns the closest point to camera
	 * 
	 * @param intersectionsPoints
	 * @return returns the closest point to camera
	 */
	private GeoPoint getClosestPoint(List<GeoPoint> intersectionsPoints) {
		Point3D rayPnt = _scene.getCamera().getP0();
		GeoPoint closestPoint = intersectionsPoints.get(0);
		double minDistancePow = rayPnt.distancePow(closestPoint.point);
		double disPow;
		for (GeoPoint p : intersectionsPoints) {
			disPow = rayPnt.distancePow(p.point);
			if (disPow < minDistancePow) {
				minDistancePow = disPow;
				closestPoint = p;
			}
		}
		return closestPoint;
	}
}

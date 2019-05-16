package renderer;

import java.util.List;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

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
				Ray ray = _scene.get_camera().constructRayThroughPixel(_imageWriter.getNx(), _imageWriter.getNy(), i, j,
						_scene.get_screenDistance(), _imageWriter.getWidth(), _imageWriter.getHeight());
				List<Point3D> intersectionsPoints = _scene.get_geometries().findIntersections(ray);
				if (intersectionsPoints.isEmpty()) {
					_imageWriter.writePixel(i, j, _scene.get_background().getColor());
				} else {
					Point3D closestPoint = getClosestPoint(intersectionsPoints);
					_imageWriter.writePixel(i, j, calcColor(closestPoint).getColor());
				}
			}
		}
	}

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

	private Color calcColor(Point3D pnt) {
		return _scene.get_ambientLight().getIntensity();
	}

	/**
	 * function gets a Point3D list and returns the closest point to camera
	 * 
	 * @param intersectionsPoints
	 * @return returns the closest point to camera
	 */
	private Point3D getClosestPoint(List<Point3D> intersectionsPoints) {
		Point3D rayPnt = _scene.get_camera().getP0();
		Point3D closestPoint = intersectionsPoints.get(0);
		double minDistancePow = rayPnt.distancePow(closestPoint);
		for (Point3D p : intersectionsPoints) {
			if (rayPnt.distancePow(p) < minDistancePow) {
				minDistancePow = rayPnt.distancePow(p);
				closestPoint = p;
			}
		}
		return closestPoint;
	}
}

package renderer;

import static primitives.Util.alignZero;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import elements.Camera;
import geometries.Plane;
import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

public class DepthOfField {
	private double _distance;
	private Render _render;
	private Scene _scene;
	private Camera _camera;
	private Vector _vTo;
	private Point3D _p0;
	private Plane _focusPlane;
	private ImageWriter _imageWriter;
	private Vector _vUp;
	private Vector _vRight;

	public DepthOfField(double dis, Render rndr) {
		_distance = dis;
		_render = rndr;
		_scene = _render.getScene();
		_imageWriter = _render.getImageWriter();
		_camera = _scene.getCamera();
		_vTo = _camera.getVTo();
		_vUp = _camera.getVUp();
		_vRight = _camera.getVRight();
		_p0 = _camera.getP0();
		_focusPlane = new Plane(_p0.addVec(_vTo.scale(_distance)), _vTo);
	}

	public void addFocus() {
		int nX = _imageWriter.getNx(), nY = _imageWriter.getNy();
		for (int i = 0; i < nX; ++i) {
			for (int j = 0; j < nY; ++j) {
				List<Point3D> points = getPointsInPixel(i, j);
				Point3D focusPoint = _focusPlane.findIntersections(new Ray(_p0, points.get(0).subtract(_p0)))
						.get(0).point;
				_imageWriter.writePixel(i, j, calcColor(points, focusPoint));
			}
		}
	}

	private java.awt.Color calcColor(List<Point3D> points, Point3D focusPoint) {
		int r = 0, g = 0, b = 0;
		int len = points.size();
		java.awt.Color col = new java.awt.Color(0, 0, 0);
		for (Point3D pnt : points) {
			col = _render.getPointColor(new Ray(pnt, new Vector(pnt.subtract(focusPoint))));
			r += col.getRed();
			g += col.getGreen();
			b += col.getBlue();
		}
		return new java.awt.Color(r / len, g / len, b / len);
	}

	/**
	 * get list of randomize points in pixel
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public List<Point3D> getPointsInPixel(int i, int j) {
		int nX = _imageWriter.getNx(), nY = _imageWriter.getNy();
		double screenDistance = _scene.getScreenDistance(), screenHeight = _imageWriter.getHeight(),
				screenWidth = _imageWriter.getWidth();
		double ry = alignZero(screenHeight / nY) / 2;
		double rx = alignZero(screenWidth / nX) / 2;
		List<Point3D> points = new ArrayList<Point3D>();
		Point3D pij = _camera.getPixelCenter(nX, nY, i, j, screenDistance, screenWidth, screenHeight);
		points.add(pij);
		double r1, r2;
		Point3D pntToAdd;
		for (int t = 0; t < 5; ++t) {
			r1 = Math.random() * ry;
			r2 = Math.random() * rx;
			pntToAdd = r1 == 0 ? new Point3D(pij) : pij.addVec(_vUp.scale(r1));
			pntToAdd = r2 == 0 ? pntToAdd : pij.addVec(_vRight.scale(r2));
			points.add(pntToAdd);
			r1 = Math.random() * ry;
			r2 = Math.random() * rx;
			pntToAdd = r1 == 0 ? new Point3D(pij) : pij.addVec(_vUp.scale(-r1));
			pntToAdd = r2 == 0 ? pntToAdd : pij.addVec(_vRight.scale(-r2));
			points.add(pntToAdd);
			r1 = Math.random() * ry;
			r2 = Math.random() * rx;
			pntToAdd = r1 == 0 ? new Point3D(pij) : pij.addVec(_vUp.scale(r1));
			pntToAdd = r2 == 0 ? pntToAdd : pij.addVec(_vRight.scale(-r2));
			points.add(pntToAdd);
			r1 = Math.random() * ry;
			r2 = Math.random() * rx;
			pntToAdd = r1 == 0 ? new Point3D(pij) : pij.addVec(_vUp.scale(-r1));
			pntToAdd = r2 == 0 ? pntToAdd : pij.addVec(_vRight.scale(r2));
			points.add(pntToAdd);
		}
		return points;
	}

}

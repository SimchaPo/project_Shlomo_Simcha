package primitives;

import primitives.Vector;

import java.lang.Math;

public class test {
	public double getAngle(Vector vec1, Vector vec2) {
		return Math.acos(vec1.vectorsDotProduct(vec2)/
				(vec1.vectorPoint.distance(Vector.nullCoordinate)*vec2.vectorPoint.distance(Vector.nullCoordinate)));
	}
	public boolean isOrtogonal(Vector vec1, Vector vec2) {
		return getAngle(vec1, vec2) == 90;
	}
	public boolean isSharpAngle(Vector vec1, Vector vec2) {
		return getAngle(vec1, vec2) < 90;
	}
	public boolean isObtuseAngle(Vector vec1, Vector vec2) {
		return getAngle(vec1, vec2) > 90 && getAngle(vec1, vec2) < 180;
	}
	public boolean isRightAngle(Vector vec1, Vector vec2) {
		return getAngle(vec1, vec2) == 180;
	}
}

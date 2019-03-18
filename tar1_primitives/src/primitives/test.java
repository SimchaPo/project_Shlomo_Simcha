package primitives;

import primitives.Vector;

import java.lang.Math

public class test {
	public boolean isOrtogonal(Vector vec1, Vector vec2) {
		double angle = Math.acos(vec1.vectorsDotProduct(vec2)/
				(vec1.vectorPoint.distance(Vector.nullCoordinate)*vec2.vectorPoint.distance(Vector.nullCoordinate)));
		
	}
}

package primitives;

import primitives.Point3D;

public class Vector {
	Point3D vectorPoint;
	Vector vectorUnit;
	static Point3D nullCoordinate= new Point3D();
	public Vector(Point3D pnt) {
		vectorPoint = pnt;
		if (pnt.distance(nullCoordinate)!=1) {
			
		}
	}
	public Vector(Point3D pnt1, Point3D pnt2) {
		vectorPoint = pnt1.substruct(pnt2).vectorPoint;
	}
	public Vector vectorUnitDet() {		
		Double distnace = this.vectorPoint.distance(nullCoordinate);
		 return new Vector(new Point3D(this.vectorPoint.x._coord/distnace,
				this.vectorPoint.y._coord/distnace,
				this.vectorPoint.z._coord/distnace));		 
	}
}
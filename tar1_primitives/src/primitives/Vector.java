package primitives;
import primitives.Point3D;


public class Vector {
	Point3D vectorPoint;
	Vector vectorUnit;
	Point3D nullCoordinate= new Point3D();
	public Vector(Point3D pnt) {
		vectorPoint=pnt;
		if (pnt.distance(nullCoordinate)!=1) {
			
		}
	}
	public Vector(Point3D otherPoint) {
		// TODO Auto-generated constructor stub
	}
	public Vector vectorUnitDet() {
		Double distnce = this.
		vectorUnit = Point3D(this.vectorPoint.x._coord/vectorPoint.distance(nullCoordinate),
				this.vectorUnit=vectorPoint.y._coord/vectorPoint.distance(nullCoordinate),
				this.vectorUn it=vectorPoint.z._coord/vectorPoint.distance(nullCoordinate))
	}
	public Vector vectorUnitDet(Vector otherVec) {
		Double distance = this.vectorPoint.distance(otherVec.vectorPoint);
		return new Vector(new Point3D(this.vectorPoint.x._coord/distance,
				vectorPoint.y._coord/distance,
				vectorPoint.z._coord/distance));
	}

}

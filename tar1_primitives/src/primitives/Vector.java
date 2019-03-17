package primitives;
import javax.tools.Diagnostic;

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
		Double distnce=this.vectorPoint.distance(nullCoordinate);
		 return vectorUnit=new Vector((new Point3D(this.vectorPoint.x._coord/distnce,
				this.vectorPoint.y._coord/distnce,
				this.vectorPoint.z._coord/distnce)));
		
	}
	public Vector vectorUnitDet(Vector otherVec) {}

}

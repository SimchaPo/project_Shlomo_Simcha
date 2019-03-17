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
<<<<<<< HEAD
		Double distnce = this.
		vectorUnit = Point3D(this.vectorPoint.x._coord/vectorPoint.distance(nullCoordinate),
				this.vectorUnit=vectorPoint.y._coord/vectorPoint.distance(nullCoordinate),
				this.vectorUn it=vectorPoint.z._coord/vectorPoint.distance(nullCoordinate))
=======
		Double distnce=this.vectorPoint.distance(nullCoordinate);
		 return vectorUnit=new Vector((new Point3D(this.vectorPoint.x._coord/distnce,
				this.vectorPoint.y._coord/distnce,
				this.vectorPoint.z._coord/distnce)));
		
>>>>>>> branch 'master' of https://github.com/SimchaPo/project_Shlomo_Simcha.git
	}
	public Vector vectorUnitDet(Vector otherVec) {
		Double distance = this.vectorPoint.distance(otherVec.vectorPoint);
		return new Vector(new Point3D(this.vectorPoint.x._coord/distance,
				vectorPoint.y._coord/distance,
				vectorPoint.z._coord/distance));
	}

}

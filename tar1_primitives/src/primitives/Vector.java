package primitives;

import javax.tools.Diagnostic;

import primitives.Point3D;

public class Vector {
	Point3D vectorPoint;
	Vector vectorUnit;
	static Point3D nullCoordinate = new Point3D();

	public Vector(Point3D pnt,Point3D beginPnt) {
		vectorPoint=pnt;
		if (pnt.distance(nullCoordinate)!=1) {
			vectorUnit=this;
		}
		else vectorUnit.vectorUnitDet(beginPnt)
	}
	public Vector(Point3D pnt) {
		this(pnt,nullCoordinate);
		if (pnt.distance(nullCoordinate)!=1) {
			vectorUnit=this;
		}
		else vectorUnit.vectorUnitDet()
	}

	public Vector vectorUnitDet() {
		Double distnce = this.vectorPoint.distance(nullCoordinate);
		return vectorUnit = new Vector((new Point3D(this.vectorPoint.x._coord / distnce,
				this.vectorPoint.y._coord / distnce, this.vectorPoint.z._coord / distnce)));

	}

	public Vector vectorUnitDet(Point3D otherVecPnt) {
		Double distnce = this.vectorPoint.distance(otherVecPnt);
		return vectorUnit = new Vector((new Point3D(this.vectorPoint.x._coord / distnce,
				this.vectorPoint.y._coord / distnce, this.vectorPoint.z._coord / distnce)));
	}

}

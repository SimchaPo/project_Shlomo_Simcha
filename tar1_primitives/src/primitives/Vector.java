package primitives;

import javax.tools.Diagnostic;

import primitives.Point3D;

public class Vector {
	double vecLenth = 0.0;
	Point3D vectorPoint;
	Vector vectorUnit;
	static Point3D nullCoordinate = new Point3D();

	public Vector(Point3D pnt, Point3D beginPnt) {
		vectorPoint = pnt;
		vecLenth = pnt.distance(beginPnt);
		if (this.vecLenth == 1) {
			vectorUnit = this;
		} else
			vectorUnit.setVectorUnitDet(beginPnt);
	}

	public Vector(Point3D pnt) {
		this(pnt, nullCoordinate);
		if (this.vecLenth == 1) {
			vectorUnit = this;
		} else
			vectorUnit.setVectorUnitDet();
	}

	public void setVectorUnitDet() {
		Double distnce = this.vectorPoint.distance(nullCoordinate);
		vectorUnit = new Vector((new Point3D(this.vectorPoint.x._coord / distnce, this.vectorPoint.y._coord / distnce,
				this.vectorPoint.z._coord / distnce)));

	}

	public void setVectorUnitDet(Point3D otherVecPnt) {
		Double distnce = this.vectorPoint.distance(otherVecPnt);
		vectorUnit = new Vector((new Point3D(this.vectorPoint.x._coord / distnce, this.vectorPoint.y._coord / distnce,
				this.vectorPoint.z._coord / distnce)));
	}

	public Vector vectorAdd(Vector otherVector) {
		return new Vector(new Point3D(this.vectorPoint.x._coord + otherVector.vectorPoint.x._coord,
				this.vectorPoint.y._coord + otherVector.vectorPoint.y._coord,
				this.vectorPoint.z._coord + otherVector.vectorPoint.z._coord), this.vectorPoint);
	}

	public Vector vectorSub(Vector otherVector) {
		return this.vectorAdd(otherVector).vecProductByScalar(-1);
	}

	public Vector vecProductByScalar(double scalar) {
		return new Vector(new Point3D(this.vectorPoint.x._coord * scalar, this.vectorPoint.y._coord * scalar,
				this.vectorPoint.z._coord * scalar));
	}

	public double vectrsDotProduct(Vector otherVector) {
		// add orto. check
		return this.vectorPoint.x._coord * otherVector.vectorPoint.x._coord
				+ this.vectorPoint.y._coord * otherVector.vectorPoint.y._coord
				+ this.vectorPoint.z._coord * otherVector.vectorPoint.z._coord;
	}

	public Vector vectrsCrossProduct(Vector otherVector) {
		return new Vector(new Point3D(
				this.vectorPoint.y._coord * otherVector.vectorPoint.z._coord
						- this.vectorPoint.z._coord * otherVector.vectorPoint.y._coord,
				this.vectorPoint.x._coord * otherVector.vectorPoint.z._coord
						- this.vectorPoint.z._coord * otherVector.vectorPoint.x._coord,
				this.vectorPoint.x._coord * otherVector.vectorPoint.y._coord
						- this.vectorPoint.y._coord * otherVector.vectorPoint.x._coord));
	}

}

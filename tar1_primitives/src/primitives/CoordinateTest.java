package primitives;

import static org.junit.Assume.assumeTrue;

import junit.framework.TestCase;

public class CoordinateTest extends TestCase {

	public void testCoordinateDouble() {
		fail("Not yet implemented");
	}

	public void testCoordinateCoordinate() {
		fail("Not yet implemented");
	}

	public void testGet() {
		fail("Not yet implemented");
	}

	public void testEqualsObject() {
	
		Coordinate a = new Coordinate(6.5);
		Coordinate b = new Coordinate(6.0);
		boolean bool = a.equals(b);
		assertEquals("false", true, bool);
		
	}

	public void testToString() {
		fail("Not yet implemented");
	}

	public void testSubtract() {
		fail("Not yet implemented");
	}

	public void testAdd() {
		fail("Not yet implemented");
	}

	public void testScale() {
		fail("Not yet implemented");
	}

	public void testMultiply() {
		fail("Not yet implemented");
	}

}

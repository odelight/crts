package gotox.crts.model;

import static org.junit.Assert.*;

import gotox.crts.GeometryUtils;
import gotox.crts.Intersection;

import java.awt.Point;

import org.junit.Test;

public class TestGeometryUtils {
	
	@Test
	public void testLinesIntersect1(){
		Point p1 = new Point(1,1);
		Point p2 = new Point(3,3);
		Point p3 = new Point(1,3);
		Point p4 = new Point(3,1);
		
		Point intersection = GeometryUtils.lineIntersection(p1, p2, p3, p4).firstPoint();
		assertEquals(new Point(2,2), intersection);	
	}
	
	@Test
	public void testLinesIntersect2(){
		Point p1 = new Point(1,1);
		Point p2 = new Point(3,3);
		Point p3 = new Point(1,3);
		Point p4 = new Point(3,1);
		
		Intersection intersection = GeometryUtils.lineIntersection(p1, p3, p2, p4);
		assertEquals(null, intersection);
	}
	
	@Test
	public void testLinesIntersect3(){
		Point p1 = new Point(1,1);
		Point p2 = new Point(5,1);
		Point p3 = new Point(3,1);
		Point p4 = new Point(8,1);
		
		Point intersection = GeometryUtils.lineIntersection(p1, p2, p3, p4).firstPoint();
		assertNotNull( intersection);
	}
	
	@Test
	public void testLinesIntersect4(){
		Point p1 = new Point(1,1);
		Point p2 = new Point(3,2);
		Point p3 = new Point(1,3);
		Point p4 = new Point(3,3);
		
		Intersection intersection = GeometryUtils.lineIntersection(p1, p2, p3, p4);
		assertEquals(null, intersection);
	}
	
	@Test
	public void testLinesIntersect5(){
		Point p1 = new Point(10,80);
		Point p2 = new Point(40,80);
		Point p3 = new Point(0,50);
		Point p4 = new Point(0,0);
		
		Intersection intersection = GeometryUtils.lineIntersection(p1, p2, p3, p4);
		assertEquals(null, intersection);
	}
	
	@Test
	public void testLinesIntersect6(){
		Point p1 = new Point(10,0);
		Point p2 = new Point(10,100);
		Point p3 = new Point(0,50);
		Point p4 = new Point(20,50);
		
		Point intersection = GeometryUtils.lineIntersection(p1, p2, p3, p4).firstPoint();
		assertEquals(new Point(10,50), intersection);	
	}
	
	@Test
	public void testLinesIntersect7(){
		Point p1 = new Point(10,0);
		Point p2 = new Point(10,100);
		Point p3 = new Point(0,120);
		Point p4 = new Point(20,120);
		
		Intersection intersection = GeometryUtils.lineIntersection(p1, p2, p3, p4);
		assertEquals(null, intersection);	
	}
	
	@Test
	public void testLinesIntersect8(){
		Point p1 = new Point(250,25);
		Point p2 = new Point(275,50);
		Point p3 = new Point(300,75);
		Point p4 = new Point(325,100);
		
		Intersection intersection = GeometryUtils.lineIntersection(p1, p2, p3, p4);
		assertEquals(null, intersection);	
	}
	
	@Test
	public void testLinesIntersect9(){
		Point p1 = new Point(250,25);
		Point p2 = new Point(300,75);
		Point p3 = new Point(275,50);
		Point p4 = new Point(325,100);
		
		Intersection intersection = GeometryUtils.lineIntersection(p1, p2, p3, p4);
		assertTrue(p2.equals(intersection.firstPoint()) || p2.equals(intersection.secondPoint()));	
		assertTrue(p3.equals(intersection.firstPoint()) || p3.equals(intersection.secondPoint()));	

	}
	@Test
	public void testLinesIntersect10(){
		Point p1 = new Point(250,25);
		Point p2 = new Point(300,75);
		Point p3 = new Point(300,75);
		Point p4 = new Point(325,100);
		
		Intersection intersection = GeometryUtils.lineIntersection(p1, p2, p3, p4);
		assertTrue(p2.equals(intersection.firstPoint()));	
		assertTrue(null == intersection.secondPoint());	
	}

	@Test
	public void testLinesIntersect11(){
		Point p1 = new Point(235,118);
		Point p2 = new Point(240,118);
		Point p3 = new Point(245,118);
		Point p4 = new Point(255,118);
		
		Intersection intersection = GeometryUtils.lineIntersection(p1, p2, p3, p4);
		assertTrue(null == intersection);	
	}
}

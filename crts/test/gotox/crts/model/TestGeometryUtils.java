package gotox.crts.model;

import static org.junit.Assert.*;

import gotox.crts.GeometryUtils;

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
		
		Point intersection = GeometryUtils.lineIntersection(p1, p3, p2, p4).firstPoint();
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
		
		Point intersection = GeometryUtils.lineIntersection(p1, p2, p3, p4).firstPoint();
		assertEquals(null, intersection);
	}
	
	@Test
	public void testLinesIntersect5(){
		Point p1 = new Point(10,80);
		Point p2 = new Point(40,80);
		Point p3 = new Point(0,50);
		Point p4 = new Point(0,0);
		
		Point intersection = GeometryUtils.lineIntersection(p1, p2, p3, p4).firstPoint();
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
		
		Point intersection = GeometryUtils.lineIntersection(p1, p2, p3, p4).firstPoint();
		assertEquals(null, intersection);	
	}
	
}

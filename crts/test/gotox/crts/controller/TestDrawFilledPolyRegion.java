package gotox.crts.controller;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gotox.crts.model.AbstractColor;
import gotox.crts.model.MapModel;
import gotox.crts.model.Polygon;

import org.junit.Before;
import org.junit.Test;

public class TestDrawFilledPolyRegion {
	private MapModel map;

	@Before
	public void init() {
		int width = 100;
		int height = 100;
		map = new MapModel(new Dimension(width, height));
		int cornerSize = 50;
		{
		List<Point> player1Blob = Arrays.asList(
				new Point[]{new Point(0,0),
						new Point(cornerSize,0),
						new Point(cornerSize,cornerSize),
						new Point(0,cornerSize),
						new Point(0,0)});
		Polygon player1 = new Polygon(player1Blob, map);
		map.putBlob(AbstractColor.PLAYER1, player1);
		}
	}

	@Test
	public void testApplyUShaped() {
		DrawFilledPolyRegion region = new DrawFilledPolyRegion(
				getUShapePolyLine(), AbstractColor.PLAYER1);
		region.apply(map);
		
		Polygon blob = map.getBlob(AbstractColor.PLAYER1);

		assertFalse(blob.contains(new Point(75,25)));
		assertFalse(blob.contains(new Point(75,75)));
		
		assertTrue(blob.contains(new Point(25,25)));
		assertTrue(blob.contains(new Point(25,75)));
		


	}

	private List<Point> getUShapePolyLine() {
		List<Point> polyLine = new ArrayList<>();
		polyLine.add(new Point(10, 10));
		polyLine.add(new Point(10, 80));
		polyLine.add(new Point(40, 80));
		polyLine.add(new Point(40, 10));
		return polyLine;
	}
	
	@Test
	public void testApplySlightWedgeShaped() {
		DrawFilledPolyRegion region = new DrawFilledPolyRegion(
				getSlightWedgePolyLine(), AbstractColor.PLAYER1);
		region.apply(map);
		
		
		Polygon blob = map.getBlob(AbstractColor.PLAYER1);
		
		assertFalse(blob.contains(new Point(75,25)));
		assertFalse(blob.contains(new Point(75,75)));
		
		assertTrue(blob.contains(new Point(25,25)));
		assertTrue(blob.contains(new Point(25,75)));
	}

	
	private List<Point> getSlightWedgePolyLine() {
		List<Point> polyLine = new ArrayList<>();
		polyLine.add(new Point(10, 10));
		polyLine.add(new Point(5, 80));
		polyLine.add(new Point(45, 80));
		polyLine.add(new Point(40, 10));
		return polyLine;
	}
	
	@Test
	public void testApply45WedgeShaped() {
		DrawFilledPolyRegion region = new DrawFilledPolyRegion(
				get45WedgePolyLine(), AbstractColor.PLAYER1);
		region.apply(map);
		Polygon blob = map.getBlob(AbstractColor.PLAYER1);
		
		assertFalse(blob.contains(new Point(75,25)));
		assertFalse(blob.contains(new Point(75,75)));
		
		assertTrue(blob.contains(new Point(25,25)));
		assertTrue(blob.contains(new Point(25,55)));

	}

	
	private List<Point> get45WedgePolyLine() {
		List<Point> polyLine = new ArrayList<>();
		polyLine.add(new Point(20, 40));
		polyLine.add(new Point(0, 60));
		polyLine.add(new Point(60, 60));
		polyLine.add(new Point(40, 40));
		return polyLine;
	}
	
	@Test
	public void testApplyBackwardsC() {
		DrawFilledPolyRegion region = new DrawFilledPolyRegion(
				getBackwardsCPolyLine(), AbstractColor.PLAYER1);
		region.apply(map);
		
		Polygon blob = map.getBlob(AbstractColor.PLAYER1);
		
		assertFalse(blob.contains(new Point(25,75)));
		assertFalse(blob.contains(new Point(75,75)));
		assertFalse(blob.contains(new Point(80,60)));

		assertTrue(blob.contains(new Point(25,25)));
		assertTrue(blob.contains(new Point(55,35)));
		
		
		for(int x = 0; x <= 80; x++){
			for(int y = 20; y <= 40; y++){
				assertTrue("wrong color at (x,y) = ("+ x +","+y+")", blob.contains(new Point(x,y)));
			}				
		}
	}

	
	private List<Point> getBackwardsCPolyLine() {
		List<Point> polyLine = new ArrayList<>();
		polyLine.add(new Point(40, 20));
		polyLine.add(new Point(80, 20));
		polyLine.add(new Point(80, 40));
		polyLine.add(new Point(40, 40));
		return polyLine;
	}
	
//	@Test
//	public void testApplyThinCrack() {
//		DrawFilledPolyRegion region = new DrawFilledPolyRegion(
//				getThinCrackPolyLine(), AbstractColor.PLAYER1);
//		region.apply(map);
//		
//		assertEquals(AbstractColor.BLANK, map.getColor(75, 75));
//		assertEquals(AbstractColor.BLANK, map.getColor(75, 25));
//		assertEquals(AbstractColor.BLANK, map.getColor(35, 50));
//		
//		assertEquals(AbstractColor.PLAYER1, map.getColor(35, 51));
//		assertEquals(AbstractColor.PLAYER1, map.getColor(35, 49));
//		assertEquals(AbstractColor.PLAYER1, map.getColor(29, 51));
//		assertEquals(AbstractColor.PLAYER1, map.getColor(29, 49));		
//		assertEquals(AbstractColor.PLAYER1, map.getColor(30, 51));
//		assertEquals(AbstractColor.PLAYER1, map.getColor(30, 49));
//		assertEquals(AbstractColor.PLAYER1, map.getColor(31, 51));
//		assertEquals(AbstractColor.PLAYER1, map.getColor(31, 49));
//
//
//		assertEquals(AbstractColor.PLAYER1, map.getColor(25, 75));
//		assertEquals(AbstractColor.PLAYER1, map.getColor(25, 25));
//		
//	}
//
//	
//	private List<Point> getThinCrackPolyLine() {
//		List<Point> polyLine = new ArrayList<>();
//		polyLine.add(new Point(20, 20));
//		polyLine.add(new Point(20, 80));
//		polyLine.add(new Point(40, 80));
//		polyLine.add(new Point(40, 51));
//		polyLine.add(new Point(30, 51));		
//		polyLine.add(new Point(30, 20));
//		return polyLine;
//	}
	
}

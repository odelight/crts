package gotox.crts.controller;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import gotox.crts.model.AbstractColor;
import gotox.crts.model.MapModel;

import org.junit.Before;
import org.junit.Test;

public class TestDrawFilledPolyRegion {
	private MapModel map;

	@Before
	public void init() {
		map = new MapModel(new Dimension(100, 100));
		int cornerSize = 50;
		for (int i = 0; i < cornerSize; i++) {
			for (int j = 0; j < cornerSize; j++) {
				map.setColor(i, j, AbstractColor.PLAYER1);
			}
		}
	}

	@Test
	public void testGetFillableSegments() {
		DrawFilledPolyRegion region = new DrawFilledPolyRegion(
				getUShapePolyLine(), AbstractColor.PLAYER1);
		List<List<Point>> fillableSegments = region.getFillableSegments(map);
		assertEquals(1, fillableSegments.size());
		List<Point> segment = fillableSegments.get(0);
		assertEquals(4, segment.size());

		assertEquals(10, segment.get(0).x);
		assertEquals(50, segment.get(0).y);

		assertEquals(10, segment.get(1).x);
		assertEquals(80, segment.get(1).y);

		assertEquals(40, segment.get(2).x);
		assertEquals(80, segment.get(2).y);

		assertEquals(40, segment.get(3).x);
		assertEquals(50, segment.get(3).y);

	}
	
	@Test
	public void testApplyUShaped() {
		DrawFilledPolyRegion region = new DrawFilledPolyRegion(
				getUShapePolyLine(), AbstractColor.PLAYER1);
		region.apply(map);
		
		assertEquals(AbstractColor.BLANK, map.getColor(75, 25));
		assertEquals(AbstractColor.BLANK, map.getColor(75, 75));
		
		assertEquals(AbstractColor.PLAYER1, map.getColor(25, 25));
		assertEquals(AbstractColor.PLAYER1, map.getColor(25, 75));

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
		
		assertEquals(AbstractColor.BLANK, map.getColor(75, 25));
		assertEquals(AbstractColor.BLANK, map.getColor(75, 75));
		
		assertEquals(AbstractColor.PLAYER1, map.getColor(25, 25));
		assertEquals(AbstractColor.PLAYER1, map.getColor(25, 75));
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
		
		assertEquals(AbstractColor.BLANK, map.getColor(75, 25));
		assertEquals(AbstractColor.BLANK, map.getColor(75, 75));
		
		assertEquals(AbstractColor.PLAYER1, map.getColor(25, 25));
		assertEquals(AbstractColor.PLAYER1, map.getColor(25, 55));
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
		
		assertEquals(AbstractColor.BLANK, map.getColor(25, 75));
		assertEquals(AbstractColor.BLANK, map.getColor(75, 75));
		assertEquals(AbstractColor.BLANK, map.getColor(80, 60));

		assertEquals(AbstractColor.PLAYER1, map.getColor(25, 25));
		assertEquals(AbstractColor.PLAYER1, map.getColor(55, 35));
		
		for(int x = 0; x <= 80; x++){
			for(int y = 20; y <= 40; y++){
				assertEquals("wrong color at (x,y) = ("+ x +","+y+")", AbstractColor.PLAYER1, map.getColor(x, y));				
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
	
	@Test
	public void testApplyThinCrack() {
		DrawFilledPolyRegion region = new DrawFilledPolyRegion(
				getThinCrackPolyLine(), AbstractColor.PLAYER1);
		region.apply(map);
		
		assertEquals(AbstractColor.BLANK, map.getColor(75, 75));
		assertEquals(AbstractColor.BLANK, map.getColor(75, 25));
		assertEquals(AbstractColor.BLANK, map.getColor(35, 50));
		
		assertEquals(AbstractColor.PLAYER1, map.getColor(35, 51));
		assertEquals(AbstractColor.PLAYER1, map.getColor(35, 49));
		assertEquals(AbstractColor.PLAYER1, map.getColor(29, 51));
		assertEquals(AbstractColor.PLAYER1, map.getColor(29, 49));		
		assertEquals(AbstractColor.PLAYER1, map.getColor(30, 51));
		assertEquals(AbstractColor.PLAYER1, map.getColor(30, 49));
		assertEquals(AbstractColor.PLAYER1, map.getColor(31, 51));
		assertEquals(AbstractColor.PLAYER1, map.getColor(31, 49));


		assertEquals(AbstractColor.PLAYER1, map.getColor(25, 75));
		assertEquals(AbstractColor.PLAYER1, map.getColor(25, 25));
		
	}

	
	private List<Point> getThinCrackPolyLine() {
		List<Point> polyLine = new ArrayList<>();
		polyLine.add(new Point(20, 20));
		polyLine.add(new Point(20, 80));
		polyLine.add(new Point(40, 80));
		polyLine.add(new Point(40, 51));
		polyLine.add(new Point(30, 51));		
		polyLine.add(new Point(30, 20));
		return polyLine;
	}
	
}

package gotox.crts.controller;

import gotox.crts.model.AbstractColor;
import gotox.crts.model.MapModel;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DrawFilledPolyRegion extends Action {

	private static final long serialVersionUID = 1L;
	private final List<Point> points;
	private final AbstractColor c;

	public DrawFilledPolyRegion(List<Point> points, AbstractColor c) {
		this.points = points;
		this.c = c;
	}

	@Override
	public void apply(MapModel map) {
		List<Point> newPoints = new ArrayList<>();
		List<List<Point>> fillableSegments = getFillableSegments(map);
		for (List<Point> fillableSegment : fillableSegments) {
			List<Point> fillableBoundary = getFillableBoundary(fillableSegment,
					map);
			newPoints.addAll(getInteriorPoints(fillableBoundary, map));
			// newPoints.addAll(fillableBoundary);

		}
		for (Point p : newPoints) {
			map.setColor(p, c);
		}
	}

	List<Point> getInteriorPoints(List<Point> closedFigure, MapModel map) {
		List<Point> ret = new ArrayList<>();
		Set<Point> boundaryPoints = new HashSet<>(closedFigure);
		Dimension d = map.getDimension();
		for (int x = 0; x < d.width; x++) {
			boolean insideFigure = false;
			boolean lastPointOnBoundary = false;
			List<Point> columnAdds = new ArrayList<>();
			for (int y = 0; y < d.height; y++) {
				Point p = new Point(x, y);
//				if(x==80){
//					System.out.println("y: " + y +
//										" contains: " + boundaryPoints.contains(p) + 
//										" prev: " + lastPointOnBoundary +
//										" insideFigure: " + insideFigure);
//				}
				if (boundaryPoints.contains(p)) {
					if (!lastPointOnBoundary){		
						if(insideFigure){
							ret.addAll(columnAdds);
						}
						insideFigure = !insideFigure;
					}
					lastPointOnBoundary = true;
				} else {
					lastPointOnBoundary = false;
				}
				if (insideFigure) {
					columnAdds.add(p);
				}
			}
		}
		ret.addAll(closedFigure);
		return ret;
	}

	List<Point> getFillableBoundary(List<Point> fillableSegments, MapModel map) {
		List<Point> ret = new ArrayList<>();
		Iterator<Point> vertices = fillableSegments.iterator();
		Point start = vertices.next();
		Point end = null;
		{
			Point p1 = start;
			Point p2;
			while (vertices.hasNext()) {
				p2 = p1;
				p1 = vertices.next();
				Iterator<Point> lineIter = lineIterator(p1, p2);
				while (lineIter.hasNext()) {
					ret.add(lineIter.next());
				}
			}
			end = p1;
		}
		ret.addAll(getBoundarySegmentConnecting(start, end, map));
		return ret;
	}

	List<List<Point>> getFillableSegments(MapModel map) {
		List<List<Point>> ret = new ArrayList<>();
		List<Point> possibleFillableSegment = null;
		Iterator<Point> iter = points.iterator();
		Point p1, p2;
		p1 = iter.next();
		boolean isExterior = map.getColor(p1).equals(AbstractColor.BLANK);
		while (iter.hasNext()) {
			if (isExterior && possibleFillableSegment != null) {
				possibleFillableSegment.add(p1);
			}
			p2 = p1;
			p1 = iter.next();
			Set<Point> boundaryCrossings = findLineBoundaryCrossings(p1, p2,
					map);
			for (Point xing : boundaryCrossings) {
				if (isExterior) {
					if (possibleFillableSegment != null) {
						possibleFillableSegment.add(xing);
						ret.add(possibleFillableSegment);
					}
					possibleFillableSegment = null;
				} else {
					possibleFillableSegment = new ArrayList<>();
					possibleFillableSegment.add(xing);
				}
				isExterior = !isExterior;
			}
		}
		return ret;
	}

	Set<Point> findLineBoundaryCrossings(Point lineEnd1, Point lineEnd2,
			MapModel map) {
		Iterator<Point> iter = lineIterator(lineEnd1, lineEnd2);
		return getBoundaryCrossings(iter, map);
	}

	Set<Point> getBoundaryCrossings(Iterator<Point> iter, MapModel map) {
		Set<Point> ret = new HashSet<>();
		Point lastPoint = iter.next();
		while (iter.hasNext()) {
			Point p = iter.next();
			AbstractColor thisColor = map.getColor(p);
			AbstractColor lastColor = map.getColor(lastPoint);
			if (thisColor.equals(AbstractColor.BLANK) && lastColor.equals(c)) {
				ret.add(p);
			} else if (thisColor.equals(c)
					&& lastColor.equals(AbstractColor.BLANK)) {
				ret.add(lastPoint);
			}
			lastPoint = p;
		}
		return ret;
	}

	Iterator<Point> lineIterator(final Point lineEnd1, final Point lineEnd2) {
		return new Iterator<Point>() {
			private int distance;
			private int i;
			private double dx, dy;

			private Iterator<Point> init() {
				distance = (int) lineEnd1.distance(lineEnd2);
				i = 0;
				dx = lineEnd2.x - lineEnd1.x;
				dy = lineEnd2.y - lineEnd1.y;
				return this;
			}

			@Override
			public boolean hasNext() {
				return i <= distance;
			}

			@Override
			public Point next() {
				Point ret = new Point((int) (lineEnd1.x + (i * dx) / distance),
						(int) (lineEnd1.y + (i * dy) / distance));
				i++;
				return ret;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

		}.init();

	}

	List<Point> getBoundarySegmentConnecting(Point p1, Point p2, MapModel map) {
		List<Iterator<Point>> boundaryIters = iteratePlayerBlobBoundary(p1, map);
		for (Iterator<Point> boundaryIter : boundaryIters) {
			List<Point> boundarySegment = new ArrayList<>();
			while (boundaryIter.hasNext()) {
				Point p = boundaryIter.next();
				boundarySegment.add(p);
				if (p.distanceSq(p2) <= 1) {
					return boundarySegment;
				}
			}
		}
		throw new IllegalStateException(
				"Could not connect points along boundary. p1: " + p1 + " p2: "
						+ p2);
	}

	List<Iterator<Point>> iteratePlayerBlobBoundary(Point startPoint,
			MapModel map) {
		Iterable<Point> validNeighbors = validNeighborsClockwise(startPoint,
				map);
		Set<Point> xings = getBoundaryCrossings(validNeighbors.iterator(), map);
		if (xings.size() > 2) {
			throw new IllegalStateException("Unexpected xings: " + xings);
		}
		List<Iterator<Point>> ret = new ArrayList<>();
		for (Point p : xings) {
			ret.add(iteratePlayerBlobBoundary(p, startPoint, map));
		}
		return ret;
	}

	Iterator<Point> iteratePlayerBlobBoundary(final Point startPoint,
			final Point secondPoint, final MapModel map) {
		return new Iterator<Point>() {
			private Point previous = secondPoint;
			private Point preprevious = startPoint;

			@Override
			public boolean hasNext() {
				return peakNext() != null;
			}

			@Override
			public Point next() {
				Point next = peakNext();
				preprevious = previous;
				previous = next;
				return next;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

			private Point peakNext() {
				Iterable<Point> validNeighbors = validNeighborsClockwise(
						previous, map);
				Set<Point> xings = getBoundaryCrossings(
						validNeighbors.iterator(), map);
				xings.remove(preprevious);
				if (xings.size() < 1) {
					return null;
				} else if (xings.size() == 1) {
					return xings.iterator().next();
				} else {
					throw new IllegalStateException("Unexpected xings: "
							+ xings);
				}
			}
		};
	}

	// If all neighbors of p are valid the first and last point in iterator will
	// be same. Otherwise not.
	// Neighbors that are invalid points are not included
	Iterable<Point> validNeighborsClockwise(Point p, MapModel m) {
		Point[] neighbors = neighborsClockwise(p);
		List<Point> ret = new ArrayList<Point>();
		List<Point> suffix = null;
		boolean allNeighborsValid = true;
		for (Point neighbor : neighbors) {
			if (isValidPoint(neighbor, m)) {
				ret.add(neighbor);
			} else {
				if (allNeighborsValid == true) {
					// Make sure the iterator doesn't jump between non-adjacent
					// points
					// if some neighbors are invalid.
					suffix = ret;
					ret = new ArrayList<Point>();
				}
				allNeighborsValid = false;
			}
		}
		if (suffix != null)
			ret.addAll(suffix);
		if (allNeighborsValid) {
			ret.add(ret.get(0));
		}
		return ret;
	}

	Point[] neighborsClockwise(Point p) {
		int x = p.x;
		int y = p.y;
		Point[] neighbors = new Point[] { new Point(x - 1, y + 1),
				new Point(x, y + 1), new Point(x + 1, y + 1),
				new Point(x + 1, y), new Point(x + 1, y - 1),
				new Point(x, y - 1), new Point(x - 1, y - 1),
				new Point(x - 1, y), };
		return neighbors;
	}

	boolean isValidPoint(Point p, MapModel m) {
		Dimension d = m.getDimension();
		return ((0 <= p.x) && (p.x < d.width) && (0 < p.y) && (p.y < d.height));
	}

}

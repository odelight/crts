package gotox.crts.controller;

import gotox.crts.model.AbstractColor;
import gotox.crts.model.Line;
import gotox.crts.model.MapModel;
import gotox.crts.model.Polygon;
import gotox.crts.model.Polyline;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DrawFilledPolyRegion extends Action {

	private static final long serialVersionUID = 1L;
	private final List<Point> points;
	private final AbstractColor drawColor;

	public DrawFilledPolyRegion(List<Point> points, AbstractColor c) {
		this.points = points;
		this.drawColor = c;
	}

	@Override
	public void apply(MapModel map) {
		Polyline draw = new Polyline(points, map);
		Polygon blob = map.getBlob(drawColor);
		Point xingLeading, xingTrailing;
		for(Line l : draw.lineIterable()){
			Point xing = blob.intersectsLine(l);
			if(xing != null){
				
			}
		}
//		if (points.size() < 2) {
//			return;
//		}
//		List<Point> newPoints = new ArrayList<>();
//		List<List<Point>> fillableSegments = getFillableSegments(map);
//
//		List<Point> fillableBoundary = null;
//		for (List<Point> fillableSegment : fillableSegments) {
//			
//			System.out.println("fillable seg: " + fillableSegment);
//			
//			
//			fillableBoundary = getFillableBoundary(fillableSegment, map);
////			widenObject(fillableBoundary, map);
//			for(Point p : fillableBoundary){
//				AbstractColor c = map.getColor(p);
//				if(!(c.equals(thisPlayerColor) || c.equals(AbstractColor.BLANK))){
//					return;
//				}
//			}
//			newPoints.addAll(getInteriorPoints(fillableBoundary, map));
//		}
//		if(newPoints.size() < 500000000){
//		for (Point p : newPoints) {
//			map.setColor(p, thisPlayerColor);
//		}
//		}
//		 for (Point p : fillableBoundary) {
//		 map.setColor(p, AbstractColor.PLAYER2);
//		 }
	}
	
//	private void widenObject(List<Point> object, MapModel map){
//		Set<Point> toBeAdded = new HashSet<>();
//		for(Point p : object){
//			toBeAdded.addAll(validNeighborsClockwise(p, map));
//		}
//		object.addAll(toBeAdded);
//	}

	List<Point> getInteriorPoints(List<Point> closedFigure, MapModel map) {
		List<Point> ret = new ArrayList<>();
		Set<Point> boundaryPoints = new HashSet<>(closedFigure);
		Dimension d = map.getDimension();
		for (int x = 0; x < d.width; x++) {
			boolean insideFigure = false;
			// boolean lastPointOnBoundary = false;

			boolean leftEntered = false;
			boolean rightEntered = false;
			boolean crossed = false;

			List<Point> columnAdds = new ArrayList<>();
			for (int y = 0; y < d.height; y++) {
				if (isRightEntered(x, y, map, boundaryPoints)) {
					rightEntered = true;
				}
				if (isLeftEntered(x, y, map, boundaryPoints)) {
					leftEntered = true;
				}
				
				Point upOne = new Point(x, y - 1);
				Point p = new Point(x, y);
				if (boundaryPoints.contains(upOne) && !boundaryPoints.contains(p)) {
					crossed = true;
					if(x==199){
						System.out.println("y " + y + " x " + x +" re " + rightEntered + " le " + leftEntered + " ce " + crossed + " inside figure " + insideFigure );
					}
				}
				
				
				if (crossed && leftEntered && rightEntered) {
					if (insideFigure) {
						ret.addAll(columnAdds);
						columnAdds = new ArrayList<>();
					}
					insideFigure = !insideFigure;
					crossed = false;
					if (boundaryPoints.contains(p)) {
						leftEntered = false;
						rightEntered = false;
					} else {
						leftEntered = isLeftEntered(x, y, map, boundaryPoints);
						rightEntered = isRightEntered(x, y, map, boundaryPoints);
					}
				}
				
				if(x==199){
					System.out.println("y " + y + " x " + x +" re " + rightEntered + " le " + leftEntered + " ce " + crossed + " inside figure " + insideFigure );
				}
				
				if (insideFigure) {
					columnAdds.add(p);
				}
			}
		}
		ret.addAll(closedFigure);
		return ret;
	}

	private boolean isRightEntered(int x, int y, MapModel map,
			Set<Point> boundaryPoints) {
		boolean rightEntered = false;
		if (x <= map.getDimension().width) {
			Point rightPoint = new Point(x + 1, y);
			if (boundaryPoints.contains(rightPoint)) {
				rightEntered = true;
			}
		} else {
			rightEntered = true;
		}
		return rightEntered;
	}

	private boolean isLeftEntered(int x, int y, MapModel map,
			Set<Point> boundaryPoints) {
		boolean leftEntered = false;
		if (x > 0) {
			Point leftPoint = new Point(x - 1, y);
			if (boundaryPoints.contains(leftPoint)) {
				leftEntered = true;
			}
		} else {
			leftEntered = true;
		}
		return leftEntered;
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
			if (thisColor.equals(AbstractColor.BLANK) && lastColor.equals(drawColor)) {
				ret.add(p);
			} else if (thisColor.equals(drawColor)
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
			private Set<Point> iteratedPoints = new HashSet<>();

			@Override
			public boolean hasNext() {
				return peakNext() != null;
			}

			@Override
			public Point next() {
				Point next = peakNext();
				preprevious = previous;
				previous = next;
				iteratedPoints.add(next);
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
				}
				Iterator<Point> iter = xings.iterator();
				if (xings.size() == 1) {
					return iter.next();
				} else {
					Point maxDistPoint = null;
					double maxDist = 0;
					while (iter.hasNext()) {
						Point p = iter.next();
						while (iteratedPoints.contains(p)) {
							if (iter.hasNext()) {
								p = iter.next();
							} else {
								throw new IllegalStateException("previous: "
										+ previous + "preprevious: "
										+ preprevious);
							}
						}
						if (p.distanceSq(preprevious) > maxDist) {
							maxDistPoint = p;
							maxDist = p.distanceSq(preprevious);
						}
					}
					return maxDistPoint;
				}
			}
		};
	}

	// If all neighbors of p are valid the first and last point in iterator will
	// be same. Otherwise not.
	// Neighbors that are invalid points are not included
	Collection<Point> validNeighborsClockwise(Point p, MapModel m) {
		Point[] neighbors = neighborsClockwise(p);
		List<Point> ret = new ArrayList<Point>();
		List<Point> suffix = null;
		boolean allNeighborsValid = true;
		for (Point neighbor : neighbors) {
			if (m.isValidPoint(neighbor)) {
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



}

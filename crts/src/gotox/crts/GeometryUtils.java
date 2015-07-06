package gotox.crts;

import gotox.crts.model.CrtsLine;
import gotox.crts.model.CrtsPolyline;

import java.awt.Point;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class GeometryUtils {

	public static boolean selfIntersects(CrtsPolyline polyLine) {
		int i = 0;
		int j = 0;
		for (CrtsLine l : polyLine.lineIterable()) {
			j = 0;
			for (CrtsLine s : polyLine.lineIterable()) {
				if (j > i + 1) {
					if (lineIntersection(l.getStart(), l.getEnd(),
							s.getStart(), s.getEnd()) != null) {
						return true;

					}
				}
				j++;
			}
			i++;
		}
		return false;
	}

	public static Iterator<Point> lineIterator(final Point lineEnd1,
			final Point lineEnd2) {
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

	public static Intersection lineIntersection(Point p1, Point p2, Point p3,
			Point p4) {
		double tDenominator = tDenominator(p1, p2, p3, p4);
		if (tDenominator == 0) {// lines are parallel
			return GeometryUtils.lineIntersectionColinear(p1, p2, p3, p4);
		} else {
			double tNumerator = tNumerator(p1, p3, p4);
			double t = tNumerator / tDenominator;
			if (t > 1 || t < 0) {
				return null;
			} else {
				double tPrime = tPrimeNumerator(p1, p2, p3) / tDenominator;
				if (tPrime > 1 || tPrime < 0) {
					return null;
				}
			}
			int intersectX = (int) ((p2.x - p1.x) * t + p1.x);
			int intersectY = (int) ((p2.y - p1.y) * t + p1.y);
			return new Intersection(new Point(intersectX, intersectY));
		}
	}

	private static int tNumerator(Point p1, Point p3, Point p4) {
		return (p4.x - p3.x) * (p3.y - p1.y) - (p3.x - p1.x) * (p4.y - p3.y);
	}

	private static int tPrimeNumerator(Point p1, Point p2, Point p3) {
		return (p2.x - p1.x) * (p3.y - p1.y) - (p3.x - p1.x) * (p2.y - p1.y);
	}

	private static int tDenominator(Point p1, Point p2, Point p3, Point p4) {
		return (p4.x - p3.x) * (p2.y - p1.y) - (p4.y - p3.y) * (p2.x - p1.x);
	}

	private static Intersection lineIntersectionColinear(Point p1, Point p2,
			Point p3, Point p4) {
		// check if the lines are colinear by checking if segment between the
		// lines starts is parallel to the lines.
		if (((p1.y - p3.y) * (p2.x - p1.x)) == ((p2.y - p1.y) * (p1.x - p3.x))) {
			// the segments lie on the same line. Just need to check for
			// overlap.
			CrtsLine l1 = new CrtsLine(p1, p2);
			CrtsLine l2 = new CrtsLine(p3, p4);
			if((l1.getStart().equals(l2.getStart()) && !(l1.contains(l2.getEnd()))) ||
				(l1.getStart().equals(l2.getEnd()) && !(l1.contains(l2.getStart())))){
				return new Intersection(l1.getStart());
			} else 	if((l1.getEnd().equals(l2.getStart()) && !(l1.contains(l2.getEnd()))) ||
					(l1.getEnd().equals(l2.getEnd()) && !(l1.contains(l2.getStart())))){
					return new Intersection(l1.getEnd());
			} else if (l1.contains(p3) || l1.contains(p4) || l2.contains(p1)
					|| l2.contains(p2)) {

				List<Point> points = Arrays
						.asList(new Point[] { p1, p2, p3, p4 });
				double distance = 0;
				Point end1 = null;
				Point end2 = null;
				for (Point p : points) {
					for (Point q : points) {
						double newDistance = p.distance(q);
						if (newDistance > distance) {
							end1 = p;
							end2 = q;
							distance = newDistance;
						}
					}
				}
				Point middle1 = null;
				Point middle2 = null;
				for (Point p : points) {
					if (!(p.equals(end1) || p.equals(end2))) {
						if (middle1 == null) {
							middle1 = p;
						} else {
							middle2 = p;
						}
					}
				}
				if(middle1 == null && middle2 == null){
					return new Intersection(end1, end2);
				}
				return new Intersection(middle1, middle2);
			}
		}
		return null;
	}

	private static boolean inInterval(double intStart, double intEnd,
			double point) {
		return (((point < intEnd) && (point > intStart)) || ((point < intEnd) && (point > intStart)));
	}

}

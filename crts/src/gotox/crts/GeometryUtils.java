package gotox.crts;

import gotox.crts.model.Line;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class GeometryUtils {

	public static List<Point> lineListToPointList(List<Line> lineList) {
		if (lineList.size() == 0) {
			return Collections.EMPTY_LIST;
		} else {
			Iterator<Line> iter = lineList.iterator();
			List<Point> ret = new ArrayList<Point>();
			ret.add(iter.next().getStart());
			while (iter.hasNext()) {
				ret.add(iter.next().getEnd());
			}
			return ret;
		}
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

	public static Point lineIntersection(Point lineAStart, Point lineAEnd,
			Point lineBStart, Point lineBEnd) {
		double tDenominator = tDenominator(lineAStart, lineAEnd, lineBStart, lineBEnd);
		if (tDenominator == 0) {// lines are parallel
			return GeometryUtils.lineIntersectionColinear(lineAStart, lineAEnd,
					lineBStart, lineBEnd);
		} else {
			double tNumerator = tNumerator(lineAStart, lineBStart, lineBEnd);
			double t = tNumerator / tDenominator;
			if (t > 1 || t < 0) {
				return null;
			} else {
				double tPrime = tNumerator(lineBStart, lineAStart, lineAEnd) / tDenominator;
				if (tPrime > 1 || tPrime < 0){
					return null;					
				}
			}
			double tPrime = tNumerator(lineAStart, lineBStart, lineBEnd);
			int intersectX = (int) ((lineAEnd.x - lineAStart.x) * t + lineAStart.x);
			int intersectY = (int) ((lineAEnd.y - lineAStart.y) * t + lineAStart.y);
			return new Point(intersectX, intersectY);
		}
	}

	private static int tNumerator(Point lineAStart, Point lineBStart,
			Point lineBEnd) {
		return (lineBEnd.x - lineBStart.x)
				* (lineBStart.y - lineAStart.y) - (lineBEnd.y - lineBStart.y)
				* (lineAStart.x - lineBStart.x);
	}

	private static int tDenominator(Point lineAStart, Point lineAEnd,
			Point lineBStart, Point lineBEnd) {
		return (lineBEnd.x - lineBStart.x)
				* (lineAEnd.y - lineAStart.y) - (lineBEnd.y - lineBStart.y)
				* (lineAEnd.x - lineAStart.x);
	}
	
	private static Point lineIntersectionColinear(Point lineAStart,
			Point lineAEnd, Point lineBStart, Point lineBEnd) {
		// check if the lines are colinear by checking if segment between the
		// lines starts is parallel to the lines.
		if (((lineAStart.y - lineBStart.y) * (lineAEnd.x - lineAStart.x)) == ((lineAEnd.y - lineAStart.y) * (lineAStart.x - lineBStart.x))) {
			// the segments lie on the same line. Just need to check for
			// overlap.
			if ((lineAEnd.x == lineAStart.x) && !(lineAEnd.y == lineAStart.y)) {
				if (GeometryUtils.inInterval(lineBStart.y, lineBEnd.y,
						lineAStart.y)) {
					return lineAStart;
				} else if (GeometryUtils.inInterval(lineBStart.y, lineBEnd.y,
						lineAEnd.y)) {
					return lineAEnd;
				} else if (GeometryUtils.inInterval(lineAStart.y, lineAEnd.y,
						lineBStart.y)) {
					return lineBStart;
				} else if (GeometryUtils.inInterval(lineAStart.y, lineAEnd.y,
						lineBEnd.y)) {
					return lineBEnd;
				}
				return null;
			} else {
				if (GeometryUtils.inInterval(lineBStart.x, lineBEnd.x,
						lineAStart.x)) {
					return lineAStart;
				} else if (GeometryUtils.inInterval(lineBStart.x, lineBEnd.x,
						lineAEnd.x)) {
					return lineAEnd;
				} else if (GeometryUtils.inInterval(lineAStart.x, lineAEnd.x,
						lineBStart.x)) {
					return lineBStart;
				} else if (GeometryUtils.inInterval(lineAStart.x, lineAEnd.x,
						lineBEnd.x)) {
					return lineBEnd;
				}
				return null;
			}
		}
		return null;
	}

	private static boolean inInterval(double intStart, double intEnd,
			double point) {
		return (((point < intEnd) && (point > intStart)) || ((point < intEnd) && (point > intStart)));
	}

}

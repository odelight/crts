package gotox.crts.model;

import java.awt.Point;

public class CrtsLine {
	private final Point start;
	private final Point end;

	public CrtsLine(Point start, Point end) {
		this.start = start;
		this.end = end;
	}

	public Point getEnd() {
		return end;
	}

	public Point getStart() {
		return start;
	}

	public boolean contains(Point p) {
		int dx = end.x - start.x;
		int dy = end.y - start.y;
		if (Math.abs(dx) > Math.abs(dy)) {
			double t = ((double)(p.x - start.x)) / dx;
			double predictedY = t * dy + start.y;
			if (Math.abs(predictedY - p.y) <= 1) {
				if ((p.y >= start.y && p.y <= end.y) || (p.y <= start.y
						&& p.y >= end.y)) {
					return true;
				}
			}
		} else {
			if (dy == 0) {
				return (p.equals(start));
			}
			double t = ((double)(p.y - start.y)) / dy;
			double predictedX = t * dx + start.x;
			if (Math.abs(predictedX - p.x) <= 1) {
				if ((p.x >= start.x && p.x <= end.x) || (p.x <= start.x
						&& p.x >= end.x)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Assumes p1, and p2 lie on this line. Returns true if p1 is closer to
	 * start then p2.
	 * 
	 * @return
	 */
	public boolean checkOrdered(Point p1, Point p2) {
		return start.distanceSq(p1) <= start.distanceSq(p2);
	}

}

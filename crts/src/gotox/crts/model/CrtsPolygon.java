package gotox.crts.model;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrtsPolygon extends Polyline {

	private final Set<Point> interior;
	private final Polygon innerPoly;

	public CrtsPolygon(List<Point> vertices, MapModel map) {
		super(vertices, map);
		Point firstPoint = vertices.get(0);
		Point lastPoint = vertices.get(vertices.size() - 1);
		if (!firstPoint.equals(lastPoint)) {
			throw new IllegalArgumentException();
		}
		int n = vertices.size();
		int[] xpoints = new int[n];
		int[] ypoints = new int[n];
		for (int i = 0; i < n; i++) {
			Point vert = vertices.get(i);
			xpoints[i] = vert.x;
			ypoints[i] = vert.y;
		}
		innerPoly = new Polygon(xpoints, ypoints, n);
		interior = Collections.unmodifiableSet(getInterior(innerPoly));
	}

	public boolean contains(Point p) {
		return interior.contains(p);
	}

	public Iterable<Point> getInterior() {
		return interior;
	}

	private static Set<Point> getInterior(Polygon innerPoly) {
		Set<Point> ret = new HashSet<>();
		Rectangle r = innerPoly.getBounds();
		for (int x = r.x; x <= r.x + r.width; x++) {
			for (int y = r.y; y <= r.y + r.height; y++) {
				Point p = new Point(x, y);
				if (innerPoly.contains(p)) {
					ret.add(p);
				}
			}
		}
		return ret;
	}
}

package gotox.crts.controller;

import gotox.crts.GeometryUtils;
import gotox.crts.Intersection;
import gotox.crts.model.AbstractColor;
import gotox.crts.model.CrtsLine;
import gotox.crts.model.CrtsPolygon;
import gotox.crts.model.MapModel;
import gotox.crts.model.CrtsPolyline;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DrawFilledPolyRegion extends Action {

	private static final long serialVersionUID = 1L;
	private final List<Point> points;
	private final AbstractColor drawColor;

	public DrawFilledPolyRegion(List<Point> points, AbstractColor c) {
		if (!points.isEmpty()) {
			Iterator<Point> pointIter = points.iterator();
			Point lastPoint = pointIter.next();
			while (pointIter.hasNext()) {
				Point p = pointIter.next();
				if (p.equals(lastPoint)) {
					pointIter.remove();
				} else {
					lastPoint = p;
				}
			}
		}
		this.points = points;
		this.drawColor = c;
	}

	@Override
	public void apply(MapModel map) {
		if (points.size() < 2) {
			System.out.println("Cannot draw single point.");
			return;
		}
		CrtsPolyline draw = new CrtsPolyline(points, map);
		CrtsPolygon blob = map.getBlob(drawColor);
		boolean inside = blob.contains(points.get(0));
		List<Point> viablePolyLine = new ArrayList<>();
		for (CrtsLine l : draw.lineIterable()) {
			if (!inside) {
				viablePolyLine.add(l.getStart());
			}
			List<Intersection> xings = blob.intersectsLine(l);
			for (Intersection xing : xings) {
				if (xing.isSingleIntersection()) {
					viablePolyLine.add(xing.firstPoint());
				} else {
					if (l.getStart().distanceSq(xing.firstPoint()) < l
							.getStart().distanceSq(xing.secondPoint())) {
						viablePolyLine.add(xing.firstPoint());
						viablePolyLine.add(xing.secondPoint());
					} else {
						viablePolyLine.add(xing.secondPoint());
						viablePolyLine.add(xing.firstPoint());
					}

				}
				if (!inside) {
					if (!GeometryUtils.selfIntersects(new CrtsPolyline(
							viablePolyLine, map))) {
						System.out.println("Attempting to draw viable figure");
						blob = annex(blob, viablePolyLine, map);
					} else {
						System.out.println("Cannot draw self intersecting figure");
					}
					viablePolyLine = new ArrayList<>();
				}
				inside = !inside;
			}
		}
		map.putBlob(drawColor, blob);
	}

	private CrtsPolygon annex(CrtsPolygon blob, List<Point> addition,
			MapModel map) {
		List<Point> newPolyPoints = new ArrayList<>();
		Point additionStart = addition.get(0);
		Point additionEnd = addition.get(addition.size() - 1);
		boolean preAddition = true;
		boolean inAddition = false;
		for (CrtsLine l : blob.lineIterable()) {
			if (preAddition) {
				newPolyPoints.add(l.getStart());
			} else if (inAddition) {
				// throw point away.
			} else {
				newPolyPoints.add(l.getEnd());
			}
			if ((l.contains(additionEnd) && (preAddition || inAddition))
					|| (l.contains(additionStart) && preAddition)) {
				if (inAddition) {
					inAddition = false;
					newPolyPoints.add(l.getEnd());
				} else {
					if (l.contains(additionEnd)) {
						if ((!l.contains(additionStart))
								|| (l.checkOrdered(additionEnd, additionStart))) {
							Collections.reverse(addition);
							Point temp = additionEnd;
							additionEnd = additionStart;
							additionStart = temp;
						}
					}
					newPolyPoints.addAll(addition);
					preAddition = false;
					if (l.contains(additionEnd) && l.contains(additionStart)) {
						newPolyPoints.add(l.getEnd());
					} else {
						inAddition = true;
					}
				}
			}
		}
		return new CrtsPolygon(newPolyPoints, map);
	}
}

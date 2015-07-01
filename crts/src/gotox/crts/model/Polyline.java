package gotox.crts.model;

import gotox.crts.GeometryUtils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Polyline {
	private final List<Point> vertices;
	
	public Polyline(List<Point> vertices, MapModel map) {
		if (vertices.size() < 2) {
			throw new IllegalArgumentException();
		}
		for (Point vert : vertices) {
			if (!map.isValidPoint(vert)) {
				throw new IllegalArgumentException();
			}
		}
		this.vertices = Collections.unmodifiableList(vertices);
	}
	
	public List<Point> intersectsLine(Line l) {
		return intersectsLine(l.getStart(), l.getEnd());
	}

	public List<Point> intersectsLine(Point lineStart, Point lineEnd) {
		Iterator<Point> vertsIterator = vertices.iterator();
		Point edgeStart;
		Point edgeEnd = vertsIterator.next();
		List<Point> ret = new ArrayList<Point>();
		while (vertsIterator.hasNext()) {
			edgeStart = edgeEnd;
			edgeEnd = vertsIterator.next();
			Point intersection = GeometryUtils.lineIntersection(lineStart, lineEnd,
					edgeStart, edgeEnd);
			if (intersection != null) {
				ret.add(intersection);
			}
		}
		return ret;
	}

	
	public Iterable<Point> pointIterable(){
		return vertices;
	}
	
	public Iterable<Line> lineIterable(){
		return new Iterable<Line>(){
			@Override
			public Iterator<Line> iterator() {
				return new Iterator<Line>(){
					private final Iterator<Point> pointIter = pointIterable().iterator();
					private Point leadingPoint = pointIter.next();
					private Point trailingPoint;
					@Override
					public boolean hasNext() {
						return pointIter.hasNext();
					}

					@Override
					public Line next() {
						trailingPoint = leadingPoint;
						leadingPoint = pointIter.next();
						return new Line(trailingPoint, leadingPoint);
					}

					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
					
				};
			}
			
		};
	}
	
}

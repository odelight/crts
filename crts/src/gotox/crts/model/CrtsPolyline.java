package gotox.crts.model;

import gotox.crts.GeometryUtils;
import gotox.crts.Intersection;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CrtsPolyline {
	private final List<Point> vertices;
	
	public CrtsPolyline(List<Point> vertices, MapModel map) {
		if (vertices.size() < 2) {
			throw new IllegalArgumentException();
		}
		for (Point vert : vertices) {
			if (!map.isValidPoint(vert)) {
				throw new IllegalArgumentException("Illegal vert:" + vert);
			}
		}
		this.vertices = Collections.unmodifiableList(vertices);
	}

	public CrtsPolyline(List<Point> vertices, ImmutableMap map) {
		if (vertices.size() < 2) {
			throw new IllegalArgumentException();
		}
		for (Point vert : vertices) {
			if (!map.isValidPoint(vert)) {
				throw new IllegalArgumentException("Illegal vert:" + vert);
			}
		}
		this.vertices = Collections.unmodifiableList(vertices);
	}
	
	public List<Point> getPoints(){
		return vertices;
	}
	
	public List<Intersection> intersectsLine(CrtsLine l) {
		return intersectsLine(l.getStart(), l.getEnd());
	}

	public List<Intersection> intersectsLine(Point lineStart, Point lineEnd) {
		Iterator<Point> vertsIterator = vertices.iterator();
		Point edgeStart;
		Point edgeEnd = vertsIterator.next();
		List<Intersection> ret = new ArrayList<>();
		while (vertsIterator.hasNext()) {
			edgeStart = edgeEnd;
			edgeEnd = vertsIterator.next();
			Intersection intersection = GeometryUtils.lineIntersection(lineStart, lineEnd,
					edgeStart, edgeEnd);
			if (intersection != null && !ret.contains(intersection)) {
				ret.add(intersection);
			}
		}
		return ret;
	}

	
	public Iterable<Point> pointIterable(){
		return vertices;
	}
	
	public Iterable<CrtsLine> lineIterable(){
		return new Iterable<CrtsLine>(){
			@Override
			public Iterator<CrtsLine> iterator() {
				return new Iterator<CrtsLine>(){
					private final Iterator<Point> pointIter = pointIterable().iterator();
					private Point leadingPoint = pointIter.next();
					private Point trailingPoint;
					@Override
					public boolean hasNext() {
						return pointIter.hasNext();
					}

					@Override
					public CrtsLine next() {
						trailingPoint = leadingPoint;
						leadingPoint = pointIter.next();
						return new CrtsLine(trailingPoint, leadingPoint);
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

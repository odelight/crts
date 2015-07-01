package gotox.crts.model;

import gotox.crts.GeometryUtils;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Polygon extends Polyline {
	
	private final Set<Point> interior;
	
	public Polygon(List<Point> vertices, MapModel map){
		super(vertices, map);
		Point firstPoint = vertices.get(0);
		Point lastPoint = vertices.get(vertices.size() - 1);
		if(!firstPoint.equals(lastPoint)){
			throw new IllegalArgumentException();
		}
		interior = Collections.unmodifiableSet(getInterior(this, map));
	}
	
	public boolean isInside(Point p){
		return interior.contains(p);
	}
	
	public Iterable<Point> getInterior(){
		return interior;
	}
	
	private static Set<Point> getInterior(Polyline sup, MapModel map) {
		Set<Point> ret = new HashSet<>();
		Dimension d = map.getDimension();
		for (int x = 0; x < d.width; x++) {
			if(x == 400){
				System.out.println("");
			}
			Line fillLine = new Line(new Point(x,0),new Point(x,d.height - 1));
			List<Point> intersections = sup.intersectsLine(fillLine);
			if(intersections.size() >= 2){
				Iterator<Point> iter = intersections.iterator();
				Point upperIntersection, lowerIntersection;
				upperIntersection = iter.next();
				boolean interior = false;
				while(iter.hasNext()){
					lowerIntersection = upperIntersection;
					upperIntersection = iter.next();
					interior = !interior;
					if(interior){
						Iterator<Point> line = GeometryUtils.lineIterator(lowerIntersection, upperIntersection);
						while(line.hasNext()){
							ret.add(line.next());
						}
					}
				}
			}
		}
		return ret;
	}
	

}

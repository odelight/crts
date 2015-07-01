package gotox.crts.model;

import java.awt.Point;

public class Line {
	private final Point start;
	private final Point end;
	public Line(Point start, Point end){
		this.start = start;
		this.end = end;
	}
	public Point getEnd() {
		return end;
	}
	public Point getStart() {
		return start;
	}
	
	public boolean contains(Point p){
		int dx = end.x - start.x;
		int dy = end.y - start.y;
		if(Math.abs(dx) > Math.abs(dy)){
			double t = (p.x - start.x)/dx;
			double predictedY = t*dy + start.y;
			return (Math.abs(predictedY - p.y) <= 1);
		} else {
			if(dy == 0){
				return(p.equals(start));
			}
			double t = (p.y - start.y)/dy;
			double predictedX = t*dx + start.x;
			return (Math.abs(predictedX - p.x) <= 1);
		}
		
	}

}

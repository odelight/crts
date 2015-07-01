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

}

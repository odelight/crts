package gotox.crts.controller;

import java.awt.Point;

import gotox.crts.model.AbstractColor;
import gotox.crts.model.MapModel;

public class DrawLine implements Action{

	private static final long serialVersionUID = 1L;
	private final Point p1, p2;
	private final AbstractColor c;
	
	public DrawLine(Point p1, Point p2, AbstractColor c){
		this.p1 = p1;
		this.p2 = p2;
		this.c = c;
	}

	@Override
	public void apply(MapModel model) {
			double distance = (int) p1.distance(p2);
			if (distance == 0) {
				model.setColor(p1.x, p1.y, c);
			} else {
				double dx = p2.x - p1.x;
				double dy = p2.y - p1.y;
				for (int i = 0; i <= distance; i++) {
					model.setColor((int) (p1.x + (i * dx) / distance),
							(int) (p1.y + (i * dy) / distance), c);
				}
			}
	}

}

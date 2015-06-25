package gotox.crts.controller;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;

import gotox.crts.model.AbstractColor;
import gotox.crts.model.MapModel;

public class DrawPolyLine extends Action {

	private static final long serialVersionUID = 1L;
	private final List<Point> points;
	private final AbstractColor c;

	public DrawPolyLine(List<Point> points, AbstractColor c) {
		this.points = points;
		this.c = c;
	}

	@Override
	public void apply(MapModel model) {
		Point p1, p2;
		Iterator<Point> iter = points.iterator();
		p1 = iter.next();
		while (iter.hasNext()) {
			p2 = p1;
			p1 = iter.next();
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

	public void validatePoint(Point p, MapModel m) {
		Dimension d = m.getDimension();
		if (p.x < 0) {
			p.x = 0;
		} else if (p.x >= d.width) {
			p.x = d.width - 1;
		}

		if (p.y < 0) {
			p.y = 0;
		} else if (p.y >= d.height) {
			p.y = d.height - 1;
		}
	}

}

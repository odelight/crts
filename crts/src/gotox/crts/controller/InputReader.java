package gotox.crts.controller;

import gotox.crts.model.MapModel;
import gotox.crts.view.RedrawListener;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class InputReader implements MouseMotionListener, MouseListener {

	private Point lastMousePosition;
	private final MapModel model;
	private final Player player;
	private List<RedrawListener> redrawListeners = new ArrayList<>();

	public InputReader(MapModel m, Player p) {
		model = m;
		player = p;
	}

	public void registerRedrawListener(RedrawListener l) {
		redrawListeners.add(l);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = e.getPoint();
		if (lastMousePosition != null)
			interpolateLine(p, lastMousePosition);
		for (RedrawListener l : redrawListeners)
			l.requestRedraw();
		lastMousePosition = p;
	}

	public void interpolateLine(Point p1, Point p2) {
		double distance = (int) p1.distance(p2);
		if (distance == 0) {
			model.setColor(p1.x, p1.y, player.getColor());
		} else {
			double dx = p2.x - p1.x;
			double dy = p2.y - p1.y;
			for (int i = 0; i <= distance; i++) {
				model.setColor((int) (p1.x + (i * dx) / distance),
						(int) (p1.y + (i * dy) / distance), player.getColor());
			}
		}
	}

	/**
	 * does nothing
	 * 
	 * @param arg0
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mouseDragged(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		lastMousePosition = null;
	}
}

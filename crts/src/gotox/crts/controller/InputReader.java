package gotox.crts.controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class InputReader implements MouseMotionListener, MouseListener {

	private Point lastMousePosition;
	private final Player player;
	private List<Action> actionList = new ArrayList<>();
	private ReentrantLock actionListLock;

	public InputReader(Player p) {
		player = p;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = e.getPoint();
		if (lastMousePosition != null) {
			DrawLine d = new DrawLine(p, lastMousePosition, player.getColor());
			queueAction(d);
		}
		lastMousePosition = p;
	}

	public List<Action> getActions() {
		actionListLock.lock();
		List<Action> ret = actionList;
		actionList = new ArrayList<>();
		actionListLock.unlock();
		return ret;
	}

	private void queueAction(Action a) {
		actionListLock.lock();
		actionList.add(a);
		actionListLock.unlock();
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

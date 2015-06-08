package gotox.crts.controller;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class InputReader {

	private Point lastMousePosition;
	private final Player player;
	private List<Action> actionList = new ArrayList<>();
	private ReentrantLock actionListLock = new ReentrantLock();

	private final MouseMotionAdapter mouseMotionAdapter;
	private final MouseAdapter mouseAdapter;

	public InputReader(Player p) {
		player = p;
		mouseMotionAdapter = new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point p = e.getPoint();
				if (lastMousePosition != null) {
					DrawLine d = new DrawLine(p, lastMousePosition,
							player.getColor());
					queueAction(d);
				}
				lastMousePosition = p;
			}
		};
		mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				lastMousePosition = null;
			}
		};
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

	public MouseAdapter asMouseAdapter() {
		return mouseAdapter;
	}

	public MouseMotionAdapter asMouseMotionAdapter() {
		return mouseMotionAdapter;
	}
}

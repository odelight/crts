package gotox.crts.controller;

import gotox.crts.view.MapDisplay;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class InputReader {

	private List<Point> currentDrawPoints = new ArrayList<>();
	private final Player player;
	private List<Action> actionList = new ArrayList<>();
	private ReentrantLock actionListLock = new ReentrantLock();

	private final MouseMotionAdapter mouseMotionAdapter;
	private final MouseAdapter mouseAdapter;

	public InputReader(Player p, final MapDisplay display) {
		player = p;
		mouseMotionAdapter = new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point p = e.getPoint();
				currentDrawPoints.add(p);
				display.setTrail(currentDrawPoints);
			}
		};
		mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				DrawFilledPolyRegion d = new DrawFilledPolyRegion(
						currentDrawPoints, player.getColor());
				currentDrawPoints = new ArrayList<>();
				display.clearTrails();
				queueAction(d);
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

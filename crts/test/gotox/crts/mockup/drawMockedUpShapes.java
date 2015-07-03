package gotox.crts.mockup;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gotox.crts.Game;
import gotox.crts.controller.Action;
import gotox.crts.controller.DrawFilledPolyRegion;
import gotox.crts.controller.Player;
import gotox.crts.model.AbstractColor;
import gotox.crts.networking.Network;

public class drawMockedUpShapes {
	
	public static void main(String[] args) {
		drawMockedUpShapes me = new drawMockedUpShapes();
		me.draw();
	}
	
	public drawMockedUpShapes() {
	}

	public void draw() {
		List<Action> actions = Arrays
				.asList(new Action[] { getBackwardsCAction(), getSimpleSquareAction() });
		MockInputReader mir = new MockInputReader(new Player(
				AbstractColor.PLAYER1), actions);
		Game g = new Game(new Network(), AbstractColor.PLAYER1, mir);
		g.setVisible(true);
		g.start();
	}

	public Action getSimpleSquareAction() {
		return new DrawFilledPolyRegion(simpleSquarePolyLine(),
		AbstractColor.PLAYER1);	
	}

	public List<Point> simpleSquarePolyLine() {
		List<Point> ret = new ArrayList<>();
		ret.add(new Point(100, 100));
		ret.add(new Point(500, 100));
		ret.add(new Point(500, 500));
		ret.add(new Point(100, 500));
		ret.add(new Point(100, 100));
		return ret;
		
	}
	
	public Action getBackwardsCAction() {
		return new DrawFilledPolyRegion(backwardsCPolyLine(),
		AbstractColor.PLAYER1);	
	}
	
	
	public List<Point> backwardsCPolyLine() {
		List<Point> ret = new ArrayList<>();
		ret.add(new Point(150, 50));
		ret.add(new Point(250, 25));
		ret.add(new Point(275, 50));
		ret.add(new Point(300, 75));		
		ret.add(new Point(325, 100));
		ret.add(new Point(300, 125));
		ret.add(new Point(275, 150));
		ret.add(new Point(250, 175));
		ret.add(new Point(150, 150));
		return ret;

	}

	
}

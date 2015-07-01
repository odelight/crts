package gotox.crts.mockup;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import gotox.crts.controller.Action;
import gotox.crts.controller.InputReader;
import gotox.crts.controller.Player;

public class MockInputReader extends InputReader {
	private List<Action> points;
	public MockInputReader(Player p, List<Action> actions) {
		super(p);
		this.points = actions;
	}
	@Override
	public List<Action> getActions() {
		if(points == null){
			return new ArrayList<>();
		}
		List<Action> ret = points;
		points = null;
		return ret;
	}

}

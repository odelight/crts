package gotox.crts;

import java.awt.Dimension;

import gotox.crts.controller.InputReader;
import gotox.crts.controller.Player;
import gotox.crts.model.ImmutableMap;
import gotox.crts.model.MapModel;
import gotox.crts.view.MapDisplay;

import javax.swing.JFrame;

public class Game extends JFrame {

	private static final long serialVersionUID = 1L;

	public Game() {
		init();
	}

	private void init() {
		int width = 1200;
		int height = 900;
		
		MapModel map = new MapModel(new Dimension(width, height));
		ImmutableMap iMap = new ImmutableMap(map);
		
		MapDisplay md = new MapDisplay(iMap);
		InputReader ir = new InputReader(map, new Player());
		ir.registerRedrawListener(md);
		
		add(md);
	    addMouseMotionListener(ir);
		setSize(width, height);
		setTitle("C.R.T.S.");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		Game g = new Game();
		g.setVisible(true);
	}

}

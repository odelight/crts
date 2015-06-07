package gotox.crts;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import gotox.crts.controller.Action;
import gotox.crts.controller.InputReader;
import gotox.crts.controller.Player;
import gotox.crts.model.ImmutableMap;
import gotox.crts.model.MapModel;
import gotox.crts.networking.Network;
import gotox.crts.view.MapDisplay;

import javax.swing.JFrame;

public class Game extends JFrame {

	private static final long serialVersionUID = 1L;
	private volatile boolean isRunning;
	private InputReader ir;
	private Network n;

	public Game() {
		init();
	}

	private void init() {
		int width = 1200;
		int height = 900;
		
		MapModel map = new MapModel(new Dimension(width, height));
		ImmutableMap iMap = new ImmutableMap(map);
		
		MapDisplay md = new MapDisplay(iMap);
		ir = new InputReader( new Player());	
		
		add(md);
	    addMouseMotionListener(ir);
		setSize(width, height);
		setTitle("C.R.T.S.");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosed(WindowEvent arg0) {
				isRunning = false;
			}
		});
		setLocationRelativeTo(null);
		
		n = new Network();
	}
	
	private void gameLoop(){
		isRunning = true;
		while (isRunning){
			List<Action> inputs = ir.getActions();
			n.queueActions(inputs);
		}
	}

	public static void main(String[] args) {
		Game g = new Game();
		g.setVisible(true);
		g.gameLoop();
	}

}

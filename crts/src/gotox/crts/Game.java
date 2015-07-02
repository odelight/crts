package gotox.crts;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import gotox.crts.controller.Action;
import gotox.crts.controller.InputReader;
import gotox.crts.controller.Player;
import gotox.crts.model.AbstractColor;
import gotox.crts.model.ImmutableMap;
import gotox.crts.model.MapModel;
import gotox.crts.model.CrtsPolygon;
import gotox.crts.networking.Network;
import gotox.crts.view.MapDisplay;

import javax.swing.JFrame;

public class Game extends JFrame {

	private static final long serialVersionUID = 1L;
	private volatile boolean isRunning;
	private InputReader ir;
	private Network n;
	private MapModel map;
	public Game(int port) throws IOException {
		this(new Network(port), AbstractColor.PLAYER1);

	}
	public Game(String hostname, int port) throws IOException {
		this(new Network(hostname, port), AbstractColor.PLAYER2);
	}
	public Game() {
		this(new Network(), AbstractColor.PLAYER1);
	}
	public Game(Network n, AbstractColor playerColor) {
		this(n, playerColor,new InputReader(new Player(playerColor)));
	}
	public Game(Network n, AbstractColor playerColor, InputReader ir) {
		this.ir = ir;
		this.n = n;
		int width = 1200;
		int height = 800;
		
		map = new MapModel(new Dimension(width, height));
		initMap(width, height);		
		ImmutableMap iMap = new ImmutableMap(map);
		
		MapDisplay md = new MapDisplay(iMap);
		
		add(md);
		addMouseMotionListener(ir.asMouseMotionAdapter());
		addMouseListener(ir.asMouseAdapter());
		setSize(width, height);
		setTitle("C.R.T.S.");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				isRunning = false;
			}
		});
		setLocationRelativeTo(null);
	}
	
	private void initMap(int width, int height) {
		int cornerSize = 200;
		{
		List<Point> player1Blob = Arrays.asList(
				new Point[]{new Point(0,0),
						new Point(cornerSize,0),
						new Point(cornerSize,cornerSize),
						new Point(0,cornerSize),
						new Point(0,0)});
		CrtsPolygon player1 = new CrtsPolygon(player1Blob, map);
		map.putBlob(AbstractColor.PLAYER1, player1);
		}
		{
		List<Point> player2Blob = Arrays.asList(
				new Point[]{new Point(width-1,height-1),
						new Point(width - cornerSize,height-1),
						new Point(width - cornerSize,height - cornerSize),
						new Point(width-1,height - cornerSize),
						new Point(width-1,height-1)});
		CrtsPolygon player2 = new CrtsPolygon(player2Blob, map);
		map.putBlob(AbstractColor.PLAYER2, player2);
		}
//		for(int i = 0; i < cornerSize; i++){
//			for(int j = 0; j < cornerSize; j++){
//				map.setColor(i, j, AbstractColor.PLAYER1);
//			}			
//		}
//		for(int i = width - cornerSize; i < width; i++){
//			for(int j = height -cornerSize; j < height; j++){
//				map.setColor(i, j, AbstractColor.PLAYER2);
//			}			
//		}
	}

	public void start() {
		n.start();
		gameLoop();
	}

	private void gameLoop() {
		isRunning = true;
		while (isRunning) {
			List<Action> inputs = ir.getActions();
			n.queueActions(inputs);
			List<Action> applicables = n.getActions();
			Collections.sort(applicables);
			if (!applicables.isEmpty()) {
				for (Action a : applicables) {
					a.apply(map);
				}
				this.repaint();
			}
		}
	}
	
	private void cleanUp(){
		try {
			n.cleanUp();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		Game g;
		if (args.length == 0){
			g = new Game();
		} else if(args.length == 1){
			int port;
			try{
			port = Integer.parseInt(args[0]);
			g = new Game(port);
			} catch (NumberFormatException nfe){
				printUsage();
				return;
			} catch (IOException e) {
				handleConstructorIOException(e);
				return;			}
		} else if(args.length == 2){
			int port;
			try{
				port = Integer.parseInt(args[1]);
				g = new Game(args[0], port);
				} catch (NumberFormatException nfe){
					printUsage();
					return;
				} catch (IOException e) {
					handleConstructorIOException(e);
					return;
				}
		} else {
			printUsage();
			return;
		}
		g.setVisible(true);
		g.start();
	}

	private static void printUsage() {
		System.out.println("Usage: 1. Call with zero arguments to start a single player game. " +
				"2. Call with a single integer as argument in order to host a game on that port. " +
				"3. Call with two arguments to connect to a host on port. arg one is host, arg two is port.");
	}
	
	private static void handleConstructorIOException(IOException e){
		System.out.println("Network issue - possibly incorrect port and/or hostname?");
		printUsage();
		System.out.println("Stack trace of issue:");
		e.printStackTrace();
	}
	
	public MapModel getMap(){
		return map;
	}

}

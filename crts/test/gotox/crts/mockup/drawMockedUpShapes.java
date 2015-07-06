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
				.asList(new Action[] { getCase2part1Action(), getCase2part2Action() });
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
	
	public Action getCase1part1Action() {
		return new DrawFilledPolyRegion(case1part1(),
		AbstractColor.PLAYER1);	
	}
	public Action getCase1part2Action() {
		return new DrawFilledPolyRegion(case1part2(),
		AbstractColor.PLAYER1);	
	}	
	public List<Point> case1part1(){
		return Arrays.asList(new Point[] { new Point(57,160), new Point(61,166), new Point(68,180), new Point(79,211), new Point(92,234), new Point(130,265), new Point(140,282), new Point(151,303), new Point(174,340), new Point(189,350), new Point(208,359), new Point(230,370), new Point(264,390), new Point(273,399), new Point(282,405), new Point(285,405), new Point(297,405), new Point(304,401), new Point(312,394), new Point(321,388), new Point(334,367), new Point(340,350), new Point(347,331), new Point(350,312), new Point(363,292), new Point(364,283), new Point(366,276), new Point(367,269), new Point(367,250), new Point(367,239), new Point(360,225), new Point(355,213), new Point(343,186), new Point(341,168), new Point(333,156), new Point(324,147), new Point(289,136), new Point(276,132), new Point(266,129), new Point(246,120), new Point(227,108), new Point(211,97), new Point(197,87), new Point(180,77), new Point(173,73), new Point(167,73), new Point(154,72), new Point(147,71), new Point(141,71), new Point(138,71), new Point(133,71), new Point(131,71), new Point(130,71), new Point(127,70)});
	}
	public List<Point> case1part2(){
		return Arrays.asList(new Point[] { new Point(61,166), new Point(68,180), new Point(79,211), new Point(92,234), new Point(130,265), new Point(140,282), new Point(151,303), new Point(174,340), new Point(189,350), new Point(208,359), new Point(230,370), new Point(264,390), new Point(273,399), new Point(282,405), new Point(285,405), new Point(297,405), new Point(304,401), new Point(312,394), new Point(321,388), new Point(334,367), new Point(340,350), new Point(347,331), new Point(350,312), new Point(363,292), new Point(364,283), new Point(366,276), new Point(367,269), new Point(367,250), new Point(367,239), new Point(360,225), new Point(355,213), new Point(343,186), new Point(341,168), new Point(333,156), new Point(324,147), new Point(289,136), new Point(276,132), new Point(266,129), new Point(246,120), new Point(227,108), new Point(211,97), new Point(197,87), new Point(180,77), new Point(173,73), new Point(167,73), new Point(154,72), new Point(147,71), new Point(141,71), new Point(138,71), new Point(133,71), new Point(131,71), new Point(130,71), new Point(127,70)});
	}
	
	public Action getCase2part1Action() {
		return new DrawFilledPolyRegion(case2part1(),
		AbstractColor.PLAYER1);	
	}
	public Action getCase2part2Action() {
		return new DrawFilledPolyRegion(case2part2(),
		AbstractColor.PLAYER1);	
	}	
	public List<Point> case2part1(){
		return Arrays.asList(new Point[] { new Point(187,106), new Point(188,106), new Point(189,106), new Point(221,108), new Point(224,108), new Point(282,114), new Point(314,122), new Point(321,129), new Point(321,135), new Point(315,143), new Point(298,158), new Point(284,164), new Point(280,166), new Point(281,166), new Point(292,167), new Point(310,169), new Point(323,171), new Point(330,175), new Point(332,176), new Point(332,181), new Point(331,194), new Point(329,204), new Point(324,213), new Point(315,221), new Point(293,232), new Point(267,238), new Point(260,239), new Point(261,239), new Point(263,239), new Point(269,240), new Point(278,247), new Point(284,255), new Point(284,265), new Point(282,278), new Point(280,278), new Point(276,293), new Point(268,304), new Point(258,314), new Point(229,335), new Point(189,348), new Point(175,350), new Point(169,345), new Point(164,338), new Point(166,328), new Point(170,317), new Point(174,306), new Point(179,288), new Point(179,276), new Point(173,264), new Point(173,263), new Point(168,253), new Point(162,241), new Point(156,229), new Point(153,219), new Point(150,207), new Point(146,197), new Point(141,188), new Point(137,183), new Point(137,182)});
	}

	public List<Point> case2part2(){
		return Arrays.asList(new Point[] { new Point(221,296), new Point(223,296), new Point(223,297), new Point(236,311), new Point(263,331), new Point(354,350), new Point(406,349), new Point(405,331), new Point(389,293), new Point(367,274), new Point(337,257), new Point(296,231), new Point(294,231), new Point(256,204), new Point(249,193), new Point(250,180), new Point(255,166), new Point(256,153), new Point(254,143), new Point(254,142)});
	}

}

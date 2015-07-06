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
				.asList(new Action[] { getCase1part1Action(), getCase1part2Action() });
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

	
//	New draw created:new Point(305,276), new Point(312,280), new Point(329,288), new Point(358,296), new Point(398,311), new Point(422,319), new Point(460,325), new Point(494,339), new Point(509,347), new Point(528,353), new Point(547,366), new Point(574,390), new Point(583,398), new Point(588,404), new Point(592,413), new Point(592,418), new Point(592,419), new Point(592,422), new Point(592,422), new Point(592,424), new Point(592,427), new Point(591,431), new Point(590,435), new Point(587,439), new Point(582,446), new Point(581,449), new Point(577,453), new Point(574,455), new Point(568,461), new Point(564,464), new Point(563,466), new Point(558,468), new Point(558,468), new Point(558,468), new Point(552,477), new Point(526,504), new Point(504,522), new Point(491,532), new Point(485,539), new Point(476,547), new Point(474,551), new Point(470,553), new Point(469,555), new Point(462,557), new Point(457,557), new Point(452,557), new Point(446,557), new Point(433,552), new Point(427,548), new Point(421,544), new Point(414,535), new Point(384,486), new Point(354,464), new Point(334,447), new Point(324,435), new Point(306,396), new Point(296,382), new Point(288,375), new Point(271,361), new Point(259,352), new Point(252,342), new Point(247,331), new Point(240,305), new Point(240,295), new Point(240,290), new Point(238,283), new Point(237,280), new Point(235,279), new Point(235,279), new Point(234,277), Attempting to draw viable figure
	
}

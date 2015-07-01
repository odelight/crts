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
				.asList(new Action[] { getDribbleIssueAction() });
		MockInputReader mir = new MockInputReader(new Player(
				AbstractColor.PLAYER1), actions);
		Game g = new Game(new Network(), AbstractColor.PLAYER1, mir);
		g.setVisible(true);
		g.start();
	}

	public Action getDribbleIssueAction() {
//		return new DrawFilledPolyRegion(dribbleIssuePolyLine(),
//				AbstractColor.PLAYER1);
		return new DrawFilledPolyRegion(simpleDiamondPolyLine(),
		AbstractColor.PLAYER1);	
	}
	
	
	public List<Point> simpleDiamondPolyLine() {
		List<Point> ret = new ArrayList<>();
		ret.add(new Point(150, 100));
		ret.add(new Point(100, 250));
		
		ret.add(new Point(200, 350));
		ret.add(new Point(100, 350));
		
		ret.add(new Point(200, 250));
		ret.add(new Point(150, 100));
		return ret;

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

	public List<Point> dribbleIssuePolyLine() {
		List<Point> ret = new ArrayList<>();
		ret.add(new Point(137, 200));
		ret.add(new Point(137, 200));
		ret.add(new Point(137, 202));
		ret.add(new Point(136, 208));
		ret.add(new Point(136, 214));
		ret.add(new Point(136, 219));
		ret.add(new Point(135, 224));
		ret.add(new Point(134, 232));
		ret.add(new Point(134, 237));
		ret.add(new Point(133, 242));
		ret.add(new Point(133, 253));
		ret.add(new Point(133, 260));
		ret.add(new Point(133, 266));
		ret.add(new Point(133, 276));
		ret.add(new Point(133, 280));
		ret.add(new Point(133, 285));
		ret.add(new Point(133, 289));
		ret.add(new Point(133, 298));
		ret.add(new Point(133, 301));
		ret.add(new Point(136, 305));
		ret.add(new Point(137, 313));
		ret.add(new Point(138, 318));
		ret.add(new Point(139, 321));
		ret.add(new Point(143, 325));
		ret.add(new Point(144, 329));
		ret.add(new Point(151, 334));
		ret.add(new Point(154, 336));
		ret.add(new Point(159, 341));
		ret.add(new Point(160, 344));
		ret.add(new Point(161, 346));
		ret.add(new Point(164, 350));
		ret.add(new Point(165, 355));
		ret.add(new Point(171, 364));
		ret.add(new Point(172, 367));
		ret.add(new Point(180, 370));
		ret.add(new Point(185, 370));
		ret.add(new Point(189, 371));
		ret.add(new Point(193, 371));
		ret.add(new Point(201, 371));
		ret.add(new Point(203, 371));
		ret.add(new Point(205, 370));
		ret.add(new Point(206, 368));
		ret.add(new Point(210, 364));
		ret.add(new Point(210, 361));
		ret.add(new Point(211, 356));
		ret.add(new Point(212, 349));
		ret.add(new Point(212, 346));
		ret.add(new Point(212, 346));
		ret.add(new Point(212, 344));
		ret.add(new Point(212, 344));
		ret.add(new Point(212, 341));
		ret.add(new Point(211, 341));
		ret.add(new Point(207, 337));
		ret.add(new Point(206, 336));
		ret.add(new Point(202, 333));
		ret.add(new Point(199, 332));
		ret.add(new Point(190, 329));
		ret.add(new Point(186, 327));
		ret.add(new Point(183, 326));
		ret.add(new Point(179, 326));
		ret.add(new Point(178, 326));
		ret.add(new Point(175, 326));
		ret.add(new Point(174, 327));
		ret.add(new Point(167, 330));
		ret.add(new Point(164, 331));
		ret.add(new Point(161, 333));
		ret.add(new Point(156, 334));
		ret.add(new Point(155, 334));
		ret.add(new Point(153, 336));
		ret.add(new Point(150, 336));
		ret.add(new Point(148, 340));
		ret.add(new Point(145, 341));
		ret.add(new Point(144, 344));
		ret.add(new Point(141, 349));
		ret.add(new Point(140, 354));
		ret.add(new Point(139, 359));
		ret.add(new Point(138, 364));
		ret.add(new Point(135, 369));
		ret.add(new Point(135, 371));
		ret.add(new Point(135, 373));
		ret.add(new Point(134, 375));
		ret.add(new Point(133, 378));
		ret.add(new Point(130, 381));
		ret.add(new Point(129, 384));
		ret.add(new Point(129, 386));
		ret.add(new Point(126, 389));
		ret.add(new Point(126, 391));
		ret.add(new Point(126, 400));
		ret.add(new Point(126, 407));
		ret.add(new Point(126, 415));
		ret.add(new Point(128, 424));
		ret.add(new Point(129, 433));
		ret.add(new Point(129, 453));
		ret.add(new Point(130, 463));
		ret.add(new Point(135, 477));
		ret.add(new Point(135, 481));
		ret.add(new Point(136, 486));
		ret.add(new Point(139, 488));
		ret.add(new Point(139, 490));
		ret.add(new Point(139, 491));
		ret.add(new Point(140, 492));
		ret.add(new Point(141, 493));
		ret.add(new Point(145, 496));
		ret.add(new Point(145, 496));
		ret.add(new Point(146, 496));
		ret.add(new Point(149, 496));
		ret.add(new Point(150, 496));
		ret.add(new Point(150, 496));
		ret.add(new Point(151, 496));
		ret.add(new Point(156, 493));
		ret.add(new Point(159, 492));
		ret.add(new Point(163, 490));
		ret.add(new Point(171, 488));
		ret.add(new Point(175, 487));
		ret.add(new Point(178, 485));
		ret.add(new Point(179, 484));
		ret.add(new Point(184, 481));
		ret.add(new Point(185, 480));
		ret.add(new Point(186, 477));
		ret.add(new Point(190, 473));
		ret.add(new Point(190, 471));
		ret.add(new Point(190, 471));
		ret.add(new Point(190, 471));
		ret.add(new Point(190, 470));
		ret.add(new Point(190, 470));
		ret.add(new Point(190, 468));
		ret.add(new Point(188, 461));
		ret.add(new Point(187, 459));
		ret.add(new Point(183, 456));
		ret.add(new Point(182, 456));
		ret.add(new Point(175, 453));
		ret.add(new Point(171, 452));
		ret.add(new Point(167, 450));
		ret.add(new Point(160, 449));
		ret.add(new Point(157, 449));
		ret.add(new Point(155, 449));
		ret.add(new Point(154, 449));
		ret.add(new Point(149, 449));
		ret.add(new Point(149, 449));
		ret.add(new Point(147, 449));
		ret.add(new Point(141, 449));
		ret.add(new Point(139, 449));
		ret.add(new Point(138, 450));
		ret.add(new Point(133, 451));
		ret.add(new Point(133, 452));
		ret.add(new Point(133, 452));
		ret.add(new Point(132, 452));
		ret.add(new Point(131, 454));
		ret.add(new Point(125, 459));
		ret.add(new Point(124, 462));
		ret.add(new Point(120, 472));
		ret.add(new Point(120, 478));
		ret.add(new Point(120, 481));
		ret.add(new Point(119, 484));
		ret.add(new Point(119, 486));
		ret.add(new Point(119, 493));
		ret.add(new Point(119, 497));
		ret.add(new Point(119, 501));
		ret.add(new Point(125, 511));
		ret.add(new Point(126, 516));
		ret.add(new Point(130, 520));
		ret.add(new Point(134, 524));
		ret.add(new Point(142, 529));
		ret.add(new Point(145, 532));
		ret.add(new Point(148, 535));
		ret.add(new Point(154, 539));
		ret.add(new Point(157, 541));
		ret.add(new Point(158, 541));
		ret.add(new Point(165, 544));
		ret.add(new Point(168, 545));
		ret.add(new Point(171, 545));
		ret.add(new Point(175, 545));
		ret.add(new Point(183, 545));
		ret.add(new Point(189, 545));
		ret.add(new Point(193, 545));
		ret.add(new Point(198, 545));
		ret.add(new Point(201, 545));
		ret.add(new Point(205, 545));
		ret.add(new Point(208, 544));
		ret.add(new Point(221, 542));
		ret.add(new Point(229, 541));
		ret.add(new Point(235, 539));
		ret.add(new Point(244, 536));
		ret.add(new Point(246, 535));
		ret.add(new Point(249, 533));
		ret.add(new Point(252, 531));
		ret.add(new Point(259, 527));
		ret.add(new Point(264, 523));
		ret.add(new Point(268, 518));
		ret.add(new Point(276, 509));
		ret.add(new Point(277, 503));
		ret.add(new Point(280, 499));
		ret.add(new Point(280, 492));
		ret.add(new Point(280, 472));
		ret.add(new Point(280, 450));
		ret.add(new Point(280, 431));
		ret.add(new Point(266, 401));
		ret.add(new Point(254, 383));
		ret.add(new Point(247, 364));
		ret.add(new Point(241, 351));
		ret.add(new Point(234, 334));
		ret.add(new Point(232, 327));
		ret.add(new Point(231, 318));
		ret.add(new Point(230, 306));
		ret.add(new Point(228, 262));
		ret.add(new Point(225, 249));
		ret.add(new Point(225, 243));
		ret.add(new Point(225, 240));
		ret.add(new Point(225, 238));
		ret.add(new Point(225, 236));
		ret.add(new Point(225, 231));
		ret.add(new Point(225, 229));
		ret.add(new Point(224, 226));
		ret.add(new Point(224, 225));
		ret.add(new Point(220, 221));
		ret.add(new Point(219, 218));
		ret.add(new Point(217, 216));
		ret.add(new Point(213, 212));
		ret.add(new Point(212, 210));
		ret.add(new Point(211, 208));
		ret.add(new Point(208, 205));
		ret.add(new Point(207, 202));
		ret.add(new Point(204, 198));
		ret.add(new Point(203, 195));
		ret.add(new Point(200, 192));
		return ret;
	}

}

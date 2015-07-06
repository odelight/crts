package gotox.crts.view;

import gotox.crts.model.AbstractColor;
import gotox.crts.model.CrtsPolyline;
import gotox.crts.model.ImmutableMap;
import gotox.crts.model.CrtsPolygon;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

public class MapDisplay extends JPanel implements RedrawListener {
	private static final long serialVersionUID = 1L;
	private final Dimension mapDimension;
	private final BufferedImage image;
	private final ImmutableMap map;
	private final Map<AbstractColor, Color> colorModel;
	private final Color trailColor;
	private CrtsPolyline trail;

	public MapDisplay(ImmutableMap m) {
		colorModel = new HashMap<>();
		initColorModel();
		trailColor = Color.BLUE;
		map = m;
		mapDimension = map.getDimension();
		image = new BufferedImage(mapDimension.width, mapDimension.height,
				BufferedImage.TYPE_INT_ARGB);
	}

	public void setTrail(List<Point> verts) {
		if (verts.size() > 2) {
			trail = new CrtsPolyline(verts, map);
			requestRedraw();
		}
	}

	public void clearTrails() {
		trail = null;
	}

	private void initColorModel() {
		colorModel.put(AbstractColor.BLANK, Color.WHITE);
		colorModel.put(AbstractColor.PLAYER1, Color.GREEN);
		colorModel.put(AbstractColor.PLAYER2, Color.RED);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw();
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, null, null);
	}

	public void draw() {
		clearImage();
		Map<AbstractColor, CrtsPolygon> blobMap = map.getBlobMap();
		for (Map.Entry<AbstractColor, CrtsPolygon> e : blobMap.entrySet()) {
			drawPolygon(e.getKey(), e.getValue());
		}
		if (trail != null)
			drawPolyline(trailColor, trail);
	}

	public void clearImage() {
		for (int x = 0; x < mapDimension.width; x++) {
			for (int y = 0; y < mapDimension.height; y++) {
				Color blank = colorModel.get(AbstractColor.BLANK);
				image.setRGB(x, y, blank.getRGB());
			}
		}
	}

	public void drawPolygon(AbstractColor ac, CrtsPolygon paul) {
		Graphics g = image.getGraphics();
		g.setColor(colorModel.get(ac));
		// g.fillPolygon(paul.getPolygon());
		g.drawPolygon(paul.getPolygon());
	}

	public void drawPolyline(Color c, CrtsPolyline line) {
		Graphics g = image.getGraphics();
		g.setColor(c);
		List<Point> verts = line.getPoints();
		int nPoints = verts.size();
		int[] xPoints = new int[nPoints];
		int[] yPoints = new int[nPoints];
		for (int i = 0; i < nPoints; i++) {
			Point p = verts.get(i);
			xPoints[i] = p.x;
			yPoints[i] = p.y;

		}
		g.drawPolyline(xPoints, yPoints, nPoints);
	}

	@Override
	public void requestRedraw() {
		repaint();
	}

}

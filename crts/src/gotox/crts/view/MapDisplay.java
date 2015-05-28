package gotox.crts.view;

import gotox.crts.model.AbstractColor;
import gotox.crts.model.ImmutableMap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class MapDisplay extends JPanel implements RedrawListener {
	private final Dimension mapDimension;
	private final BufferedImage image;
	private final ImmutableMap map;
	private final Map<AbstractColor, Color> colorModel;

	public MapDisplay(ImmutableMap m) {
		colorModel = new HashMap<>();
		initColorModel();
		map = m;
		mapDimension = map.getDimension();
		image = new BufferedImage(mapDimension.width, mapDimension.height,
				BufferedImage.TYPE_INT_ARGB);
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
		for (int x = 0; x < mapDimension.width; x++) {
			for (int y = 0; y < mapDimension.height; y++) {
				AbstractColor ac = map.getColor(x, y);
				Color c = colorModel.get(ac);
				image.setRGB(x, y, c.getRGB());
			}
		}
	}

	@Override
	public void requestRedraw() {
		repaint();
	}

}

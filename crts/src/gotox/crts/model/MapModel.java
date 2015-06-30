package gotox.crts.model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MapModel {	
	private final Dimension mapSize;
	private final Map<AbstractColor, Polygon> blobs;
	public MapModel(Dimension d){
		mapSize = new Dimension(d.width,d.height);
		blobs = new HashMap<>();
	}
	
	public void putBlob(AbstractColor c, Polygon p){
		blobs.put(c, p);
	}
	/**
	 * returns LIVE COPY
	 * @param c
	 * @return
	 */
	public Polygon getBlob(AbstractColor c){
		return blobs.get(c);
	}
	
	public Map<AbstractColor, Polygon> getBlobMap(){
		return blobs;
	}
	
	public Dimension getDimension(){
		return new Dimension(mapSize.width, mapSize.height);
	}
	
	public boolean isValidPoint(Point p) {
		return ((0 <= p.x) && (p.x < mapSize.width) && (0 <= p.y) && (p.y < mapSize.height));
	}
}

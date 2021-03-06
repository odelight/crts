package gotox.crts.model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MapModel {	
	private final Dimension mapSize;
	private final Map<AbstractColor, CrtsPolygon> blobs;
	public MapModel(Dimension d){
		mapSize = new Dimension(d.width,d.height);
		blobs = new HashMap<>();
	}
	
	public void putBlob(AbstractColor c, CrtsPolygon p){
		blobs.put(c, p);
	}
	/**
	 * returns LIVE COPY
	 * @param c
	 * @return
	 */
	public CrtsPolygon getBlob(AbstractColor c){
		return blobs.get(c);
	}
	
	public Map<AbstractColor, CrtsPolygon> getBlobMap(){
		return blobs;
	}
	
	public Dimension getDimension(){
		return new Dimension(mapSize.width, mapSize.height);
	}
	
	public boolean isValidPoint(Point p) {
		if(p == null){
			return false;
		}
		return ((0 <= p.x) && (p.x < mapSize.width) && (0 <= p.y) && (p.y < mapSize.height));
	}
	
	public void makePointValid(Point p){
		if(p.x < 0){
			p.x = 0;
		} else if(p.x >= mapSize.width){
			p.x = mapSize.width - 1;
		}
		
		if(p.y < 0){
			p.y = 0;
		} else if (p.y >= mapSize.height){
			p.y = mapSize.height - 1;
		}
	}
}

package gotox.crts.model;

import gotox.crts.model.AbstractColor;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Collections;
import java.util.Map;
/**
 * Provides an immutable view of an underlying map.
 * DOES NOT MAKE UNDERLYING MAP IMMUTABLE.
 * 
 * @author olof
 *
 */
public class ImmutableMap {
	private final MapModel innerMap;

	public ImmutableMap(MapModel m) {
		innerMap = m;
	}

	public Polygon getBlob(AbstractColor c){
		return innerMap.getBlob(c);
	}
	
	public Map<AbstractColor, Polygon> getBlobMap(){
		return Collections.unmodifiableMap(innerMap.getBlobMap());
	}

	
	public boolean isValidPoint(Point p) {
		return innerMap.isValidPoint(p);
	}
	
	public Dimension getDimension(){
		return innerMap.getDimension();
	}
}

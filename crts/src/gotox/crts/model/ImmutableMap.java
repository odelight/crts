package gotox.crts.model;

import gotox.crts.model.AbstractColor;

import java.awt.Dimension;
import java.awt.Point;
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

	public AbstractColor getColor(Point p) {
		return innerMap.getColor(p);
	}
	public AbstractColor getColor(int x, int y){
		return innerMap.getColor(x,y);
	}
	public Dimension getDimension(){
		return innerMap.getDimension();
	}
}

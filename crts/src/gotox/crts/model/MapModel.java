package gotox.crts.model;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Arrays;

public class MapModel {	
	private final Dimension mapSize;
	private final AbstractColor[][] mapArray;
	
	public MapModel(Dimension d){
		mapSize = new Dimension(d.width,d.height);
		mapArray = new AbstractColor[mapSize.width][mapSize.height];
		for(AbstractColor[] col : mapArray)
			Arrays.fill(col, AbstractColor.BLANK);
	}
	
	public void setColor(Point p, AbstractColor c){
		mapArray[p.x][p.y] = c;
	}
	public void setColor(int x, int y, AbstractColor c){
		mapArray[x][y] = c;
	}
	public AbstractColor getColor(Point p){
		return mapArray[p.x][p.y];
	}
	public AbstractColor getColor(int x, int y){
		return mapArray[x][y];
	}
	public Dimension getDimension(){
		return new Dimension(mapSize.width, mapSize.height);
	}
}

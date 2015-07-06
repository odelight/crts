package gotox.crts;

import java.awt.Point;

public class Intersection {
	
	private final Point p1, p2;
	
	public Intersection(Point p){
		if(p == null){
			throw new NullPointerException();
		}
		p1 = p;
		p2 = null;
	}

	public Intersection(Point p1, Point p2){
		if(p1 == null || p2 == null){
			throw new NullPointerException();
		}
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public boolean isSingleIntersection(){
		return p2 == null;
	}
	
	public Point firstPoint(){
		return p1;
	}
	
	public Point secondPoint(){
		return p2;
	}
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Intersection))
			return false;
		Intersection other = (Intersection)o;
		if(equalAllowNulls(p1, other.p1)){
			if(equalAllowNulls(p2, other.p2)){
				return true;
			} 
		} 
		return false;
	}
	
	private boolean equalAllowNulls(Object o1, Object o2){
		if (o1 == null){
			if(o2 == null){
				return true;
			}
			return false;
		}
		return o1.equals(o2);
	}
	
	@Override
	public int hashCode(){
		return p1.hashCode()*17 + p2.hashCode()*13;
		
	}
}

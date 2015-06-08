package gotox.crts.controller;

import java.io.Serializable;

import gotox.crts.model.MapModel;
/**
 * Note: this class has a natural ordering that is inconsistent with equals.
 * 
 * @author olof
 *
 */
public abstract class Action implements Serializable , Comparable<Action> {
	private final long inputtedTime;
	public abstract void apply(MapModel m);
	public Action(){
		inputtedTime = System.currentTimeMillis();
	}
	@Override
	public int compareTo(Action o) {
		// TODO Auto-generated method stub
		return sign(this.inputtedTime - o.inputtedTime);
	}
	
	private static int sign(long l){
		if(l > 0){
			return 1;
		} else if (l < 0){
			return -1;
		} else if(l == 0){
			return 0;
		} else {
			throw new RuntimeException();
		}
	}

}

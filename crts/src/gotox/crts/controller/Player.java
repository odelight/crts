package gotox.crts.controller;

import gotox.crts.model.AbstractColor;

public class Player {
	private final AbstractColor ac;
	public Player(AbstractColor ac){
		this.ac = ac;
	}
	public AbstractColor getColor(){
		return ac;
	}
}

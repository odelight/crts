package gotox.crts.controller;

import java.io.Serializable;

import gotox.crts.model.MapModel;

public interface Action extends Serializable {
	public void apply(MapModel m);
}

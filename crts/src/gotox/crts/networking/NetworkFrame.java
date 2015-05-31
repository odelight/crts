package gotox.crts.networking;

import gotox.crts.controller.Action;

import java.io.Serializable;
import java.util.List;

public class NetworkFrame implements Serializable {
	private static final long serialVersionUID = 1L;
	private final List<Action> actionList;
	public NetworkFrame(List<Action> frameActions){
		actionList = frameActions;
	}

}

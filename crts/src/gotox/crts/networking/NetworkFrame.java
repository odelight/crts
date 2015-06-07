package gotox.crts.networking;

import gotox.crts.controller.Action;

import java.io.Serializable;
import java.util.List;

public class NetworkFrame implements Serializable {
	private static final long serialVersionUID = 1L;
	private final List<Action> actionList;
	private final int frameNumber;
	public NetworkFrame(List<Action> frameActions, int frameNumber){
		actionList = frameActions;
		this.frameNumber = frameNumber;
	}
	public int getFrameNumber() {
		return frameNumber;
	}
	public List<Action> getActionList() {
		return actionList;
	}

}

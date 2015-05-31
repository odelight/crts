package gotox.networking;

import java.util.Date;

public class FrameTimer {
	private final static long msFrameLength = 200;
	private final long createdTime;
	
	public FrameTimer(){
		createdTime = System.currentTimeMillis();
	}

	public Date nextFrameEnd() {
		long msNow = System.currentTimeMillis();
		long msSinceCreated = msNow - createdTime;
		return new Date((msNow + msFrameLength) - (msSinceCreated % msFrameLength));
	}

}

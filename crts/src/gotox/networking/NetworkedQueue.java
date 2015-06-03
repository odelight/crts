package gotox.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Queue of objects of type T('frames') which is synchronized over a network.
 * frames can be added freely, but will only be visible in the queue once all
 * other frames with the same timestamp have been received.
 * 
 * @author olof
 * 
 * @param <T>
 */
public class NetworkedQueue<T extends Serializable> {
	private final List<ObjectOutputStream> remoteOut;
	private final List<ObjectInputStream> remoteIn;
	private final List<ReentrantLock> receivedFramesLocks;
	private final List<List<FrameWrapper>> receivedFrames;
	private int lastReceivedFrame = 0;
	private int lastSentFrame = 0;

	public NetworkedQueue(List<ObjectOutputStream> remoteOut,
			List<ObjectInputStream> remoteIn) {
		this.remoteOut = remoteOut;
		this.remoteIn = remoteIn;
		receivedFrames = new ArrayList<List<FrameWrapper>>();
		receivedFramesLocks = new ArrayList<>();
		for (int i = 0; i < remoteIn.size(); i++) {
			receivedFrames.add(new ArrayList<FrameWrapper>());
			receivedFramesLocks.add(new ReentrantLock());
		}
	}

	public NetworkedQueue(ObjectOutputStream out, ObjectInputStream in) {
		this(Arrays.asList(out), Arrays.asList(in));
	}

	public void pushFrame(T frame) throws IOException {
		if (frame == null)
			throw new NullPointerException();
		FrameWrapper wrapped = new FrameWrapper(frame, lastSentFrame++,
				System.currentTimeMillis());
		for (ObjectOutputStream out : remoteOut) {
			out.writeObject(wrapped);
			out.flush();
		}
	}

	public List<T> pollFrames() throws IOException {
		List<T> ret = new ArrayList<T>();
		int lowestFrame = Integer.MAX_VALUE;
		for (int i = 0; i < remoteIn.size(); i++) {
			List<FrameWrapper> frameList = receivedFrames.get(i);
			if (frameList.size() > 0) {
				FrameWrapper f = frameList.get(0);
				lowestFrame = Math.min(lowestFrame, f.frameNumber);
			} else {
				return ret;
			}
		}
		for (int i = 0; i < remoteIn.size(); i++) {
			List<FrameWrapper> frameList = receivedFrames.get(i);
			receivedFramesLocks.get(i).lock();
			for(FrameWrapper wrappedFrame  : frameList){
//				if(wrappedFrame.frameNumber < lowes){
					
//				}
			}
		}
		return ret;
	}

/**
 * TODO: Put some more thought into the serialization here before release.
 * 
 * @author olof
 * 
 */
 static class FrameWrapper<S> implements Serializable {
	private static final long serialVersionUID = 1L;
	private final long pushedTimestamp;
	private final int frameNumber;
	private S innerFrame;
	
	private FrameWrapper(S frame, int frameNumber, long pushedTimestamp) {
		innerFrame = frame;
		this.frameNumber = frameNumber;
		this.pushedTimestamp = pushedTimestamp;
	}
	
	S getInnerFrame(){
		return innerFrame;
	}
	
	int getFrameNumber(){
		return frameNumber;
	}
	
	long getPushedTimestamp(){
		return pushedTimestamp;
	}
}
}
